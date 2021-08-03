package cn.exrick.xboot.modules.zz.serviceimpl;

import cn.exrick.xboot.modules.zz.mapper.VillageIntroduceMapper;
import cn.exrick.xboot.modules.zz.entity.VillageIntroduce;
import cn.exrick.xboot.modules.zz.service.IVillageIntroduceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 村情介绍接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IVillageIntroduceServiceImpl extends ServiceImpl<VillageIntroduceMapper, VillageIntroduce> implements IVillageIntroduceService {

    @Autowired
    private VillageIntroduceMapper villageIntroduceMapper;
}