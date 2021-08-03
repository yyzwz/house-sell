package cn.exrick.xboot.modules.dz.serviceimpl;

import cn.exrick.xboot.modules.dz.mapper.FamilyStyleMapper;
import cn.exrick.xboot.modules.dz.entity.FamilyStyle;
import cn.exrick.xboot.modules.dz.service.IFamilyStyleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 家风家训接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IFamilyStyleServiceImpl extends ServiceImpl<FamilyStyleMapper, FamilyStyle> implements IFamilyStyleService {

    @Autowired
    private FamilyStyleMapper familyStyleMapper;
}