/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * test message entity
 *
 * @author liyu.caelus 2024/12/31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pong_message")
public class PongMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    /**
     * service trace id
     */
    @Column(name = "trace_id")
    private String traceId;
    /**
     * ping send message
     */
    @Column(name = "ping_message")
    private String pingMessage;
    /**
     * ping pong response
     */
    @Column(name = "pong_message")
    private String pongMessage;
}
