package cn.exrick.xboot.modules.zz.serviceimpl;

import cn.exrick.xboot.modules.zz.mapper.GovernanceEffectivenessMapper;
import cn.exrick.xboot.modules.zz.entity.GovernanceEffectiveness;
import cn.exrick.xboot.modules.zz.service.IGovernanceEffectivenessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 治理成效接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IGovernanceEffectivenessServiceImpl extends ServiceImpl<GovernanceEffectivenessMapper, GovernanceEffectiveness> implements IGovernanceEffectivenessService {

    @Autowired
    private GovernanceEffectivenessMapper governanceEffectivenessMapper;
}