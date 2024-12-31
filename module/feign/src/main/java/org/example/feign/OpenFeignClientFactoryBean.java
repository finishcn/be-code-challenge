/**
 * 黎宇 2024/12/30
 * Copyright
 */
package org.example.feign;

import org.example.feign.client.OpenFeignClientProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

/**
 * @author liyu.caelus 2024/12/29
 */
public class OpenFeignClientFactoryBean<T> implements FactoryBean<Object> {

    private Class<T> type;
    private Class<?> fallback;
    private String name;
    private String url;
    private String contextId;
    private String path;

    @Override
    public Object getObject() {
        if (!this.url.startsWith("http://") && !this.url.startsWith("https://")) {
            this.url = "http://" + this.url;
        }
        if (StringUtils.hasText(path)) {
            if (!this.url.endsWith("/") && !this.path.startsWith("/")) {
                this.url = this.url + "/" + this.path;
            } else {
                this.url = this.url + this.path;
            }
        }
        OpenFeignClientProxyFactory<T> factory = new OpenFeignClientProxyFactory<>(type);
        return factory.newInstance(this.name, this.url);
    }

    @Override
    public Class<?> getObjectType() {
        return this.type;
    }

    public boolean isSingleton() {
        return true;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public Class<?> getFallback() {
        return fallback;
    }

    public void setFallback(Class<?> fallback) {
        this.fallback = fallback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
