/**
 * liyu.caelus 2025/1/1
 * Copyright
 */
package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * message entity
 *
 * @author liyu.caelus 2024/12/31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PingMessage {

    /**
     * data id
     */
    private String id;
    /**
     * data content
     */
    private String message;
}
