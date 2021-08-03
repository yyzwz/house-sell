package cn.exrick.xboot.modules.dj.serviceimpl;

import cn.exrick.xboot.modules.dj.mapper.LearningMaterialsMapper;
import cn.exrick.xboot.modules.dj.entity.LearningMaterials;
import cn.exrick.xboot.modules.dj.service.ILearningMaterialsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 学习资料接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class ILearningMaterialsServiceImpl extends ServiceImpl<LearningMaterialsMapper, LearningMaterials> implements ILearningMaterialsService {

    @Autowired
    private LearningMaterialsMapper learningMaterialsMapper;
}