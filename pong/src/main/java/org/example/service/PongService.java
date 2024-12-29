/**
 * pong controller
 * @author liyu.caelus 2024/12/29
 *
 */
package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.MappedConstant;
import org.springframework.stereotype.Service;

/**
 * pong service
 * @author liyu.caelus 2024/12/29
 *
 */
@Slf4j
@Service
public class PongService {

    public String respond(String msg) {
        log.info("ping Request sent {}", msg);
        return MappedConstant.RESPONSE_MSG;
    }
}
