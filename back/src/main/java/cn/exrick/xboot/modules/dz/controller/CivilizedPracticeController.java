package cn.exrick.xboot.modules.dz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.dz.entity.CivilizedPractice;
import cn.exrick.xboot.modules.dz.service.ICivilizedPracticeService;
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
@Api(tags = "文明实践管理接口")
@RequestMapping("/xboot/civilizedPractice")
@Transactional
public class CivilizedPracticeController {

    @Autowired
    private ICivilizedPracticeService iCivilizedPracticeService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<CivilizedPractice> get(@PathVariable String id) {

        CivilizedPractice civilizedPractice = iCivilizedPracticeService.getById(id);
        return new ResultUtil<CivilizedPractice>().setData(civilizedPractice);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<CivilizedPractice>> getAll() {

        List<CivilizedPractice> list = iCivilizedPracticeService.list();
        return new ResultUtil<List<CivilizedPractice>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<CivilizedPractice>> getByPage(PageVo page) {

        IPage<CivilizedPractice> data = iCivilizedPracticeService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<CivilizedPractice>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<CivilizedPractice> saveOrUpdate(CivilizedPractice civilizedPractice) {

        if (iCivilizedPracticeService.saveOrUpdate(civilizedPractice)) {
            return new ResultUtil<CivilizedPractice>().setData(civilizedPractice);
        }
        return new ResultUtil<CivilizedPractice>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iCivilizedPracticeService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
