package cn.exrick.xboot.modules.dj.serviceimpl;

import cn.exrick.xboot.modules.dj.mapper.DynamicBuildMapper;
import cn.exrick.xboot.modules.dj.entity.DynamicBuild;
import cn.exrick.xboot.modules.dj.service.IDynamicBuildService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 党建动态接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IDynamicBuildServiceImpl extends ServiceImpl<DynamicBuildMapper, DynamicBuild> implements IDynamicBuildService {

    @Autowired
    private DynamicBuildMapper dynamicBuildMapper;
}