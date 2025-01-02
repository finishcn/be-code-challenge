/**
 * liyu.caelus 2025/1/2
 * Copyright
 */
package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * mq message
 *
 * @author liyu.caelus 2024/12/31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MqMessageDTO {

    private String id;
    private String ping;
    private String pong;
}
