/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.component.FileLimitedLock;
import org.example.constant.HttpCode;
import org.example.feign.client.FeignResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.FileLock;

/**
 * Ping service
 *
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@Service
public class PingService {

    @Autowired
    private FileLimitedLock limitedLock;
    @Autowired
    private PingClient pingClient;
//    @Autowired
//    private StreamBridge streamBridge;
//    @Autowired
//    private MongodbLock mongodbLock;

    public String service(String msg) {
        FileLock lock = limitedLock.lock();
        if (null != lock) {
            try {
                FeignResponse response = pingClient.send(msg);
                log.info("Ping Request sent {}", msg);
                String body = response.getBody();
                if (HttpCode.RATE_LIMITED == response.getHttpCode()) {
                    log.info("Pong throttled {}:{}", body, response.getHttpCode());
                    return HttpCode.RATE_LIMITED_MSG;
                }
                log.info("Pong Respond {}:{}", body, response.getHttpCode());
                return body;
            } catch (Exception e) {
                log.error("Request sent error", e);
                return HttpCode.PING_ERROR_MSG;
            } finally {
                limitedLock.unlock(lock);
            }
        }
        log.info("Request not send as being 'rate limited'");
        return HttpCode.PING_LIMITED_MSG;
    }

    /*public String message(String msg) {
        boolean flag = streamBridge.send(MappedConstant.MQ_RECEIVE_TASK, MessageBuilder.withPayload(msg).build());
        log.info("Ping sent message {},result {}", msg, flag);
        return MappedConstant.SUCCESS;
    }*/

    /*public String sendMessageToKafka(String msg) {
        boolean send = streamBridge.send("kafkaMessage-out-0", MessageBuilder.withPayload("kafka test：" + msg).build());
        return MappedConstant.SUCCESS;
    }*/

}
