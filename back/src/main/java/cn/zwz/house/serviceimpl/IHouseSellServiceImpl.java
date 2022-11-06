package cn.zwz.house.serviceimpl;

import cn.zwz.house.mapper.HouseSellMapper;
import cn.zwz.house.entity.HouseSell;
import cn.zwz.house.service.IHouseSellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房屋出售单 服务层接口实现

 */
@Slf4j
@Service
@Transactional
public class IHouseSellServiceImpl extends ServiceImpl<HouseSellMapper, HouseSell> implements IHouseSellService {

    @Autowired
    private HouseSellMapper houseSellMapper;
}