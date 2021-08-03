package cn.exrick.xboot.modules.bm.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.bm.entity.ConvenientService;
import cn.exrick.xboot.modules.bm.service.IConvenientServiceService;
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
@Api(tags = "便民服务中心管理接口")
@RequestMapping("/xboot/convenientService")
@Transactional
public class ConvenientServiceController {

    @Autowired
    private IConvenientServiceService iConvenientServiceService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<ConvenientService> get(@PathVariable String id) {

        ConvenientService convenientService = iConvenientServiceService.getById(id);
        return new ResultUtil<ConvenientService>().setData(convenientService);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<ConvenientService>> getAll() {

        List<ConvenientService> list = iConvenientServiceService.list();
        return new ResultUtil<List<ConvenientService>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<ConvenientService>> getByPage(PageVo page) {

        IPage<ConvenientService> data = iConvenientServiceService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<ConvenientService>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<ConvenientService> saveOrUpdate(ConvenientService convenientService) {

        if (iConvenientServiceService.saveOrUpdate(convenientService)) {
            return new ResultUtil<ConvenientService>().setData(convenientService);
        }
        return new ResultUtil<ConvenientService>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iConvenientServiceService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
