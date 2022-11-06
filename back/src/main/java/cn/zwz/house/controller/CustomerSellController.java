package cn.zwz.house.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import cn.zwz.data.utils.HouseOrderChangeUtils;
import cn.zwz.data.utils.HouseVoChangeUtils;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.house.entity.CustomerLease;
import cn.zwz.house.entity.CustomerSell;
import cn.zwz.house.entity.HouseLease;
import cn.zwz.house.entity.HouseSell;
import cn.zwz.house.service.ICustomerSellService;
import cn.hutool.core.util.StrUtil;
import cn.zwz.house.service.IHouseSellOrderService;
import cn.zwz.house.service.IHouseSellService;
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
@Api(tags = "房屋求购单管理接口")
@RequestMapping("/zwz/customerSell")
@Transactional
public class CustomerSellController {

    @Autowired
    private ICustomerSellService iCustomerSellService;

    @Autowired
    private IHouseSellService iHouseSellService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IHouseSellOrderService iHouseSellOrderService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条房屋求购单")
    public Result<CustomerSell> get(@RequestParam String id){
        return new ResultUtil<CustomerSell>().setData(iCustomerSellService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋求购单个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iCustomerSellService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋求购单")
    public Result<List<CustomerSell>> getAll(){
        return new ResultUtil<List<CustomerSell>>().setData(iCustomerSellService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋求购单")
    public Result<IPage<CustomerSell>> getByPage(@ModelAttribute CustomerSell customerSell ,@ModelAttribute PageVo page){
        QueryWrapper<CustomerSell> qw = new QueryWrapper<>();
        if(customerSell.getStatus() > -1) {
            qw.eq("status",customerSell.getStatus());
        }
        if(!ZwzNullUtils.isNull(customerSell.getHouseType())) {
            qw.inSql("id","select id from t_customer_sell where house_id in (select id from t_house_sell where house_type like '%" + customerSell.getHouseType() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerSell.getAddress())) {
            qw.inSql("id","select id from t_customer_sell where house_id in (select id from t_house_sell where address like '%" + customerSell.getAddress() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerSell.getUserName())) {
            qw.inSql("id","select id from t_customer_sell where user_id in (select id from a_user where nickname like '%" + customerSell.getUserName() + "%')");
        }
        IPage<CustomerSell> data = iCustomerSellService.page(PageUtil.initMpPage(page),qw);
        for (CustomerSell sell : data.getRecords()) {
            HouseSell houseSell = iHouseSellService.getById(sell.getHouseId());
            sell = HouseVoChangeUtils.fillCustomerSellByHouseSell(sell,houseSell);
            User user = iUserService.getById(sell.getUserId());
            sell = HouseVoChangeUtils.fillCustomerSellByUser(sell,user);
        }
        return new ResultUtil<IPage<CustomerSell>>().setData(data);
    }

    @RequestMapping(value = "/getByMyPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询买家的房屋求购单")
    public Result<IPage<CustomerSell>> getByMyPage(@ModelAttribute CustomerSell customerSell ,@ModelAttribute PageVo page){
        QueryWrapper<CustomerSell> qw = new QueryWrapper<>();
        qw.eq("user_id",securityUtil.getCurrUser().getId());
        if(customerSell.getStatus() > -1) {
            qw.eq("status",customerSell.getStatus());
        }
        if(!ZwzNullUtils.isNull(customerSell.getHouseType())) {
            qw.inSql("id","select id from t_customer_sell where house_id in (select id from t_house_sell where house_type like '%" + customerSell.getHouseType() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerSell.getAddress())) {
            qw.inSql("id","select id from t_customer_sell where house_id in (select id from t_house_sell where address like '%" + customerSell.getAddress() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerSell.getUserName())) {
            qw.inSql("id","select id from t_customer_sell where user_id in (select id from a_user where nickname like '%" + customerSell.getUserName() + "%')");
        }
        IPage<CustomerSell> data = iCustomerSellService.page(PageUtil.initMpPage(page),qw);
        for (CustomerSell sell : data.getRecords()) {
            HouseSell houseSell = iHouseSellService.getById(sell.getHouseId());
            sell = HouseVoChangeUtils.fillCustomerSellByHouseSell(sell,houseSell);
            User user = iUserService.getById(sell.getUserId());
            sell = HouseVoChangeUtils.fillCustomerSellByUser(sell,user);
        }
        return new ResultUtil<IPage<CustomerSell>>().setData(data);
    }

    @RequestMapping(value = "/getBySellerPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询卖家的房屋求购单")
    public Result<IPage<CustomerSell>> getBySellerPage(@ModelAttribute CustomerSell customerSell ,@ModelAttribute PageVo page){
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<CustomerSell> qw = new QueryWrapper<>();
        qw.and(wrapper -> wrapper.eq("seller_id", currUser.getId()).or().inSql("seller_id","select id from a_user where mobile like '" + currUser.getMobile() + "'"));
        if(customerSell.getStatus() > -1) {
            qw.eq("status",customerSell.getStatus());
        }
        if(!ZwzNullUtils.isNull(customerSell.getHouseType())) {
            qw.inSql("id","select id from t_customer_sell where house_id in (select id from t_house_sell where house_type like '%" + customerSell.getHouseType() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerSell.getAddress())) {
            qw.inSql("id","select id from t_customer_sell where house_id in (select id from t_house_sell where address like '%" + customerSell.getAddress() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerSell.getUserName())) {
            qw.inSql("id","select id from t_customer_sell where user_id in (select id from a_user where nickname like '%" + customerSell.getUserName() + "%')");
        }
        IPage<CustomerSell> data = iCustomerSellService.page(PageUtil.initMpPage(page),qw);
        for (CustomerSell sell : data.getRecords()) {
            HouseSell houseSell = iHouseSellService.getById(sell.getHouseId());
            sell = HouseVoChangeUtils.fillCustomerSellByHouseSell(sell,houseSell);
            User user = iUserService.getById(sell.getUserId());
            sell = HouseVoChangeUtils.fillCustomerSellByUser(sell,user);
        }
        return new ResultUtil<IPage<CustomerSell>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改房屋求购单")
    public Result<CustomerSell> saveOrUpdate(CustomerSell customerSell){
        if(iCustomerSellService.saveOrUpdate(customerSell)){
            return new ResultUtil<CustomerSell>().setData(customerSell);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋求购单")
    public Result<CustomerSell> insert(CustomerSell customerSell){
        iCustomerSellService.saveOrUpdate(customerSell);
        return new ResultUtil<CustomerSell>().setData(customerSell);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑房屋求购单")
    public Result<CustomerSell> update(CustomerSell customerSell){
        iCustomerSellService.saveOrUpdate(customerSell);
        return new ResultUtil<CustomerSell>().setData(customerSell);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除房屋求购单")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iCustomerSellService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    @ApiOperation(value = "通过房屋求购单")
    public Result<Object> pass(@RequestParam String id){
        CustomerSell customerSell = iCustomerSellService.getById(id);
        if(customerSell == null) {
            return ResultUtil.error("求购单不存在");
        }
        customerSell.setStatus(1);
        iCustomerSellService.saveOrUpdate(customerSell);
        User user = iUserService.getById(customerSell.getUserId());
        if(user == null) {
            return ResultUtil.error("买家不存在");
        }
        User seller = iUserService.getById(customerSell.getSellerId());
        if(seller == null) {
            return ResultUtil.error("卖家不存在");
        }
        HouseSell houseSell = iHouseSellService.getById(customerSell.getHouseId());
        if(houseSell == null) {
            return ResultUtil.error("房屋不存在");
        }
        iHouseSellOrderService.saveOrUpdate(HouseOrderChangeUtils.changeSell(customerSell,user,seller,houseSell));
        return ResultUtil.success();
    }

    @RequestMapping(value = "/notPass", method = RequestMethod.POST)
    @ApiOperation(value = "驳回房屋求购单")
    public Result<Object> notPass(@RequestParam String id){
        CustomerSell customerSell = iCustomerSellService.getById(id);
        if(customerSell == null) {
            return ResultUtil.error("求购单不存在");
        }
        customerSell.setStatus(2);
        iCustomerSellService.saveOrUpdate(customerSell);
        return ResultUtil.success();
    }
}
