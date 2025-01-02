/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.feign.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * feign client http response
 * @author liyu.caelus 2024/12/31
 */
@Data
@AllArgsConstructor
public class FeignResponse {
    private Integer httpCode;
    private String body;
    private Map<String, List<String>> headers;
}
