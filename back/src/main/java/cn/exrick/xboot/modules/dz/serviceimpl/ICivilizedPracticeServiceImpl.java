package cn.exrick.xboot.modules.dz.serviceimpl;

import cn.exrick.xboot.modules.dz.mapper.CivilizedPracticeMapper;
import cn.exrick.xboot.modules.dz.entity.CivilizedPractice;
import cn.exrick.xboot.modules.dz.service.ICivilizedPracticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 文明实践接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class ICivilizedPracticeServiceImpl extends ServiceImpl<CivilizedPracticeMapper, CivilizedPractice> implements ICivilizedPracticeService {

    @Autowired
    private CivilizedPracticeMapper civilizedPracticeMapper;
}