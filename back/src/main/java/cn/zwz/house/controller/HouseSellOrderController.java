package cn.zwz.house.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.house.entity.HouseSellOrder;
import cn.zwz.house.service.IHouseSellOrderService;
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
@Api(tags = "房屋销售交易单管理接口")
@RequestMapping("/zwz/houseSellOrder")
@Transactional
public class HouseSellOrderController {

    @Autowired
    private IHouseSellOrderService iHouseSellOrderService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条房屋销售交易单")
    public Result<HouseSellOrder> get(@RequestParam String id){
        return new ResultUtil<HouseSellOrder>().setData(iHouseSellOrderService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋销售交易单个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iHouseSellOrderService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋销售交易单")
    public Result<List<HouseSellOrder>> getAll(){
        return new ResultUtil<List<HouseSellOrder>>().setData(iHouseSellOrderService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋销售交易单")
    public Result<IPage<HouseSellOrder>> getByPage(@ModelAttribute HouseSellOrder houseSellOrder ,@ModelAttribute PageVo page){
        QueryWrapper<HouseSellOrder> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseSellOrder.getAddress())) {
            qw.like("address",houseSellOrder.getAddress());
        }
        if(!ZwzNullUtils.isNull(houseSellOrder.getHouseOwner())) {
            qw.like("house_owner",houseSellOrder.getHouseOwner());
        }
        IPage<HouseSellOrder> data = iHouseSellOrderService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseSellOrder>>().setData(data);
    }

    @RequestMapping(value = "/getBySellPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询卖家房屋销售交易单")
    public Result<IPage<HouseSellOrder>> getBySellPage(@ModelAttribute HouseSellOrder houseSellOrder ,@ModelAttribute PageVo page){
        QueryWrapper<HouseSellOrder> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseSellOrder.getAddress())) {
            qw.like("address",houseSellOrder.getAddress());
        }
        if(!ZwzNullUtils.isNull(houseSellOrder.getHouseOwner())) {
            qw.like("house_owner",houseSellOrder.getHouseOwner());
        }
        qw.eq("seller_id",securityUtil.getCurrUser().getId());
        IPage<HouseSellOrder> data = iHouseSellOrderService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseSellOrder>>().setData(data);
    }

    @RequestMapping(value = "/getByBuyPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询买家房屋销售交易单")
    public Result<IPage<HouseSellOrder>> getByBuyPage(@ModelAttribute HouseSellOrder houseSellOrder ,@ModelAttribute PageVo page){
        QueryWrapper<HouseSellOrder> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseSellOrder.getAddress())) {
            qw.like("address",houseSellOrder.getAddress());
        }
        if(!ZwzNullUtils.isNull(houseSellOrder.getHouseOwner())) {
            qw.like("house_owner",houseSellOrder.getHouseOwner());
        }
        qw.eq("user_id",securityUtil.getCurrUser().getId());
        IPage<HouseSellOrder> data = iHouseSellOrderService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseSellOrder>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改房屋销售交易单")
    public Result<HouseSellOrder> saveOrUpdate(HouseSellOrder houseSellOrder){
        if(iHouseSellOrderService.saveOrUpdate(houseSellOrder)){
            return new ResultUtil<HouseSellOrder>().setData(houseSellOrder);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋销售交易单")
    public Result<HouseSellOrder> insert(HouseSellOrder houseSellOrder){
        iHouseSellOrderService.saveOrUpdate(houseSellOrder);
        return new ResultUtil<HouseSellOrder>().setData(houseSellOrder);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑房屋销售交易单")
    public Result<HouseSellOrder> update(HouseSellOrder houseSellOrder){
        iHouseSellOrderService.saveOrUpdate(houseSellOrder);
        return new ResultUtil<HouseSellOrder>().setData(houseSellOrder);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除房屋销售交易单")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iHouseSellOrderService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ApiOperation(value = "支付")
    public Result<Object> delByIds(@RequestParam String id) {
        HouseSellOrder order = iHouseSellOrderService.getById(id);
        if(order == null) {
            return ResultUtil.error("销售单不存在");
        }
        order.setStatus(1);
        iHouseSellOrderService.saveOrUpdate(order);
        return ResultUtil.success();
    }
}
