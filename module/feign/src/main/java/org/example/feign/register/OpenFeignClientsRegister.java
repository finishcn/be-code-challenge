/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.feign.register;

import org.example.feign.OpenFeignClientFactoryBean;
import org.example.feign.annotation.EnableOpenFeignClients;
import org.example.feign.annotation.OpenFeignClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author liyu.caelus 2024/12/29
 */
public class OpenFeignClientsRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, EnvironmentAware {

    private ResourceLoader resourceLoader;
    private Environment environment;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
        LinkedHashSet<BeanDefinition> candidateComponents = new LinkedHashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = this.getScanner();
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(OpenFeignClient.class));
        Set<String> basePackages = this.getBasePackages(metadata);

        for (String basePackage : basePackages) {
            candidateComponents.addAll(scanner.findCandidateComponents(basePackage));
        }
        for (BeanDefinition candidateComponent : candidateComponents) {
            if (candidateComponent instanceof AnnotatedBeanDefinition beanDefinition) {
                AnnotationMetadata annotationMetadata = beanDefinition.getMetadata();
                Assert.isTrue(annotationMetadata.isInterface(), "@OpenFeignClient can only be specified on an interface");
                Map<String, Object> attributes = annotationMetadata.getAnnotationAttributes(OpenFeignClient.class.getCanonicalName());
                String className = annotationMetadata.getClassName();
                this.eagerlyRegisterFeignClientBeanDefinition(className, attributes, registry);
            }
        }
    }

    private void eagerlyRegisterFeignClientBeanDefinition(String className, Map<String, Object> attributes, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(OpenFeignClientFactoryBean.class);
        definition.addPropertyValue("url", this.getUrl(null, attributes));
        definition.addPropertyValue("path", this.getPath(null, attributes));
        String name = this.getName(attributes);
        definition.addPropertyValue("name", name);
        String contextId = this.getContextId(null, attributes);
        definition.addPropertyValue("contextId", contextId);
        definition.addPropertyValue("type", className);
        Object fallback = attributes.get("fallback");
        if (fallback != null) {
            definition.addPropertyValue("fallback", fallback instanceof Class ? fallback : ClassUtils.resolveClassName(fallback.toString(), null));
        }
        definition.setAutowireMode(2);
        String[] qualifiers = new String[]{contextId + "FeignClient"};
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
//        beanDefinition.setAttribute("factoryBeanObjectType", className);
        boolean primary = (Boolean) attributes.get("primary");
        beanDefinition.setPrimary(primary);
        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition, className, qualifiers);
        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);

    }

    private String getQualifier(Map<String, Object> client) {
        if (client == null) {
            return null;
        } else {
            String qualifier = (String) client.get("qualifier");
            return StringUtils.hasText(qualifier) ? qualifier : null;
        }
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    protected Set<String> getBasePackages(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableOpenFeignClients.class.getCanonicalName());
        Set<String> basePackages = new HashSet<>();
        String[] var4 = (String[]) attributes.get("value");
        int var5 = var4.length;

        int var6;
        String pkg;
        for (var6 = 0; var6 < var5; ++var6) {
            pkg = var4[var6];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        var4 = (String[]) attributes.get("basePackages");
        var5 = var4.length;

        for (var6 = 0; var6 < var5; ++var6) {
            pkg = var4[var6];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        Class[] var8 = (Class[]) attributes.get("basePackageClasses");
        var5 = var8.length;

        for (var6 = 0; var6 < var5; ++var6) {
            Class<?> clazz = var8[var6];
            basePackages.add(ClassUtils.getPackageName(clazz));
        }

        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        }

        return basePackages;
    }

    private String getUrl(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String url = this.resolve(beanFactory, (String) attributes.get("url"));
        return getUrl(url);
    }

    private String getPath(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String path = this.resolve(beanFactory, (String) attributes.get("path"));
        return getPath(path);
    }

    private String getClientName(Map<String, Object> client) {
        if (client == null) {
            return null;
        } else {
            String value = (String) client.get("contextId");
            if (!StringUtils.hasText(value)) {
                value = (String) client.get("value");
            }

            if (!StringUtils.hasText(value)) {
                value = (String) client.get("name");
            }

            if (!StringUtils.hasText(value)) {
                value = (String) client.get("serviceId");
            }

            if (StringUtils.hasText(value)) {
                return value;
            } else {
                throw new IllegalStateException("Either 'name' or 'value' must be provided in @" + OpenFeignClient.class.getSimpleName());
            }
        }
    }

    protected ClassPathScanningCandidateComponentProvider getScanner() {
        return new ClassPathScanningCandidateComponentProvider(false, this.environment) {
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent() && !beanDefinition.getMetadata().isAnnotation()) {
                    isCandidate = true;
                }

                return isCandidate;
            }
        };
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private boolean isClientRefreshEnabled() {
        return this.environment.getProperty("spring.cloud.openfeign.client.refresh-enabled", Boolean.class, false);
    }

    private String getContextId(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String contextId = (String) attributes.get("contextId");
        if (!StringUtils.hasText(contextId)) {
            return this.getName(attributes);
        } else {
            contextId = this.resolve(beanFactory, contextId);
            return getName(contextId);
        }
    }

    String getName(Map<String, Object> attributes) {
        return this.getName((ConfigurableBeanFactory) null, attributes);
    }

    String getName(ConfigurableBeanFactory beanFactory, Map<String, Object> attributes) {
        String name = (String) attributes.get("serviceId");
        if (!StringUtils.hasText(name)) {
            name = (String) attributes.get("name");
        }

        if (!StringUtils.hasText(name)) {
            name = (String) attributes.get("value");
        }

        name = this.resolve(beanFactory, name);
        return getName(name);
    }

    private String resolve(ConfigurableBeanFactory beanFactory, String value) {
        if (StringUtils.hasText(value)) {
            if (beanFactory == null) {
                return this.environment.resolvePlaceholders(value);
            } else {
                BeanExpressionResolver resolver = beanFactory.getBeanExpressionResolver();
                String resolved = beanFactory.resolveEmbeddedValue(value);
                if (resolver == null) {
                    return resolved;
                } else {
                    Object evaluateValue = resolver.evaluate(resolved, new BeanExpressionContext(beanFactory, null));
                    return evaluateValue != null ? String.valueOf(evaluateValue) : null;
                }
            }
        } else {
            return value;
        }
    }

    static String getName(String name) {
        if (!StringUtils.hasText(name)) {
            return "";
        } else {
            String host = null;

            try {
                String url;
                if (!name.startsWith("http://") && !name.startsWith("https://")) {
                    url = "http://" + name;
                } else {
                    url = name;
                }

                host = (new URI(url)).getHost();
            } catch (URISyntaxException var3) {
            }

            Assert.state(host != null, "Service id not legal hostname (" + name + ")");
            return name;
        }
    }

    static String getUrl(String url) {
        if (StringUtils.hasText(url) && (!url.startsWith("#{") || !url.contains("}"))) {
            if (!url.contains("://")) {
                url = "http://" + url;
            }

            try {
                new URL(url);
            } catch (MalformedURLException var2) {
                MalformedURLException e = var2;
                throw new IllegalArgumentException(url + " is malformed", e);
            }
        }

        return url;
    }

    static String getPath(String path) {
        if (StringUtils.hasText(path)) {
            path = path.trim();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }

            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
        }

        return path;
    }
}
