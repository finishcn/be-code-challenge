/**
 * liyu.caelus 2024/12/31
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
 * @author liyu.caelus 2024/12/31
 */
@OpenFeignClient(name = "${spring.application.name}", url = "${client.serviceUrl}", path = "pong")
public interface PingClient {

    /**
     * Use the get method to send a message to the Pong service.
     *
     * @param msg
     * @return
     */
    @GetMapping("send/{msg}")
    FeignResponse send(String msg);

    /**
     * Use the post method to send a message to the Pong service.
     *
     * @param msg
     * @return
     */
    @PostMapping("service")
    FeignResponse service(String msg);
}
