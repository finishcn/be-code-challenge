/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * ping controller
 *
 * @author liyu.caelus 2024/12/31
 */
@Slf4j
@RestController
@RequestMapping("ping")
public class PingController {

    @Autowired
    private PingService pingService;

    /**
     * do send
     */
    @GetMapping(value = "send")
    public Mono<String> send() {
        return Mono.just(pingService.send());
    }

    /**
     * set send message
     */
    @PostMapping(value = "message")
    public Mono<String> message(String msg) {
        return Mono.just(pingService.setSendMessage(msg));
    }

    /**
     * send to pong service around every 1 second
     */
    @Scheduled(cron = "* * * * * ? ")
    public void scheduled() {
        pingService.send();
    }
}
