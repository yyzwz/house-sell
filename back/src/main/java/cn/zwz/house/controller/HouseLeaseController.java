package cn.zwz.house.controller;

import cn.zwz.basics.utils.PageUtil;
import cn.zwz.basics.utils.ResultUtil;
import cn.zwz.basics.baseVo.PageVo;
import cn.zwz.basics.baseVo.Result;
import cn.zwz.basics.utils.SecurityUtil;
import cn.zwz.data.entity.User;
import cn.zwz.data.service.IUserService;
import cn.zwz.data.utils.ZwzNullUtils;
import cn.zwz.house.entity.CustomerLease;
import cn.zwz.house.entity.HouseLease;
import cn.zwz.house.entity.HouseSell;
import cn.zwz.house.service.ICustomerLeaseService;
import cn.zwz.house.service.IHouseLeaseService;
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
@Api(tags = "房屋出租单管理接口")
@RequestMapping("/zwz/houseLease")
@Transactional
public class HouseLeaseController {

    @Autowired
    private IHouseLeaseService iHouseLeaseService;

    @Autowired
    private ICustomerLeaseService iCustomerLeaseService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条房屋出租单")
    public Result<HouseLease> get(@RequestParam String id){
        return new ResultUtil<HouseLease>().setData(iHouseLeaseService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋出租单个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iHouseLeaseService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部房屋出租单")
    public Result<List<HouseLease>> getAll(){
        return new ResultUtil<List<HouseLease>>().setData(iHouseLeaseService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋出租单")
    public Result<IPage<HouseLease>> getByPage(@ModelAttribute HouseLease houseLease ,@ModelAttribute PageVo page){
        QueryWrapper<HouseLease> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(houseLease.getHouseType())) {
            qw.eq("house_type",houseLease.getHouseType());
        }
        if(!ZwzNullUtils.isNull(houseLease.getMobile())) {
            qw.like("mobile",houseLease.getMobile());
        }
        if(!ZwzNullUtils.isNull(houseLease.getAddress())) {
            qw.like("address",houseLease.getAddress());
        }
        if(houseLease.getStatus() < 0) {
            qw.eq("status",0);
        }
        IPage<HouseLease> data = iHouseLeaseService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseLease>>().setData(data);
    }

    @RequestMapping(value = "/getBySeller", method = RequestMethod.GET)
    @ApiOperation(value = "查询房屋出租单")
    public Result<IPage<HouseLease>> getBySeller(@ModelAttribute HouseLease houseLease ,@ModelAttribute PageVo page){
        QueryWrapper<HouseLease> qw = new QueryWrapper<>();
        qw.eq("seller_id",securityUtil.getCurrUser().getId());
        if(!ZwzNullUtils.isNull(houseLease.getHouseType())) {
            qw.eq("house_type",houseLease.getHouseType());
        }
        if(!ZwzNullUtils.isNull(houseLease.getMobile())) {
            qw.like("mobile",houseLease.getMobile());
        }
        if(!ZwzNullUtils.isNull(houseLease.getAddress())) {
            qw.like("address",houseLease.getAddress());
        }
        IPage<HouseLease> data = iHouseLeaseService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<HouseLease>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改房屋出租单")
    public Result<HouseLease> saveOrUpdate(HouseLease houseLease){
        if(iHouseLeaseService.saveOrUpdate(houseLease)){
            return new ResultUtil<HouseLease>().setData(houseLease);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋出租单")
    public Result<HouseLease> insert(HouseLease houseLease) {
        houseLease.setSellerId(securityUtil.getCurrUser().getId());
        String mobile = houseLease.getMobile();
        if(!ZwzNullUtils.isNull(mobile)) {
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("mobile",mobile);
            List<User> userList = iUserService.list(qw);
            if(userList.size() > 0) {
                houseLease.setSellerId(userList.get(0).getId());
            }
        }
        iHouseLeaseService.saveOrUpdate(houseLease);
        return new ResultUtil<HouseLease>().setData(houseLease);
    }

    @RequestMapping(value = "/insertBySeller", method = RequestMethod.POST)
    @ApiOperation(value = "新增房屋出租单")
    public Result<HouseLease> insertBySeller(HouseLease houseLease) {
        houseLease.setSellerId(securityUtil.getCurrUser().getId());
        houseLease.setStatus(1);
        iHouseLeaseService.saveOrUpdate(houseLease);
        return new ResultUtil<HouseLease>().setData(houseLease);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑房屋出租单")
    public Result<HouseLease> update(HouseLease houseLease){
        iHouseLeaseService.saveOrUpdate(houseLease);
        return new ResultUtil<HouseLease>().setData(houseLease);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除房屋出租单")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iHouseLeaseService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    @ApiOperation(value = "审核")
    public Result<Object> pass(@RequestParam String id){
        HouseLease se = iHouseLeaseService.getById(id);
        if(se == null) {
            return ResultUtil.error("不存在");
        }
        se.setStatus(0);
        iHouseLeaseService.saveOrUpdate(se);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/notPass", method = RequestMethod.POST)
    @ApiOperation(value = "审核驳回")
    public Result<Object> notPass(@RequestParam String id){
        iHouseLeaseService.removeById(id);
        return ResultUtil.success();
    }

    @RequestMapping(value = "/frontLease", method = RequestMethod.POST)
    @ApiOperation(value = "出租意向预定")
    public Result<Object> frontLease(@RequestParam String id,@RequestParam float money,@RequestParam String remark){
        HouseLease houseLease = iHouseLeaseService.getById(id);
        if(houseLease == null) {
            return ResultUtil.error("房屋出租单不存在");
        }
        User currUser = securityUtil.getCurrUser();
        CustomerLease lease = new CustomerLease();
        lease.setSellerId(houseLease.getSellerId());
        lease.setHouseId(houseLease.getId());
        lease.setUserId(currUser.getId());
        lease.setStatus(0);
        lease.setUserMoney(BigDecimal.valueOf(money));
        lease.setUserRemark(remark);
        iCustomerLeaseService.saveOrUpdate(lease);
        return ResultUtil.success();
    }
}
