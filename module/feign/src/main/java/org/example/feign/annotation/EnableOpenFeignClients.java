/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.feign.annotation;

import org.example.feign.register.OpenFeignClientsRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Scans for interfaces that declare they are feign clients (via FeignClient @OpenFeignClient).
 * Configures component scanning directives for use with org. springframework. context. annotation.
 * @author liyu.caelus 2024/12/31
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(OpenFeignClientsRegister.class)
public @interface EnableOpenFeignClients {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};

}
