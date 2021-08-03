package cn.exrick.xboot.modules.zz.controller;

import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.modules.zz.entity.VillageAppointment;
import cn.exrick.xboot.modules.zz.service.IVillageAppointmentService;
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
@Api(tags = "村规民约管理接口")
@RequestMapping("/xboot/villageAppointment")
@Transactional
public class VillageAppointmentController {

    @Autowired
    private IVillageAppointmentService iVillageAppointmentService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取")
    public Result<VillageAppointment> get(@PathVariable String id) {

        VillageAppointment villageAppointment = iVillageAppointmentService.getById(id);
        return new ResultUtil<VillageAppointment>().setData(villageAppointment);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部数据")
    public Result<List<VillageAppointment>> getAll() {

        List<VillageAppointment> list = iVillageAppointmentService.list();
        return new ResultUtil<List<VillageAppointment>>().setData(list);
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取")
    public Result<IPage<VillageAppointment>> getByPage(PageVo page) {

        IPage<VillageAppointment> data = iVillageAppointmentService.page(PageUtil.initMpPage(page));
        return new ResultUtil<IPage<VillageAppointment>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "编辑或更新数据")
    public Result<VillageAppointment> saveOrUpdate(VillageAppointment villageAppointment) {

        if (iVillageAppointmentService.saveOrUpdate(villageAppointment)) {
            return new ResultUtil<VillageAppointment>().setData(villageAppointment);
        }
        return new ResultUtil<VillageAppointment>().setErrorMsg("操作失败");
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过id删除")
    public Result<Object> delAllByIds(@RequestParam String[] ids) {

        for (String id : ids) {
            iVillageAppointmentService.removeById(id);
        }
        return ResultUtil.success("批量通过id删除数据成功");
    }
}
