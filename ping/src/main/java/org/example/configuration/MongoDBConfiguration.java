/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.MappedConstant;
import org.example.entity.PingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * MongoDB config
 *
 * @author liyu.caelus 2024/12/31
 */
@Slf4j
@Configuration
public class MongoDBConfiguration {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * init mongodb message data
     */
    @PostConstruct
    public void init() {
        Query query = new Query(Criteria.where("id").is(MappedConstant.MSG_ID));
        if (!mongoTemplate.exists(query, PingMessage.class)) {
            // without
            mongoTemplate.insert(PingMessage.builder().id(MappedConstant.MSG_ID).message(MappedConstant.REQUEST_MSG).build());
        }
    }

    /**
     * create mongodb Transaction Manager
     */
    @Bean
    @ConditionalOnProperty(name = "spring.data.mongodb.transactionEnabled", havingValue = "true")
    public MongoTransactionManager transactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }
}
