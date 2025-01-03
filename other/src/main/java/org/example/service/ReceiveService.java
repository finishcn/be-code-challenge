/**
 * pong controller
 *
 * @author liyu.caelus 2024/12/31
 */
package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.example.constant.MappedConstant;
import org.example.dao.PongMessageRepository;
import org.example.dto.MqMessageDTO;
import org.example.entity.PongMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * rocketMQ receive service
 *
 * @author liyu.caelus 2024/12/31
 */
@Slf4j
@Service
@RocketMQMessageListener(topic = MappedConstant.MQ_TOPIC, consumerGroup = "test_group")
public class ReceiveService implements RocketMQListener<MqMessageDTO> {

    @Autowired
    private PongMessageRepository messageRepository;

    /**
     * receive pong message
     */
    @Override
    public void onMessage(MqMessageDTO msg) {
        try {
            log.info("ping sent rocketmq message {}", msg);
            messageRepository.save(PongMessage.builder().traceId(msg.getId()).pongMessage(msg.getPong())
                    .pingMessage(msg.getPing()).build());
        } catch (Exception e) {
            log.error("onMessage error ", e);
        }
    }
}
