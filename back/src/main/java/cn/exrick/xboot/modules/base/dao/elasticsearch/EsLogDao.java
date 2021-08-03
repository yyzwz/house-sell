package cn.exrick.xboot.modules.base.dao.elasticsearch;

import cn.exrick.xboot.modules.base.entity.elasticsearch.EsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @author Exrickx
 */
public interface EsLogDao extends ElasticsearchRepository<EsLog, String> {

    /**
     * 通过类型获取
     * @param type
     * @param pageable
     * @return
     */
    Page<EsLog> findByLogType(Integer type, Pageable pageable);
}
