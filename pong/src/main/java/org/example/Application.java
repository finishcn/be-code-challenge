/**
 * pong controller
 *
 * @author liyu.caelus 2024/12/29
 */
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * pong server
 * @author liyu.caelus 2024/12/29
 *
 */
//@EnableDiscoveryClient
//@EnableFeignClients
@EnableWebFlux
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
