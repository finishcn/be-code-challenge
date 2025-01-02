/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.feign.client;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.example.constant.MappedConstant;
import org.example.mapped.util.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * feign client execute http request
 *
 * @author liyu.caelus 2024/12/31
 */
@Setter
@Getter
public class OpenFeignClientProxy implements InvocationHandler {

    private String name;
    private String url;

    public OpenFeignClientProxy(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            final Request request = this.getRequest(method);
            if (request != null) {
                if (StringUtils.isBlank(MDC.get(MappedConstant.TRACE_ID))) {
                    MDC.put(MappedConstant.TRACE_ID, TraceIdUtil.getGenerateTraceId());
                }
                final Map<String, Object> paramMap = new HashMap<>();
                final Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; ++i) {
                    paramMap.put(parameters[i].getName(), args[i]);
                }
                String baseUrl = this.url;
                String path = request.getPath();
                if (StringUtils.isNotBlank(path)) {
                    if (!baseUrl.endsWith("/") && !path.startsWith("/")) {
                        baseUrl = baseUrl + "/" + path;
                    } else {
                        baseUrl = baseUrl + path;
                    }
                }
                HttpRequest httpRequest;
                if (RequestMethod.GET == request.getMethod()) {
                    if (path.contains("{")) {
                        baseUrl = StrUtil.format(baseUrl, paramMap);
                    }
                    httpRequest = HttpUtil.createGet(baseUrl);
                } else {
                    httpRequest = HttpUtil.createPost(baseUrl);
                    httpRequest.body(JSONUtil.toJsonStr(parameters));
                }
                httpRequest.header(MappedConstant.TRACE_ID, MDC.get(MappedConstant.TRACE_ID));
                httpRequest.form(paramMap);
                HttpResponse response = httpRequest.execute();
                final FeignResponse res = new FeignResponse(response.getStatus(), response.body(), response.headers());
                response.close();
                return res;
            }
        }
        return null;
    }

    private Request getRequest(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        if (null != annotation) {
            return new Request(annotation.value()[0], annotation.method()[0]);
        }
        GetMapping getAnnotation = method.getAnnotation(GetMapping.class);
        if (null != getAnnotation) {
            return new Request(getAnnotation.value()[0], RequestMethod.GET);
        }
        PostMapping postAnnotation = method.getAnnotation(PostMapping.class);
        if (null != postAnnotation) {
            return new Request(postAnnotation.value()[0], RequestMethod.POST);
        }
        PutMapping putAnnotation = method.getAnnotation(PutMapping.class);
        if (null != putAnnotation) {
            return new Request(putAnnotation.value()[0], RequestMethod.POST);
        }
        DeleteMapping delAnnotation = method.getAnnotation(DeleteMapping.class);
        if (null != delAnnotation) {
            return new Request(delAnnotation.value()[0], RequestMethod.POST);
        }
        return null;
    }

    @Setter
    @Getter
    static class Request {
        private String path;
        private RequestMethod method;

        public Request(String path, RequestMethod method) {
            this.path = path;
            this.method = method;
        }
    }
}
