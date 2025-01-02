/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.configuration;

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
 * @Author liyu.caelus
 * @@version 1.0
 */
@Configuration
public class TraceEnvironmentAware implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        MDC.put(MappedConstant.TRACE_ID, "main");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
