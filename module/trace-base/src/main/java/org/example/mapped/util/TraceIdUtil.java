/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.mapped.util;

import cn.hutool.core.util.RandomUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liyu.caelus
 */
public class TraceIdUtil {

    private String hostname;

    private final ThreadLocalRandom randomUtil = RandomUtil.getRandom();

    private final static TraceIdUtil trace = new TraceIdUtil();

    private TraceIdUtil() {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            hostname = RandomUtil.randomString(6);
            e.printStackTrace();
        }
    }

    public static String getGenerateTraceId() {
        return trace.generateTraceId();
    }

    public String generateTraceId() {
        return String.valueOf(randomUtil.nextInt());
    }
}
