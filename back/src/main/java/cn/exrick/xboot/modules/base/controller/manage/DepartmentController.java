package cn.exrick.xboot.modules.base.controller.manage;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.redis.RedisTemplateHelper;
import cn.exrick.xboot.common.utils.CommonUtil;
import cn.exrick.xboot.common.utils.HibernateProxyTypeAdapter;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.utils.SecurityUtil;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.base.dao.mapper.DeleteMapper;
import cn.exrick.xboot.modules.base.entity.Department;
import cn.exrick.xboot.modules.base.entity.DepartmentHeader;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.base.service.DepartmentHeaderService;
import cn.exrick.xboot.modules.base.service.DepartmentService;
import cn.exrick.xboot.modules.base.service.RoleDepartmentService;
import cn.exrick.xboot.modules.base.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Exrick
 */
@Slf4j
@RestController
@Api(tags = "部门管理接口")
@RequestMapping("/xboot/department")
@CacheConfig(cacheNames = "department")
@Transactional
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDepartmentService roleDepartmentService;

    @Autowired
    private DepartmentHeaderService departmentHeaderService;

    @Autowired
    private DeleteMapper deleteMapper;

    @Autowired
    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过parentId获取")
    public Result<List<Department>> getByParentId(@PathVariable String parentId,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {

        List<Department> list;
        User u = securityUtil.getCurrUser();
        String key = "department::" + parentId + ":" + u.getId() + "_" + openDataFilter;
        String v = redisTemplate.get(key);
        if (StrUtil.isNotBlank(v)) {
            list = new Gson().fromJson(v, new TypeToken<List<Department>>() {
            }.getType());
            return new ResultUtil<List<Department>>().setData(list);
        }
        list = departmentService.findByParentIdOrderBySortOrder(parentId, openDataFilter);
        setInfo(list);
        redisTemplate.set(key,
                new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).create().toJson(list), 15L, TimeUnit.DAYS);
        return new ResultUtil<List<Department>>().setData(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    public Result<Object> add(Department department) {

        Department d = departmentService.save(department);
        // 如果不是添加的一级 判断设置上级为父节点标识
        if (!CommonConstant.PARENT_ID.equals(department.getParentId())) {
            Department parent = departmentService.get(department.getParentId());
            if (parent.getIsParent() == null || !parent.getIsParent()) {
                parent.setIsParent(true);
                departmentService.update(parent);
            }
        }
        // 更新缓存
        redisTemplate.deleteByPattern("department::*");
        return ResultUtil.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public Result<Object> edit(Department department,
                               @RequestParam(required = false) String[] mainHeader,
                               @RequestParam(required = false) String[] viceHeader) {

        if (department.getId().equals(department.getParentId())) {
            return ResultUtil.error("上级节点不能为自己");
        }
        Department old = departmentService.get(department.getId());
        String oldParentId = old.getParentId();
        Department d = departmentService.update(department);
        // 先删除原数据
        departmentHeaderService.deleteByDepartmentId(department.getId());
        List<DepartmentHeader> headers = new ArrayList<>();
        if (mainHeader != null) {
            for (String id : mainHeader) {
                DepartmentHeader dh = new DepartmentHeader().setUserId(id).setDepartmentId(d.getId())
                        .setType(CommonConstant.HEADER_TYPE_MAIN);
                headers.add(dh);
            }
        }
        if (viceHeader != null) {
            for (String id : viceHeader) {
                DepartmentHeader dh = new DepartmentHeader().setUserId(id).setDepartmentId(d.getId())
                        .setType(CommonConstant.HEADER_TYPE_VICE);
                headers.add(dh);
            }
        }
        // 批量保存
        departmentHeaderService.saveOrUpdateAll(headers);
        // 如果该节点不是一级节点 且修改了级别 判断上级还有无子节点
        if (!CommonConstant.PARENT_ID.equals(oldParentId) && !oldParentId.equals(department.getParentId())) {
            Department parent = departmentService.get(oldParentId);
            List<Department> children = departmentService.findByParentIdOrderBySortOrder(parent.getId(), false);
            if (parent != null && (children == null || children.isEmpty())) {
                parent.setIsParent(false);
                departmentService.update(parent);
            }
        }
        // 若修改了部门名称
        if (!old.getTitle().equals(department.getTitle())) {
            userService.updateDepartmentTitle(department.getId(), department.getTitle());
            // 删除所有用户缓存
            redisTemplate.deleteByPattern("user:*");
        }
        // 手动删除所有部门缓存
        redisTemplate.deleteByPattern("department:*");
        return ResultUtil.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            deleteRecursion(id, ids);
        }
        // 手动删除所有部门缓存
        redisTemplate.deleteByPattern("department:*");
        // 删除数据权限缓存
        redisTemplate.deleteByPattern("userRole::depIds:*");
        return ResultUtil.success("批量通过id删除数据成功");
    }

    public void deleteRecursion(String id, String[] ids) {

        List<User> list = userService.findByDepartmentId(id);
        if (list != null && list.size() > 0) {
            throw new XbootException("删除失败，包含正被用户使用关联的部门");
        }
        // 获得其父节点
        Department dep = departmentService.get(id);
        Department parent = null;
        if (dep != null && StrUtil.isNotBlank(dep.getParentId())) {
            parent = departmentService.get(dep.getParentId());
        }
        departmentService.delete(id);
        // 删除关联数据权限
        roleDepartmentService.deleteByDepartmentId(id);
        // 删除关联部门负责人
        departmentHeaderService.deleteByDepartmentId(id);
        // 删除流程关联节点
        deleteMapper.deleteActNode(id);
        // 判断父节点是否还有子节点
        if (parent != null) {
            List<Department> childrenDeps = departmentService.findByParentIdOrderBySortOrder(parent.getId(), false);
            if (childrenDeps == null || childrenDeps.isEmpty()) {
                parent.setIsParent(false);
                departmentService.update(parent);
            }
        }
        // 递归删除
        List<Department> departments = departmentService.findByParentIdOrderBySortOrder(id, false);
        for (Department d : departments) {
            if (!CommonUtil.judgeIds(d.getId(), ids)) {
                deleteRecursion(d.getId(), ids);
            }
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public Result<List<Department>> searchByTitle(@RequestParam String title,
                                                  @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {

        List<Department> list = departmentService.findByTitleLikeOrderBySortOrder("%" + title + "%", openDataFilter);
        setInfo(list);
        return new ResultUtil<List<Department>>().setData(list);
    }

    public void setInfo(List<Department> list) {

        // lambda表达式
        list.forEach(item -> {
            if (!CommonConstant.PARENT_ID.equals(item.getParentId())) {
                Department parent = departmentService.get(item.getParentId());
                item.setParentTitle(parent.getTitle());
            } else {
                item.setParentTitle("一级部门");
            }
            // 设置负责人
            item.setMainHeaders(departmentHeaderService.findHeaderByDepartmentId(item.getId(), CommonConstant.HEADER_TYPE_MAIN));
            item.setViceHeaders(departmentHeaderService.findHeaderByDepartmentId(item.getId(), CommonConstant.HEADER_TYPE_VICE));
        });
    }
}
