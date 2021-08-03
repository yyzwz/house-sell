package cn.exrick.xboot.modules.zz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.zz.entity.TaskDisclosure;
import cn.exrick.xboot.modules.zz.service.ITaskDisclosureService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 郑为中
 */
@Slf4j
@RestController
@Api(tags = "村务公开管理接口")
@RequestMapping("/xboot/taskDisclosure")
@Transactional
public class TaskDisclosureController {

    @Autowired
    private ITaskDisclosureService iTaskDisclosureService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<TaskDisclosure> get(@PathVariable String id) {

        TaskDisclosure taskDisclosure = iTaskDisclosureService.getById(id);
        return new ResultUtil<TaskDisclosure>().setData(taskDisclosure);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<TaskDisclosure>> getAll() {

        List<TaskDisclosure> list = iTaskDisclosureService.list();
        return new ResultUtil<List<TaskDisclosure>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<TaskDisclosure>> getByPage(PageVo page) {

        IPage<TaskDisclosure> data = iTaskDisclosureService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<TaskDisclosure>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<TaskDisclosure> saveOrUpdate(TaskDisclosure taskDisclosure) {

        if (iTaskDisclosureService.saveOrUpdate(taskDisclosure)) {
            return new ResultUtil<TaskDisclosure>().setData(taskDisclosure);
        }
        return new ResultUtil<TaskDisclosure>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iTaskDisclosureService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
