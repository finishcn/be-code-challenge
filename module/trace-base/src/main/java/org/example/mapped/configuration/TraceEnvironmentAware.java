/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.configuration;

import jakarta.annotation.Nonnull;
import org.example.constant.MappedConstant;
import org.slf4j.MDC;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * initial trace name
 *
 * @version 1.0
 * @Author liyu.caelus
 */
@Configuration
public class TraceEnvironmentAware implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@Nonnull BeanDefinitionRegistry registry) throws BeansException {
        MDC.put(MappedConstant.TRACE_ID, "main");
    }

    @Override
    public void postProcessBeanFactory(@Nonnull ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
