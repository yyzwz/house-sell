package cn.zwz.house.serviceimpl;

import cn.zwz.house.mapper.CustomerLeaseMapper;
import cn.zwz.house.entity.CustomerLease;
import cn.zwz.house.service.ICustomerLeaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房屋求租单 服务层接口实现

 */
@Slf4j
@Service
@Transactional
public class ICustomerLeaseServiceImpl extends ServiceImpl<CustomerLeaseMapper, CustomerLease> implements ICustomerLeaseService {

    @Autowired
    private CustomerLeaseMapper customerLeaseMapper;
}