package cn.zwz.house.serviceimpl;

import cn.zwz.house.mapper.CustomerSellMapper;
import cn.zwz.house.entity.CustomerSell;
import cn.zwz.house.service.ICustomerSellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 房屋求购单 服务层接口实现

 */
@Slf4j
@Service
@Transactional
public class ICustomerSellServiceImpl extends ServiceImpl<CustomerSellMapper, CustomerSell> implements ICustomerSellService {

    @Autowired
    private CustomerSellMapper customerSellMapper;
}