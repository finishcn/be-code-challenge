/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.feign.annotation;

import org.example.feign.register.OpenFeignClientsRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author liyu.caelus 2024/12/29
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
