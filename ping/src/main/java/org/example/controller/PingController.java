/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.MappedConstant;
import org.example.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * ping controller
 *
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@RestController
@RequestMapping("ping")
public class PingController {

    @Autowired
    private PingService pingService;

    @GetMapping(value = "service")
    public Mono<String> service() {
        return Mono.just(pingService.service(MappedConstant.REQUEST_MSG));
    }

    @Scheduled(cron = "* * * * * ? ")
    public void scheduled() {
        pingService.service(MappedConstant.REQUEST_MSG);
    }
}
