package cn.exrick.xboot.modules.zz.serviceimpl;

import cn.exrick.xboot.modules.zz.mapper.VillageAppointmentMapper;
import cn.exrick.xboot.modules.zz.entity.VillageAppointment;
import cn.exrick.xboot.modules.zz.service.IVillageAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 村规民约接口实现
 * @author 郑为中
 */
@Slf4j
@Service
@Transactional
public class IVillageAppointmentServiceImpl extends ServiceImpl<VillageAppointmentMapper, VillageAppointment> implements IVillageAppointmentService {

    @Autowired
    private VillageAppointmentMapper villageAppointmentMapper;
}