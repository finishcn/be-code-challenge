/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.base.LimitedLock;
import org.example.constant.HttpCode;
import org.example.constant.MappedConstant;
import org.example.util.TraceIdUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.channels.FileLock;

/**
 * Ping service
 *
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
@Service
public class PingService {

    @Autowired
    private LimitedLock limitedLock;

    @Value("${client.serviceUrl}")
    private String baseUrl;

    private String uri;

    @PostConstruct
    public void init() {
        uri = baseUrl + "/pong/service/";
    }

    public String service(String msg) {
        if (StringUtils.isBlank(MDC.get(MappedConstant.TRACE_ID))) {
            MDC.put(MappedConstant.TRACE_ID, TraceIdUtil.getGenerateTraceId());
        }
        FileLock lock = limitedLock.lock();
        if (null != lock) {
            try {
                HttpRequest request = HttpUtil.createGet(uri + msg);
                request.header(MappedConstant.TRACE_ID, MDC.get(MappedConstant.TRACE_ID));
                HttpResponse response = request.execute();
                log.info("ping Request sent {}", msg);
                String body = response.body();
                if (HttpCode.RATE_LIMITED == response.getStatus()) {
                    log.info("Pong throttled {}:{}", body, response.getStatus());
                    return HttpCode.RATE_LIMITED_MSG;
                }
                log.info("Pong Respond {}:{}", body, response.getStatus());
                response.close();
                return body;
            } catch (Exception e) {
                log.error("Request sent error", e);
                return HttpCode.PING_ERROR_MSG;
            } finally {
                limitedLock.unlock(lock);
            }
        }
        log.info("Request not send as being 'rate limited'");
        return HttpCode.PING_LIMITED_MSG;
    }
}
