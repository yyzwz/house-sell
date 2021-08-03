package cn.exrick.xboot.modules.dz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.dz.entity.FamilyStyle;
import cn.exrick.xboot.modules.dz.service.IFamilyStyleService;
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
@Api(tags = "家风家训管理接口")
@RequestMapping("/xboot/familyStyle")
@Transactional
public class FamilyStyleController {

    @Autowired
    private IFamilyStyleService iFamilyStyleService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<FamilyStyle> get(@PathVariable String id) {

        FamilyStyle familyStyle = iFamilyStyleService.getById(id);
        return new ResultUtil<FamilyStyle>().setData(familyStyle);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<FamilyStyle>> getAll() {

        List<FamilyStyle> list = iFamilyStyleService.list();
        return new ResultUtil<List<FamilyStyle>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<FamilyStyle>> getByPage(PageVo page) {

        IPage<FamilyStyle> data = iFamilyStyleService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<FamilyStyle>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<FamilyStyle> saveOrUpdate(FamilyStyle familyStyle) {

        if (iFamilyStyleService.saveOrUpdate(familyStyle)) {
            return new ResultUtil<FamilyStyle>().setData(familyStyle);
        }
        return new ResultUtil<FamilyStyle>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iFamilyStyleService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
