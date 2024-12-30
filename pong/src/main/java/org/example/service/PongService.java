/**
 * pong controller
 *
 * @author liyu.caelus 2024/12/29
 */
package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.MappedConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * pong service
 *
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@Service
@Configuration
public class PongService {

//    @Autowired
//    private LogRepository logRepository;

    public String respond(String msg) {
        log.info("ping Request sent {}", msg);
        return MappedConstant.RESPONSE_MSG;
    }

   /* @Bean
    public Consumer<Message> receiveTask() {
        return message -> {
            try {
                String data = message.getBody().toString();
                log.info("ping sent rocketmq message {}", data);
                logRepository.save(PongLog.builder().message(data).build());
            } catch (Exception e) {
                log.error("recive message error", e);
            }
        };
    }*/

    /*@Bean
    public Consumer<Message<String>> kafkaMessage() {
        return message -> {
            try {
                String data = message.getPayload();
                log.info("ping sent kafka message {}", data);
            } catch (Exception e) {
                log.error("recive message kafka error", e);
            }
        };
    }*/
}
