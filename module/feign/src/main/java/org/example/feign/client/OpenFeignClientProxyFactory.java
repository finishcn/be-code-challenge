/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.feign.client;

import lombok.Getter;

import java.lang.reflect.Proxy;

/**
 * @author liyu.caelus 2024/12/29
 */
public class OpenFeignClientProxyFactory<T> {

    @Getter
    private final Class<T> feignInterface;

    public OpenFeignClientProxyFactory(Class<T> feignInterface) {
        this.feignInterface = feignInterface;
    }

    protected T newInstance(OpenFeignClientProxy mapperProxy) {
        return (T) Proxy.newProxyInstance(feignInterface.getClassLoader(), new Class[]{feignInterface}, mapperProxy);
    }

    public T newInstance(String name, String url) {
        final OpenFeignClientProxy mapperProxy = new OpenFeignClientProxy(name, url);
        return newInstance(mapperProxy);
    }
}
