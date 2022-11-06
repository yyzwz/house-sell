package cn.zwz.data.utils;

import cn.zwz.data.entity.User;
import cn.zwz.house.entity.*;

public class HouseOrderChangeUtils {

    public static HouseLeaseOrder changeLease(CustomerLease cl, User user,User seller,HouseLease houseLease) {
        HouseLeaseOrder order = new HouseLeaseOrder();
        order.setSellerId(cl.getSellerId());
        order.setHouseId(houseLease.getId());
        order.setHouseType(houseLease.getHouseType());
        order.setHouseSize(houseLease.getHouseSize());
        order.setHouseMoney(houseLease.getHouseMoney());
        order.setInitMoney(houseLease.getInitMoney());
        order.setMobile(seller.getMobile());
        order.setHouseOwner(seller.getUsername());
        order.setAddress(houseLease.getAddress());
        order.setRemark(houseLease.getRemark());
        order.setUserId(user.getId());
        order.setUserName(user.getNickname());
        order.setUserMobile(user.getMobile());
        order.setUserMoney(cl.getUserMoney());
        order.setUserRemark(cl.getUserRemark());
        order.setStatus(0);
        return order;
    }

    public static HouseSellOrder changeSell(CustomerSell cs,User user,User seller,HouseSell houseSell) {
        HouseSellOrder order = new HouseSellOrder();
        order.setSellerId(seller.getId());
        order.setHouseId(houseSell.getId());
        order.setHouseType(houseSell.getHouseType());
        order.setHouseSize(houseSell.getHouseSize());
        order.setHouseMoney(houseSell.getHouseMoney());
        order.setMobile(houseSell.getMobile());
        order.setHouseOwner(houseSell.getHouseOwner());
        order.setAddress(houseSell.getAddress());
        order.setRemark(houseSell.getRemark());
        order.setUserId(user.getId());
        order.setUserName(user.getNickname());
        order.setUserMobile(user.getMobile());
        order.setUserMoney(cs.getUserMoney());
        order.setUserRemark(cs.getUserRemark());
        order.setStatus(0);
        return order;
    }
}
