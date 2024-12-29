/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

/**
 * @author liyu.caelus 2024/12/29
 */
public class SimplifiedHttp {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            HttpRequest request = HttpUtil.createGet("http://localhost:8001/pong/service?msg=Hello");
            HttpResponse response = request.execute();
            System.out.println(response.body() + ":" + response.getStatus());
            response.close();
        }
    }
}
