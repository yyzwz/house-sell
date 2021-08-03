package cn.exrick.xboot.modules.dj.serviceimpl;

import cn.exrick.xboot.modules.dj.mapper.MeetingMapper;
import cn.exrick.xboot.modules.dj.entity.Meeting;
import cn.exrick.xboot.modules.dj.service.IMeetingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IMeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements IMeetingService {

    @Autowired
    private MeetingMapper meetingMapper;
}