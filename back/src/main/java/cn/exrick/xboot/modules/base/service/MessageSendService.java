package cn.exrick.xboot.modules.base.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.base.entity.Message;
import cn.exrick.xboot.modules.base.entity.MessageSend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 消息发送接口
 * @author Exrick
 */
public interface MessageSendService extends XbootBaseService<MessageSend, String> {

    /**
     * 发送消息 带websock推送
     * @param messageSend
     * @return
     */
    MessageSend send(MessageSend messageSend);

    /**
     * 通过消息id删除
     * @param messageId
     */
    void deleteByMessageId(String messageId);

    /**
     * 多条件分页获取
     * @param messageSend
     * @param pageable
     * @return
     */
    Page<MessageSend> findByCondition(MessageSend messageSend, Pageable pageable);

    /**
     * 批量更新消息状态
     * @param userId
     * @param status
     */
    void updateStatusByUserId(String userId, Integer status);

    /**
     * 通过userId删除
     * @param userId
     */
    void deleteByUserId(String userId);

    /**
     * 获得填充变量后的消息
     * @param messageId
     * @param params
     * @return
     */
    Message getTemplateMessage(String messageId, Map<String, String> params);

    /**
     * 发送模版消息
     * @param userIds   发送用户
     * @param messageId 消息ID
     * @param params    消息模版变量参数
     */
    void sendTemplateMessage(List<String> userIds, String messageId, Map<String, String> params);

    /**
     * 发送模版消息
     * @param userId    发送给单个用户
     * @param messageId 消息ID
     * @param params    消息模版变量参数
     */
    void sendTemplateMessage(String userId, String messageId, Map<String, String> params);
}