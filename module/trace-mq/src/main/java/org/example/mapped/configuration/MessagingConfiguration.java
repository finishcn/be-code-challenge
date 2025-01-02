/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.configuration;

import org.example.mapped.interceptor.MqInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.AbstractMessageChannel;

/**
 * add message queue interceptor
 *
 * @Author liyu.caelus
 * @@version 1.0
 */
@Configuration
public class MessagingConfiguration {

    @Bean
    public BeanPostProcessor channelsConfigurar(@Qualifier("mqInterceptor") MqInterceptor mqInterceptor) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof AbstractMessageChannel) {
                    ((AbstractMessageChannel) bean).addInterceptor(0, mqInterceptor);
                }
                return bean;
            }
        };
    }
}
