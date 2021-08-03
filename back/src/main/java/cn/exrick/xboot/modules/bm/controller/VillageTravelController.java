package cn.exrick.xboot.modules.bm.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.bm.entity.VillageTravel;
import cn.exrick.xboot.modules.bm.service.IVillageTravelService;
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
@Api(tags = "乡村旅游管理接口")
@RequestMapping("/xboot/villageTravel")
@Transactional
public class VillageTravelController {

    @Autowired
    private IVillageTravelService iVillageTravelService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<VillageTravel> get(@PathVariable String id) {

        VillageTravel villageTravel = iVillageTravelService.getById(id);
        return new ResultUtil<VillageTravel>().setData(villageTravel);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<VillageTravel>> getAll() {

        List<VillageTravel> list = iVillageTravelService.list();
        return new ResultUtil<List<VillageTravel>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<VillageTravel>> getByPage(PageVo page) {

        IPage<VillageTravel> data = iVillageTravelService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<VillageTravel>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<VillageTravel> saveOrUpdate(VillageTravel villageTravel) {

        if (iVillageTravelService.saveOrUpdate(villageTravel)) {
            return new ResultUtil<VillageTravel>().setData(villageTravel);
        }
        return new ResultUtil<VillageTravel>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iVillageTravelService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
