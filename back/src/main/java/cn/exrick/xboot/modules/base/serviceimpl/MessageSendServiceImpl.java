package cn.exrick.xboot.modules.base.serviceimpl;

import cn.exrick.xboot.common.constant.CommonConstant;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.modules.base.dao.MessageDao;
import cn.exrick.xboot.modules.base.dao.MessageSendDao;
import cn.exrick.xboot.modules.base.entity.Message;
import cn.exrick.xboot.modules.base.entity.MessageSend;
import cn.exrick.xboot.modules.base.service.MessageSendService;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 消息发送接口实现
 * @author Exrick
 */
@Slf4j
@Service
@Transactional
public class MessageSendServiceImpl implements MessageSendService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MessageSendDao messageSendDao;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public MessageSendDao getRepository() {
        return messageSendDao;
    }

    @Override
    public MessageSend send(MessageSend messageSend) {

        MessageSend ms = messageSendDao.save(messageSend);
        messagingTemplate.convertAndSendToUser(messageSend.getUserId(), "/queue/subscribe", "您收到了新的消息");
        return ms;
    }

    @Override
    public void deleteByMessageId(String messageId) {

        messageSendDao.deleteByMessageId(messageId);
    }

    @Override
    public Page<MessageSend> findByCondition(MessageSend messageSend, Pageable pageable) {

        return messageSendDao.findAll(new Specification<MessageSend>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<MessageSend> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> messageIdField = root.get("messageId");
                Path<String> userIdField = root.get("userId");
                Path<Integer> statusField = root.get("status");

                List<Predicate> list = new ArrayList<>();

                if (StrUtil.isNotBlank(messageSend.getMessageId())) {
                    list.add(cb.equal(messageIdField, messageSend.getMessageId()));
                }
                if (StrUtil.isNotBlank(messageSend.getUserId())) {
                    list.add(cb.equal(userIdField, messageSend.getUserId()));
                }
                if (messageSend.getStatus() != null) {
                    list.add(cb.equal(statusField, messageSend.getStatus()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public void updateStatusByUserId(String userId, Integer status) {

        messageSendDao.updateStatusByUserId(userId, status);
    }

    @Override
    public void deleteByUserId(String userId) {

        messageSendDao.deleteByUserId(userId, CommonConstant.MESSAGE_STATUS_READ);
    }

    @Override
    public Message getTemplateMessage(String messageId, Map<String, String> params) {

        Message message = messageDao.findById(messageId).orElse(null);
        if (message == null) {
            throw new XbootException("消息ID：" + messageId + "不存在");
        }
        // 放入变量
        Message newMessage = new Message();
        newMessage.setTitle(changeParams(message.getTitle(), params));
        newMessage.setContent(changeParams(message.getContent(), params));
        return newMessage;
    }

    @Override
    public void sendTemplateMessage(List<String> userIds, String messageId, Map<String, String> params) {

        Message message = messageDao.findById(messageId).orElse(null);
        if (message == null) {
            throw new XbootException("消息ID：" + messageId + "不存在");
        }
        List<MessageSend> messageSends = new ArrayList<>();
        for (String userId : userIds) {
            MessageSend ms = new MessageSend();
            ms.setUserId(userId).setMessageId(messageId);
            // 放入变量
            ms.setTitle(changeParams(message.getTitle(), params));
            ms.setContent(changeParams(message.getContent(), params));
            ms.setParams(new Gson().toJson(params));
            messageSends.add(ms);
            messagingTemplate.convertAndSendToUser(userId, "/queue/subscribe", "您收到了新的消息");
        }
        messageSendDao.saveAll(messageSends);
    }

    @Override
    public void sendTemplateMessage(String userId, String messageId, Map<String, String> params) {

        List<String> users = new ArrayList<>();
        users.add(userId);
        this.sendTemplateMessage(users, messageId, params);
    }

    public String changeParams(String v, Map<String, String> params) {

        if (v == null || params == null) {
            return "";
        }
        for (Map.Entry entry : params.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            v = v.replaceAll("\\$\\{" + key + "\\}", value);
        }
        return v;
    }
}