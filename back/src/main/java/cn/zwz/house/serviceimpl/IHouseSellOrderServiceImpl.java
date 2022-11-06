package cn.zwz.house.serviceimpl;

import cn.zwz.house.mapper.HouseSellOrderMapper;
import cn.zwz.house.entity.HouseSellOrder;
import cn.zwz.house.service.IHouseSellOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房屋销售交易单 服务层接口实现

 */
@Slf4j
@Service
@Transactional
public class IHouseSellOrderServiceImpl extends ServiceImpl<HouseSellOrderMapper, HouseSellOrder> implements IHouseSellOrderService {

    @Autowired
    private HouseSellOrderMapper houseSellOrderMapper;
}