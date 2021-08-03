package cn.exrick.xboot.modules.dz.serviceimpl;

import cn.exrick.xboot.modules.dz.mapper.FolkActivitiesMapper;
import cn.exrick.xboot.modules.dz.entity.FolkActivities;
import cn.exrick.xboot.modules.dz.service.IFolkActivitiesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 民俗活动接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IFolkActivitiesServiceImpl extends ServiceImpl<FolkActivitiesMapper, FolkActivities> implements IFolkActivitiesService {

    @Autowired
    private FolkActivitiesMapper folkActivitiesMapper;
}