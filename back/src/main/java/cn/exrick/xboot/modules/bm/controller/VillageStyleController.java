package cn.exrick.xboot.modules.bm.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.bm.entity.VillageStyle;
import cn.exrick.xboot.modules.bm.service.IVillageStyleService;
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
@Api(tags = "一村一品管理接口")
@RequestMapping("/xboot/villageStyle")
@Transactional
public class VillageStyleController {

    @Autowired
    private IVillageStyleService iVillageStyleService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<VillageStyle> get(@PathVariable String id) {

        VillageStyle villageStyle = iVillageStyleService.getById(id);
        return new ResultUtil<VillageStyle>().setData(villageStyle);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<VillageStyle>> getAll() {

        List<VillageStyle> list = iVillageStyleService.list();
        return new ResultUtil<List<VillageStyle>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<VillageStyle>> getByPage(PageVo page) {

        IPage<VillageStyle> data = iVillageStyleService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<VillageStyle>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<VillageStyle> saveOrUpdate(VillageStyle villageStyle) {

        if (iVillageStyleService.saveOrUpdate(villageStyle)) {
            return new ResultUtil<VillageStyle>().setData(villageStyle);
        }
        return new ResultUtil<VillageStyle>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iVillageStyleService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
