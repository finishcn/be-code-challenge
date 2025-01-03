/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.util;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import org.example.mapped.pool.StringBuilderPool;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * create trace id util
 *
 * @author liyu.caelus
 */
public class TraceIdUtil {

    private String hostname;

    private final ThreadLocalRandom randomUtil = RandomUtil.getRandom();

    private final static TraceIdUtil trace = new TraceIdUtil();

    private TraceIdUtil() {
        try {
            hostname = PinyinUtil.getPinyin(InetAddress.getLocalHost().getHostName(), "");
        } catch (UnknownHostException e) {
            hostname = RandomUtil.randomString(6);
        }
    }

    public static String getGenerateTraceId() {
        return trace.generateTraceId();
    }

    public String generateTraceId() {
        StrBuilder sb = StringBuilderPool.getObject();
        Objects.requireNonNull(sb).append(hostname).append("-").append(randomUtil.nextInt());
        String traceId = sb.toString();
        StringBuilderPool.returnObject(sb);
        return traceId;
    }
}
