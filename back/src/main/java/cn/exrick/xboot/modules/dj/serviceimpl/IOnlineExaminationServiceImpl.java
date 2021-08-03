package cn.exrick.xboot.modules.dj.serviceimpl;

import cn.exrick.xboot.modules.dj.mapper.OnlineExaminationMapper;
import cn.exrick.xboot.modules.dj.entity.OnlineExamination;
import cn.exrick.xboot.modules.dj.service.IOnlineExaminationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 在线考试接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IOnlineExaminationServiceImpl extends ServiceImpl<OnlineExaminationMapper, OnlineExamination> implements IOnlineExaminationService {

    @Autowired
    private OnlineExaminationMapper onlineExaminationMapper;
}