package cn.exrick.xboot.modules.fz.serviceimpl;

import cn.exrick.xboot.modules.fz.mapper.UniversalLawMapper;
import cn.exrick.xboot.modules.fz.entity.UniversalLaw;
import cn.exrick.xboot.modules.fz.service.IUniversalLawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 普法宣传接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IUniversalLawServiceImpl extends ServiceImpl<UniversalLawMapper, UniversalLaw> implements IUniversalLawService {

    @Autowired
    private UniversalLawMapper universalLawMapper;
}