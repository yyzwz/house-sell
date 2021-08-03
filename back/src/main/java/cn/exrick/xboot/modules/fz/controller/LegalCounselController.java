package cn.exrick.xboot.modules.fz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.fz.entity.LegalCounsel;
import cn.exrick.xboot.modules.fz.service.ILegalCounselService;
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
@Api(tags = "法律顾问管理接口")
@RequestMapping("/xboot/legalCounsel")
@Transactional
public class LegalCounselController {

    @Autowired
    private ILegalCounselService iLegalCounselService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<LegalCounsel> get(@PathVariable String id) {

        LegalCounsel legalCounsel = iLegalCounselService.getById(id);
        return new ResultUtil<LegalCounsel>().setData(legalCounsel);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<LegalCounsel>> getAll() {

        List<LegalCounsel> list = iLegalCounselService.list();
        return new ResultUtil<List<LegalCounsel>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<LegalCounsel>> getByPage(PageVo page) {

        IPage<LegalCounsel> data = iLegalCounselService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<LegalCounsel>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<LegalCounsel> saveOrUpdate(LegalCounsel legalCounsel) {

        if (iLegalCounselService.saveOrUpdate(legalCounsel)) {
            return new ResultUtil<LegalCounsel>().setData(legalCounsel);
        }
        return new ResultUtil<LegalCounsel>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iLegalCounselService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
