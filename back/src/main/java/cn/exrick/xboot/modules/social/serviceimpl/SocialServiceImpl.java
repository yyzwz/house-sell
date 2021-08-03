package cn.exrick.xboot.modules.social.serviceimpl;

import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.social.dao.SocialDao;
import cn.exrick.xboot.modules.social.entity.Social;
import cn.exrick.xboot.modules.social.service.SocialService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Github登录接口实现
 * @author Exrick
 */
@Slf4j
@Service
@Transactional
public class SocialServiceImpl implements SocialService {

    @Autowired
    private SocialDao socialDao;

    @Override
    public SocialDao getRepository() {
        return socialDao;
    }

    @Override
    public Social findByOpenIdAndPlatform(String openId, Integer platform) {

        return socialDao.findByOpenIdAndPlatform(openId, platform);
    }

    @Override
    public Social findByRelateUsernameAndPlatform(String relateUsername, Integer platform) {

        return socialDao.findByRelateUsernameAndPlatform(relateUsername, platform);
    }

    @Override
    public List<Social> findByRelateUsername(String relateUsername) {

        return socialDao.findByRelateUsername(relateUsername);
    }

    @Override
    public Page<Social> findByCondition(Social social, SearchVo searchVo, Pageable pageable) {

        return socialDao.findAll(new Specification<Social>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Social> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> usernameField = root.get("username");
                Path<String> relateUsernameField = root.get("relateUsername");
                Path<Integer> platformField = root.get("platform");
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<>();

                if (StrUtil.isNotBlank(social.getUsername())) {
                    list.add(cb.like(usernameField, '%' + social.getUsername() + '%'));
                }
                if (StrUtil.isNotBlank(social.getRelateUsername())) {
                    list.add(cb.like(relateUsernameField, '%' + social.getRelateUsername() + '%'));
                }
                if (social.getPlatform() != null) {
                    list.add(cb.equal(platformField, social.getPlatform()));
                }
                //创建时间
                if (StrUtil.isNotBlank(searchVo.getStartDate()) && StrUtil.isNotBlank(searchVo.getEndDate())) {
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }
}