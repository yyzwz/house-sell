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
import cn.zwz.house.entity.HouseLease;
import cn.zwz.house.service.ICustomerLeaseService;
import cn.hutool.core.util.StrUtil;
import cn.zwz.house.service.IHouseLeaseOrderService;
import cn.zwz.house.service.IHouseLeaseService;
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
@Api(tags = "房屋求租单管理接口")
@RequestMapping("/zwz/customerLease")
@Transactional
public class CustomerLeaseController {

    @Autowired
    private ICustomerLeaseService iCustomerLeaseService;

    @Autowired
    private IHouseLeaseService iHouseLeaseService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IHouseLeaseOrderService iHouseLeaseOrderService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条房屋求租单")
    public Result<CustomerLease> get(@RequestParam String id){
        return new ResultUtil<CustomerLease>().setData(iCustomerLeaseService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋求租单个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iCustomerLeaseService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋求租单")
    public Result<List<CustomerLease>> getAll(){
        return new ResultUtil<List<CustomerLease>>().setData(iCustomerLeaseService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋求租单")
    public Result<IPage<CustomerLease>> getByPage(@ModelAttribute CustomerLease customerLease ,@ModelAttribute PageVo page){
        QueryWrapper<CustomerLease> qw = new QueryWrapper<>();
        if(customerLease.getStatus() > -1) {
            qw.eq("status",customerLease.getStatus());
        }
        if(!ZwzNullUtils.isNull(customerLease.getHouseType())) {
            qw.inSql("id","select id from t_customer_lease where house_id in (select id from t_house_lease where house_type like '%" + customerLease.getHouseType() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerLease.getAddress())) {
            qw.inSql("id","select id from t_customer_lease where house_id in (select id from t_house_lease where address like '%" + customerLease.getAddress() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerLease.getUserName())) {
            qw.inSql("id","select id from t_customer_lease where user_id in (select id from a_user where nickname like '%" + customerLease.getUserName() + "%')");
        }
        IPage<CustomerLease> data = iCustomerLeaseService.page(PageUtil.initMpPage(page),qw);
        for (CustomerLease lease : data.getRecords()) {
            HouseLease houseLease = iHouseLeaseService.getById(lease.getHouseId());
            lease = HouseVoChangeUtils.fillCustomerLeaseByHouseLease(lease,houseLease);
            User user = iUserService.getById(lease.getUserId());
            lease = HouseVoChangeUtils.fillCustomerLeaseByUser(lease,user);
        }
        return new ResultUtil<IPage<CustomerLease>>().setData(data);
    }

    @RequestMapping(value = "/getByMyPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询买家的房屋求租单")
    public Result<IPage<CustomerLease>> getByMyPage(@ModelAttribute CustomerLease customerLease ,@ModelAttribute PageVo page){
        QueryWrapper<CustomerLease> qw = new QueryWrapper<>();
        qw.eq("user_id",securityUtil.getCurrUser().getId());
        if(customerLease.getStatus() > -1) {
            qw.eq("status",customerLease.getStatus());
        }
        if(!ZwzNullUtils.isNull(customerLease.getHouseType())) {
            qw.inSql("id","select id from t_customer_lease where house_id in (select id from t_house_lease where house_type like '%" + customerLease.getHouseType() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerLease.getAddress())) {
            qw.inSql("id","select id from t_customer_lease where house_id in (select id from t_house_lease where address like '%" + customerLease.getAddress() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerLease.getUserName())) {
            qw.inSql("id","select id from t_customer_lease where user_id in (select id from a_user where nickname like '%" + customerLease.getUserName() + "%')");
        }
        IPage<CustomerLease> data = iCustomerLeaseService.page(PageUtil.initMpPage(page),qw);
        for (CustomerLease lease : data.getRecords()) {
            HouseLease houseLease = iHouseLeaseService.getById(lease.getHouseId());
            lease = HouseVoChangeUtils.fillCustomerLeaseByHouseLease(lease,houseLease);
            User user = iUserService.getById(lease.getUserId());
            lease = HouseVoChangeUtils.fillCustomerLeaseByUser(lease,user);
        }
        return new ResultUtil<IPage<CustomerLease>>().setData(data);
    }

    @RequestMapping(value = "/getBySellerPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询卖家的房屋求租单")
    public Result<IPage<CustomerLease>> getBySellerPage(@ModelAttribute CustomerLease customerLease ,@ModelAttribute PageVo page){
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<CustomerLease> qw = new QueryWrapper<>();
        qw.and(wrapper -> wrapper.eq("seller_id", currUser.getId()).or().inSql("seller_id","select id from a_user where mobile like '" + currUser.getMobile() + "'"));
        if(customerLease.getStatus() > -1) {
            qw.eq("status",customerLease.getStatus());
        }
        if(!ZwzNullUtils.isNull(customerLease.getHouseType())) {
            qw.inSql("id","select id from t_customer_lease where house_id in (select id from t_house_lease where house_type like '%" + customerLease.getHouseType() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerLease.getAddress())) {
            qw.inSql("id","select id from t_customer_lease where house_id in (select id from t_house_lease where address like '%" + customerLease.getAddress() + "%')");
        }
        if(!ZwzNullUtils.isNull(customerLease.getUserName())) {
            qw.inSql("id","select id from t_customer_lease where user_id in (select id from a_user where nickname like '%" + customerLease.getUserName() + "%')");
        }
        IPage<CustomerLease> data = iCustomerLeaseService.page(PageUtil.initMpPage(page),qw);
        for (CustomerLease lease : data.getRecords()) {
            HouseLease houseLease = iHouseLeaseService.getById(lease.getHouseId());
            lease = HouseVoChangeUtils.fillCustomerLeaseByHouseLease(lease,houseLease);
            User user = iUserService.getById(lease.getUserId());
            lease = HouseVoChangeUtils.fillCustomerLeaseByUser(lease,user);
        }
        return new ResultUtil<IPage<CustomerLease>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改房屋求租单")
    public Result<CustomerLease> saveOrUpdate(CustomerLease customerLease){
        if(iCustomerLeaseService.saveOrUpdate(customerLease)){
            return new ResultUtil<CustomerLease>().setData(customerLease);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋求租单")
    public Result<CustomerLease> insert(CustomerLease customerLease){
        iCustomerLeaseService.saveOrUpdate(customerLease);
        return new ResultUtil<CustomerLease>().setData(customerLease);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑房屋求租单")
    public Result<CustomerLease> update(CustomerLease customerLease){
        iCustomerLeaseService.saveOrUpdate(customerLease);
        return new ResultUtil<CustomerLease>().setData(customerLease);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除房屋求租单")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iCustomerLeaseService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    @ApiOperation(value = "通过房屋求租单")
    public Result<Object> pass(@RequestParam String id){
        CustomerLease customerLease = iCustomerLeaseService.getById(id);
        if(customerLease == null) {
            return ResultUtil.error("求租单不存在");
        }
        customerLease.setStatus(1);
        iCustomerLeaseService.saveOrUpdate(customerLease);
        User user = iUserService.getById(customerLease.getUserId());
        if(user == null) {
            return ResultUtil.error("买家不存在");
        }
        User seller = iUserService.getById(customerLease.getSellerId());
        if(seller == null) {
            return ResultUtil.error("卖家不存在");
        }
        HouseLease houseLease = iHouseLeaseService.getById(customerLease.getHouseId());
        if(houseLease == null) {
            return ResultUtil.error("房屋不存在");
        }
        iHouseLeaseOrderService.saveOrUpdate(HouseOrderChangeUtils.changeLease(customerLease,user,seller,houseLease));
        return ResultUtil.success();
    }

    @RequestMapping(value = "/notPass", method = RequestMethod.POST)
    @ApiOperation(value = "驳回房屋求租单")
    public Result<Object> notPass(@RequestParam String id){
        CustomerLease customerLease = iCustomerLeaseService.getById(id);
        if(customerLease == null) {
            return ResultUtil.error("求租单不存在");
        }
        customerLease.setStatus(2);
        iCustomerLeaseService.saveOrUpdate(customerLease);
        return ResultUtil.success();
    }
}
