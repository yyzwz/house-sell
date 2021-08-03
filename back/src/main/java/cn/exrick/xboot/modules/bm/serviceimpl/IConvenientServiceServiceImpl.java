package cn.exrick.xboot.modules.bm.serviceimpl;

import cn.exrick.xboot.modules.bm.mapper.ConvenientServiceMapper;
import cn.exrick.xboot.modules.bm.entity.ConvenientService;
import cn.exrick.xboot.modules.bm.service.IConvenientServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 便民服务中心接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IConvenientServiceServiceImpl extends ServiceImpl<ConvenientServiceMapper, ConvenientService> implements IConvenientServiceService {

    @Autowired
    private ConvenientServiceMapper convenientServiceMapper;
}