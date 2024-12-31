/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.PongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "send/{msg}")
    public Mono<String> send(@PathVariable("msg") String msg) {
        return Mono.just(pongService.respond(msg));
    }

    @PostMapping(value = "service")
    public Mono<String> service(String msg) {
        return Mono.just(pongService.respond(msg));
    }
}
