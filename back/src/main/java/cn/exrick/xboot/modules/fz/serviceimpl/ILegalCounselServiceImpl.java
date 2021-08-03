package cn.exrick.xboot.modules.fz.serviceimpl;

import cn.exrick.xboot.modules.fz.mapper.LegalCounselMapper;
import cn.exrick.xboot.modules.fz.entity.LegalCounsel;
import cn.exrick.xboot.modules.fz.service.ILegalCounselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 法律顾问接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class ILegalCounselServiceImpl extends ServiceImpl<LegalCounselMapper, LegalCounsel> implements ILegalCounselService {

    @Autowired
    private LegalCounselMapper legalCounselMapper;
}