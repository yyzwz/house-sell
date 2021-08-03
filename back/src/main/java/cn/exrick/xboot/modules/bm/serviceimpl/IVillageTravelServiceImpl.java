package cn.exrick.xboot.modules.bm.serviceimpl;

import cn.exrick.xboot.modules.bm.mapper.VillageTravelMapper;
import cn.exrick.xboot.modules.bm.entity.VillageTravel;
import cn.exrick.xboot.modules.bm.service.IVillageTravelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 乡村旅游接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IVillageTravelServiceImpl extends ServiceImpl<VillageTravelMapper, VillageTravel> implements IVillageTravelService {

    @Autowired
    private VillageTravelMapper villageTravelMapper;
}