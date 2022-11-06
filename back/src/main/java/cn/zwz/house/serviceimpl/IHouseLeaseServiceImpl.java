package cn.zwz.house.serviceimpl;

import cn.zwz.house.mapper.HouseLeaseMapper;
import cn.zwz.house.entity.HouseLease;
import cn.zwz.house.service.IHouseLeaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房屋出租单 服务层接口实现

 */
@Slf4j
@Service
@Transactional
public class IHouseLeaseServiceImpl extends ServiceImpl<HouseLeaseMapper, HouseLease> implements IHouseLeaseService {

    @Autowired
    private HouseLeaseMapper houseLeaseMapper;
}