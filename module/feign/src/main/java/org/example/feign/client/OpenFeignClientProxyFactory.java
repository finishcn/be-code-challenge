/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.feign.client;

import lombok.Getter;

import java.lang.reflect.Proxy;

/**
 * create feign client proxy
 * @author liyu.caelus 2024/12/31
 */
public record OpenFeignClientProxyFactory<T>(Class<T> feignInterface) {

    private T newInstance(OpenFeignClientProxy mapperProxy) {
        return (T) Proxy.newProxyInstance(feignInterface.getClassLoader(), new Class[]{feignInterface}, mapperProxy);
    }

    public T newInstance(String name, String url) {
        final OpenFeignClientProxy mapperProxy = new OpenFeignClientProxy(name, url);
        return newInstance(mapperProxy);
    }
}
