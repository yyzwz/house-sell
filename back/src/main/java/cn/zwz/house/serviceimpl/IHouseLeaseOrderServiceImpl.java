package cn.zwz.house.serviceimpl;

import cn.zwz.house.mapper.HouseLeaseOrderMapper;
import cn.zwz.house.entity.HouseLeaseOrder;
import cn.zwz.house.service.IHouseLeaseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房屋租借交易单 服务层接口实现

 */
@Slf4j
@Service
@Transactional
public class IHouseLeaseOrderServiceImpl extends ServiceImpl<HouseLeaseOrderMapper, HouseLeaseOrder> implements IHouseLeaseOrderService {

    @Autowired
    private HouseLeaseOrderMapper houseLeaseOrderMapper;
}