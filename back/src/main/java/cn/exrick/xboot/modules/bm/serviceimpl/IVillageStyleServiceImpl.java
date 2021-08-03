package cn.exrick.xboot.modules.bm.serviceimpl;

import cn.exrick.xboot.modules.bm.mapper.VillageStyleMapper;
import cn.exrick.xboot.modules.bm.entity.VillageStyle;
import cn.exrick.xboot.modules.bm.service.IVillageStyleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 一村一品接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IVillageStyleServiceImpl extends ServiceImpl<VillageStyleMapper, VillageStyle> implements IVillageStyleService {

    @Autowired
    private VillageStyleMapper villageStyleMapper;
}