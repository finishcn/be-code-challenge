/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Ping service
 *
 * @author liyu.caelus 2024/12/29
 */
//@FeignClient(name = "${spring.application.name}", url = "${client.serviceUrl}", path = "pong")
public interface PingClient {

    @GetMapping("service/{msg}")
    String service(@PathVariable("msg") String msg);
}
