package cn.zwz.house.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.house.entity.HouseLeaseOrder;
import cn.zwz.house.entity.HouseSellOrder;
import cn.zwz.house.service.IHouseLeaseOrderService;
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
 * Q：916077357
 */
@Slf4j
@RestController
@Api(tags = "房屋租借交易单管理接口")
@RequestMapping("/zwz/houseLeaseOrder")
@Transactional
public class HouseLeaseOrderController {

    @Autowired
    private IHouseLeaseOrderService iHouseLeaseOrderService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条房屋租借交易单")
    public Result<HouseLeaseOrder> get(@RequestParam String id){
        return new ResultUtil<HouseLeaseOrder>().setData(iHouseLeaseOrderService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋租借交易单个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iHouseLeaseOrderService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋租借交易单")
    public Result<List<HouseLeaseOrder>> getAll(){
        return new ResultUtil<List<HouseLeaseOrder>>().setData(iHouseLeaseOrderService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋租借交易单")
    public Result<IPage<HouseLeaseOrder>> getByPage(@ModelAttribute HouseLeaseOrder houseLeaseOrder ,@ModelAttribute PageVo page){
        QueryWrapper<HouseLeaseOrder> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseLeaseOrder.getHouseOwner())) {
            qw.like("house_owner",houseLeaseOrder.getHouseOwner());
        }
        if(!ZwzNullUtils.isNull(houseLeaseOrder.getAddress())) {
            qw.like("address",houseLeaseOrder.getAddress());
        }
        IPage<HouseLeaseOrder> data = iHouseLeaseOrderService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseLeaseOrder>>().setData(data);
    }

    @RequestMapping(value = "/getBySellPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询卖家房屋租借交易单")
    public Result<IPage<HouseLeaseOrder>> getBySellPage(@ModelAttribute HouseLeaseOrder houseLeaseOrder ,@ModelAttribute PageVo page){
        QueryWrapper<HouseLeaseOrder> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseLeaseOrder.getHouseOwner())) {
            qw.like("house_owner",houseLeaseOrder.getHouseOwner());
        }
        if(!ZwzNullUtils.isNull(houseLeaseOrder.getAddress())) {
            qw.like("address",houseLeaseOrder.getAddress());
        }
        qw.eq("seller_id",securityUtil.getCurrUser().getId());
        IPage<HouseLeaseOrder> data = iHouseLeaseOrderService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseLeaseOrder>>().setData(data);
    }

    @RequestMapping(value = "/getByBuyPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询卖家房屋租借交易单")
    public Result<IPage<HouseLeaseOrder>> getByBuyPage(@ModelAttribute HouseLeaseOrder houseLeaseOrder ,@ModelAttribute PageVo page){
        QueryWrapper<HouseLeaseOrder> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseLeaseOrder.getHouseOwner())) {
            qw.like("house_owner",houseLeaseOrder.getHouseOwner());
        }
        if(!ZwzNullUtils.isNull(houseLeaseOrder.getAddress())) {
            qw.like("address",houseLeaseOrder.getAddress());
        }
        qw.eq("user_id",securityUtil.getCurrUser().getId());
        IPage<HouseLeaseOrder> data = iHouseLeaseOrderService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseLeaseOrder>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改房屋租借交易单")
    public Result<HouseLeaseOrder> saveOrUpdate(HouseLeaseOrder houseLeaseOrder){
        if(iHouseLeaseOrderService.saveOrUpdate(houseLeaseOrder)){
            return new ResultUtil<HouseLeaseOrder>().setData(houseLeaseOrder);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋租借交易单")
    public Result<HouseLeaseOrder> insert(HouseLeaseOrder houseLeaseOrder){
        iHouseLeaseOrderService.saveOrUpdate(houseLeaseOrder);
        return new ResultUtil<HouseLeaseOrder>().setData(houseLeaseOrder);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑房屋租借交易单")
    public Result<HouseLeaseOrder> update(HouseLeaseOrder houseLeaseOrder){
        iHouseLeaseOrderService.saveOrUpdate(houseLeaseOrder);
        return new ResultUtil<HouseLeaseOrder>().setData(houseLeaseOrder);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除房屋租借交易单")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iHouseLeaseOrderService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ApiOperation(value = "支付")
    public Result<Object> delByIds(@RequestParam String id) {
        HouseLeaseOrder order = iHouseLeaseOrderService.getById(id);
        if(order == null) {
            return ResultUtil.error("出租单不存在");
        }
        order.setStatus(1);
        iHouseLeaseOrderService.saveOrUpdate(order);
        return ResultUtil.success();
    }
}
