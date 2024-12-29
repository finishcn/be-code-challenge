/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * pong controller
 *
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@RestController
@RequestMapping("pong")
public class PongController {

    @Autowired
    private PongService pongService;

    @GetMapping(value = "service/{msg}")
    public Mono<String> service(@PathVariable("msg") String msg) {
        return Mono.just(pongService.respond(msg));
    }
}
