/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.component.FileLimitedLock;
import org.example.constant.HttpStatus;
import org.example.constant.MappedConstant;
import org.example.entity.PingMessage;
import org.example.feign.client.FeignResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.channels.FileLock;

/**
 * Ping service
 *
 * @author liyu.caelus 2024/12/31
 */
@Slf4j
@Service
public class PingService {

    @Autowired
    private FileLimitedLock limitedLock;
    @Autowired
    private PingClient pingClient;
    @Getter
    @Setter
    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    public String send() {
        FileLock lock = limitedLock.lock();
        if (null != lock) {
            try {
                String msg = this.getSendMessage();
                // http client
                FeignResponse response = pingClient.send(msg);
                log.info("Ping Request sent '{}'", msg);
                String body = response.getBody();
                if (HttpStatus.RATE_LIMITED == response.getHttpCode()) {
                    // Pong service rate limited
                    log.warn("Pong throttled '{}':{}", body, response.getHttpCode());
                    return HttpStatus.RATE_LIMITED_MSG;
                }
                log.info("Pong Respond '{}':{}", body, response.getHttpCode());
                return body;
            } finally {
                limitedLock.unlock(lock);
            }
        }
        // Ping service rate limited
        log.warn("Request not send as being 'rate limited'");
        return HttpStatus.PING_LIMITED_MSG;
    }

    /**
     * get send message from mongodb
     *
     * @return
     */
    private String getSendMessage() {
        if (null != mongoTemplate) {
            final Query query = new Query(Criteria.where("id").is(MappedConstant.MSG_ID));
            PingMessage message = mongoTemplate.findOne(query, PingMessage.class);
            if (null == message) {
                // without
                message = PingMessage.builder().id(MappedConstant.MSG_ID).message(MappedConstant.REQUEST_MSG).build();
                mongoTemplate.insert(message);
            }
            return message.getMessage();
        }
        return MappedConstant.REQUEST_MSG;
    }

    /**
     * set send message to mongodb
     *
     * @return
     */
    public String setSendMessage(String msg) {
        if (null != mongoTemplate) {
            final Query query = new Query(Criteria.where("id").is(MappedConstant.MSG_ID));
            Update update = new Update().set("id", MappedConstant.MSG_ID).set("message", msg);
            mongoTemplate.updateFirst(query, update, PingMessage.class);
        }
        return HttpStatus.SUCCESS_MESSAGE;
    }
}
