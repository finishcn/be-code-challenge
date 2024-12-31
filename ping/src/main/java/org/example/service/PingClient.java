/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.service;

import org.example.feign.annotation.OpenFeignClient;
import org.example.feign.client.FeignResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Ping openfeign clients
 *
 * @author liyu.caelus 2024/12/29
 */
@OpenFeignClient(name = "${spring.application.name}", url = "${client.serviceUrl}", path = "pong")
public interface PingClient {

    @GetMapping("send/{msg}")
    FeignResponse send(String msg);

    @PostMapping("service")
    FeignResponse service(String msg);
}
