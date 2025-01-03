/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.feign.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Annotation for interfaces declaring that a REST client with that interface should be created
 *
 * @author liyu.caelus 2024/12/31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenFeignClient {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String qualifier() default "";

    String url() default "";

    Class<?> fallback() default void.class;

    String path() default "";

    boolean primary() default true;
}
