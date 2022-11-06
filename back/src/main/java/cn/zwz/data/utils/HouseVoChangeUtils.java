package cn.zwz.data.utils;

import cn.zwz.data.entity.User;
import cn.zwz.house.entity.CustomerLease;
import cn.zwz.house.entity.CustomerSell;
import cn.zwz.house.entity.HouseLease;
import cn.zwz.house.entity.HouseSell;

import java.math.BigDecimal;

public class HouseVoChangeUtils {

    public static final String NONE_STR = "无";

    public static CustomerLease fillCustomerLeaseByUser(CustomerLease cl, User user) {
        if(user == null) {
            cl.setUserName(NONE_STR);
            cl.setUserMobile(NONE_STR);
            return cl;
        }
        cl.setUserName(user.getNickname());
        cl.setUserMobile(user.getMobile());
        return cl;
    }

    public static CustomerSell fillCustomerSellByUser(CustomerSell cs, User user) {
        if(user == null) {
            cs.setUserName(NONE_STR);
            cs.setUserMobile(NONE_STR);
            return cs;
        }
        cs.setUserName(user.getNickname());
        cs.setUserMobile(user.getMobile());
        return cs;
    }

    public static CustomerLease fillCustomerLeaseByHouseLease(CustomerLease cl, HouseLease hl) {
        if(hl == null) {
            cl.setSellerId(NONE_STR);
            cl.setHouseType(NONE_STR);
            cl.setHouseSize(BigDecimal.ZERO);
            cl.setInitMoney(BigDecimal.ZERO);
            cl.setMobile(NONE_STR);
            cl.setHouseOwner(NONE_STR);
            cl.setAddress(NONE_STR);
            cl.setRemark(NONE_STR);
            return cl;
        }
        cl.setSellerId(hl.getSellerId());
        cl.setHouseType(hl.getHouseType());
        cl.setHouseSize(hl.getHouseSize());
        cl.setInitMoney(hl.getInitMoney());
        cl.setMobile(hl.getMobile());
        cl.setHouseOwner(hl.getHouseOwner());
        cl.setAddress(hl.getAddress());
        cl.setRemark(hl.getRemark());
        return cl;
    }

    public static CustomerSell fillCustomerSellByHouseSell(CustomerSell cs, HouseSell hs) {
        if(hs == null) {
            cs.setSellerId(NONE_STR);
            cs.setHouseType(NONE_STR);
            cs.setHouseSize(BigDecimal.ZERO);
            cs.setMobile(NONE_STR);
            cs.setHouseMoney(BigDecimal.ZERO);
            cs.setHouseOwner(NONE_STR);
            cs.setAddress(NONE_STR);
            cs.setRemark(NONE_STR);
            return cs;
        }
        cs.setSellerId(hs.getSellerId());
        cs.setHouseType(hs.getHouseType());
        cs.setHouseSize(hs.getHouseSize());
        cs.setMobile(hs.getMobile());
        cs.setHouseMoney(hs.getHouseMoney());
        cs.setHouseOwner(hs.getHouseOwner());
        cs.setAddress(hs.getAddress());
        cs.setRemark(hs.getRemark());
        return cs;
    }
}
