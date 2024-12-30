package org.example.mapped.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.example.constant.MappedConstant;
import org.slf4j.MDC;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalChannelInterceptor
public class MqInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> msg, MessageChannel mc) {
        String traceId = (String) msg.getHeaders().get(MappedConstant.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = MDC.get(MappedConstant.TRACE_ID);
            if (StringUtils.isNotBlank(traceId)) {
                return MessageBuilder.fromMessage(msg).setHeader(MappedConstant.TRACE_ID, traceId).build();
            }
        } else {
            MDC.put(MappedConstant.TRACE_ID, traceId);
        }
        return msg;
    }

    @Override
    public void postSend(Message<?> msg, MessageChannel mc, boolean bln) {

    }

    @Override
    public void afterSendCompletion(Message<?> msg, MessageChannel mc, boolean bln, Exception excptn) {
    }

    @Override
    public boolean preReceive(MessageChannel mc) {
        return true;
    }

    @Override
    public Message<?> postReceive(Message<?> msg, MessageChannel mc) {
        String traceId = (String) msg.getHeaders().get(MappedConstant.TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            MDC.put(MappedConstant.TRACE_ID, traceId);
        }
        return msg;
    }

    @Override
    public void afterReceiveCompletion(Message<?> msg, MessageChannel mc, Exception excptn) {
        MDC.clear();
    }

}
