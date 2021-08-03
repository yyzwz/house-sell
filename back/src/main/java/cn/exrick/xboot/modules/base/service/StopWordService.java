package cn.exrick.xboot.modules.base.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.base.entity.StopWord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

/**
 * 禁用词管理接口
 * @author Exrick
 */
public interface StopWordService extends XbootBaseService<StopWord, String> {

    /**
    * 多条件分页获取
    * @param stopWord
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<StopWord> findByCondition(StopWord stopWord, SearchVo searchVo, Pageable pageable);

}