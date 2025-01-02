/**
 * pong controller
 *
 * @author liyu.caelus 2024/12/31
 */
package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.MappedConstant;
import org.example.dto.MqMessageDTO;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * pong service
 *
 * @author liyu.caelus 2024/12/31
 */
@Slf4j
@Service
@Configuration
public class PongService {

    @Autowired(required = false)
    private StreamBridge streamBridge;

    /**
     * Receive a message from the ping.
     *
     * @param msg
     * @return
     */
    public String respond(String msg) {
        log.info("ping Request sent {}", msg);
        String response = MappedConstant.RESPONSE_MSG;
        this.message(msg, response);
        return response;
    }

    /**
     * Send a message to RocketMQ
     *
     * @param req  ping message
     * @param resp pong message
     */
    private void message(String req, String resp) {
        if (null != streamBridge) {
            MqMessageDTO message = MqMessageDTO.builder().id(MDC.get(MappedConstant.TRACE_ID)).pong(resp).ping(req).build();
            boolean flag = streamBridge.send(MappedConstant.MQ_TOPIC, MessageBuilder.withPayload(message)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
            log.info("Ping sent message {},result {}", message, flag);
        }
    }
}
