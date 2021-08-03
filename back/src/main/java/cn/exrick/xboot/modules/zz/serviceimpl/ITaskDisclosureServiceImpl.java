package cn.exrick.xboot.modules.zz.serviceimpl;

import cn.exrick.xboot.modules.zz.mapper.TaskDisclosureMapper;
import cn.exrick.xboot.modules.zz.entity.TaskDisclosure;
import cn.exrick.xboot.modules.zz.service.ITaskDisclosureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 村务公开接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class ITaskDisclosureServiceImpl extends ServiceImpl<TaskDisclosureMapper, TaskDisclosure> implements ITaskDisclosureService {

    @Autowired
    private TaskDisclosureMapper taskDisclosureMapper;
}