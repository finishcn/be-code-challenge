/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.dao;

import org.example.entity.PongMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * test message dao
 *
 * @author liyu
 * @version 1.0
 */
@Repository
public interface PongMessageRepository extends JpaRepository<PongMessage, Integer> {
}
