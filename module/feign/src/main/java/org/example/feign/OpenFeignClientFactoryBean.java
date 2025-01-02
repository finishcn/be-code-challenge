/**
 * 黎宇 2024/12/30
 * Copyright
 */
package org.example.feign;

import lombok.Setter;
import org.example.feign.client.OpenFeignClientProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

/**
 * create feign client proxy
 *
 * @author liyu.caelus 2024/12/31
 */
@Setter
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
}
