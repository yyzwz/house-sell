package cn.exrick.xboot.modules.dj.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.dj.entity.DynamicBuild;
import cn.exrick.xboot.modules.dj.service.IDynamicBuildService;
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
@Api(tags = "党建动态管理接口")
@RequestMapping("/xboot/dynamicBuild")
@Transactional
public class DynamicBuildController {

    @Autowired
    private IDynamicBuildService iDynamicBuildService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<DynamicBuild> get(@PathVariable String id) {

        DynamicBuild dynamicBuild = iDynamicBuildService.getById(id);
        return new ResultUtil<DynamicBuild>().setData(dynamicBuild);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<DynamicBuild>> getAll() {

        List<DynamicBuild> list = iDynamicBuildService.list();
        return new ResultUtil<List<DynamicBuild>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<DynamicBuild>> getByPage(PageVo page) {

        IPage<DynamicBuild> data = iDynamicBuildService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<DynamicBuild>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<DynamicBuild> saveOrUpdate(DynamicBuild dynamicBuild) {

        if (iDynamicBuildService.saveOrUpdate(dynamicBuild)) {
            return new ResultUtil<DynamicBuild>().setData(dynamicBuild);
        }
        return new ResultUtil<DynamicBuild>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iDynamicBuildService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
