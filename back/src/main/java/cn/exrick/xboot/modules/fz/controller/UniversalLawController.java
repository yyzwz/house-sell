package cn.exrick.xboot.modules.fz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.fz.entity.UniversalLaw;
import cn.exrick.xboot.modules.fz.service.IUniversalLawService;
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
@Api(tags = "普法宣传管理接口")
@RequestMapping("/xboot/universalLaw")
@Transactional
public class UniversalLawController {

    @Autowired
    private IUniversalLawService iUniversalLawService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<UniversalLaw> get(@PathVariable String id) {

        UniversalLaw universalLaw = iUniversalLawService.getById(id);
        return new ResultUtil<UniversalLaw>().setData(universalLaw);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<UniversalLaw>> getAll() {

        List<UniversalLaw> list = iUniversalLawService.list();
        return new ResultUtil<List<UniversalLaw>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<UniversalLaw>> getByPage(PageVo page) {

        IPage<UniversalLaw> data = iUniversalLawService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<UniversalLaw>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<UniversalLaw> saveOrUpdate(UniversalLaw universalLaw) {

        if (iUniversalLawService.saveOrUpdate(universalLaw)) {
            return new ResultUtil<UniversalLaw>().setData(universalLaw);
        }
        return new ResultUtil<UniversalLaw>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iUniversalLawService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
