package cn.zwz.house.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.house.entity.CustomerSell;
import cn.zwz.house.entity.HouseSell;
import cn.zwz.house.service.ICustomerSellService;
import cn.zwz.house.service.IHouseSellService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 郑为中
 * Q：916077357
 */
@Slf4j
@RestController
@Api(tags = "房屋出售单管理接口")
@RequestMapping("/zwz/houseSell")
@Transactional
public class HouseSellController {

    @Autowired
    private IHouseSellService iHouseSellService;

    @Autowired
    private ICustomerSellService iCustomerSellService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条房屋出售单")
    public Result<HouseSell> get(@RequestParam String id){
        return new ResultUtil<HouseSell>().setData(iHouseSellService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋出售单个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iHouseSellService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋出售单")
    public Result<List<HouseSell>> getAll(){
        return new ResultUtil<List<HouseSell>>().setData(iHouseSellService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋出售单")
    public Result<IPage<HouseSell>> getByPage(@ModelAttribute HouseSell houseSell ,@ModelAttribute PageVo page){
        QueryWrapper<HouseSell> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseSell.getHouseType())) {
            qw.eq("house_type",houseSell.getHouseType());
        }
        if(!ZwzNullUtils.isNull(houseSell.getMobile())) {
            qw.like("mobile",houseSell.getMobile());
        }
        if(!ZwzNullUtils.isNull(houseSell.getAddress())) {
            qw.like("address",houseSell.getAddress());
        }
        if(houseSell.getStatus() < 0) {
            qw.eq("status",0);
        }
        IPage<HouseSell> data = iHouseSellService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseSell>>().setData(data);
    }

    @RequestMapping(value = "/getBySeller", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋出售单")
    public Result<IPage<HouseSell>> getBySeller(@ModelAttribute HouseSell houseSell ,@ModelAttribute PageVo page){
        QueryWrapper<HouseSell> qw = new QueryWrapper<>();
        qw.eq("seller_id",securityUtil.getCurrUser().getId());
        if(!ZwzNullUtils.isNull(houseSell.getHouseType())) {
            qw.eq("house_type",houseSell.getHouseType());
        }
        if(!ZwzNullUtils.isNull(houseSell.getMobile())) {
            qw.like("mobile",houseSell.getMobile());
        }
        if(!ZwzNullUtils.isNull(houseSell.getAddress())) {
            qw.like("address",houseSell.getAddress());
        }
        IPage<HouseSell> data = iHouseSellService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseSell>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改房屋出售单")
    public Result<HouseSell> saveOrUpdate(HouseSell houseSell){
        if(iHouseSellService.saveOrUpdate(houseSell)){
            return new ResultUtil<HouseSell>().setData(houseSell);
        }
        return ResultUtil.error();
    }

@RequestMapping(value = "/insert", method = RequestMethod.POST)
@ApiOperation(value = "新增房屋出售单")
public Result<HouseSell> insert(HouseSell houseSell) {
    houseSell.setSellerId(securityUtil.getCurrUser().getId());
    String mobile = houseSell.getMobile();
    if(!ZwzNullUtils.isNull(mobile)) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("mobile",mobile);
        List<User> userList = iUserService.list(qw);
        if(userList.size() > 0) {
            houseSell.setSellerId(userList.get(0).getId());
        }
    }
    iHouseSellService.saveOrUpdate(houseSell);
    return new ResultUtil<HouseSell>().setData(houseSell);
}

    @RequestMapping(value = "/insertBySeller", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋出售单")
    public Result<HouseSell> insertBySell(HouseSell houseSell) {
        houseSell.setSellerId(securityUtil.getCurrUser().getId());
        houseSell.setStatus(1);
        iHouseSellService.saveOrUpdate(houseSell);
        return new ResultUtil<HouseSell>().setData(houseSell);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑房屋出售单")
    public Result<HouseSell> update(HouseSell houseSell){
        iHouseSellService.saveOrUpdate(houseSell);
        return new ResultUtil<HouseSell>().setData(houseSell);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除房屋出售单")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iHouseSellService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    @ApiOperation(value = "审核")
    public Result<Object> pass(@RequestParam String id){
        HouseSell se = iHouseSellService.getById(id);
        if(se == null) {
            return ResultUtil.error("不存在");
        }
        se.setStatus(0);
        iHouseSellService.saveOrUpdate(se);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/notPass", method = RequestMethod.POST)
    @ApiOperation(value = "审核驳回")
    public Result<Object> notPass(@RequestParam String id){
        iHouseSellService.removeById(id);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/frontBuy", method = RequestMethod.POST)
    @ApiOperation(value = "销售意向预定")
    public Result<Object> frontBuy(@RequestParam String id,@RequestParam float money,@RequestParam String remark){
        HouseSell houseSell = iHouseSellService.getById(id);
        if(houseSell == null) {
            return ResultUtil.error("房屋出售单不存在");
        }
        User currUser = securityUtil.getCurrUser();
        CustomerSell sell = new CustomerSell();
        sell.setSellerId(houseSell.getSellerId());
        sell.setHouseId(houseSell.getId());
        sell.setUserId(currUser.getId());
        sell.setStatus(0);
        sell.setUserMoney(BigDecimal.valueOf(money));
        sell.setUserRemark(remark);
        iCustomerSellService.saveOrUpdate(sell);
        return ResultUtil.success();
    }
}
