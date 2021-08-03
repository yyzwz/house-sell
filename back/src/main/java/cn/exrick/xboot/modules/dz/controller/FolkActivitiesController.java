package cn.exrick.xboot.modules.dz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.dz.entity.FolkActivities;
import cn.exrick.xboot.modules.dz.service.IFolkActivitiesService;
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
@Api(tags = "民俗活动管理接口")
@RequestMapping("/xboot/folkActivities")
@Transactional
public class FolkActivitiesController {

    @Autowired
    private IFolkActivitiesService iFolkActivitiesService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<FolkActivities> get(@PathVariable String id) {

        FolkActivities folkActivities = iFolkActivitiesService.getById(id);
        return new ResultUtil<FolkActivities>().setData(folkActivities);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<FolkActivities>> getAll() {

        List<FolkActivities> list = iFolkActivitiesService.list();
        return new ResultUtil<List<FolkActivities>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<FolkActivities>> getByPage(PageVo page) {

        IPage<FolkActivities> data = iFolkActivitiesService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<FolkActivities>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<FolkActivities> saveOrUpdate(FolkActivities folkActivities) {

        if (iFolkActivitiesService.saveOrUpdate(folkActivities)) {
            return new ResultUtil<FolkActivities>().setData(folkActivities);
        }
        return new ResultUtil<FolkActivities>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iFolkActivitiesService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
