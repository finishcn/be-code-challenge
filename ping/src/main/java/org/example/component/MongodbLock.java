/**
 * liyu.caelus 2024/12/29
 * Copyright
 */
package org.example.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author liyu.caelus 2024/12/29
 */
@Slf4j
//@Component
//@Configuration
public class MongodbLock {

//    @Autowired
//    private MongoTemplate mongoTemplate;

    @Value("${config.num:2}")
    private Integer num;

    private final static String LOCK_ID = "pingLock";

    /*@PostConstruct
    public void init() {
        Query query = new Query(Criteria.where("id").is(LOCK_ID));
        if (!mongoTemplate.exists(query, Lock.class)) {
            mongoTemplate.insert(Lock.builder().id(LOCK_ID).num(num).build());
        } else {
            Update update = new Update().set("id", LOCK_ID).set("num", num);
            mongoTemplate.updateFirst(query, update, Lock.class);
        }
    }

    @Transactional
    public boolean lock() {
        Query query = new Query(Criteria.where("id").is(LOCK_ID));
        Lock lock = mongoTemplate.findOne(query, Lock.class);
        if (null != lock && lock.num > 0) {
            log.info(lock.toString());
            Update update = new Update().set("id", LOCK_ID).set("num", lock.num - 1);
            mongoTemplate.updateFirst(query, update, Lock.class);
            return true;
        }
        return false;
    }

    @Transactional
    public void unlock() {
        Query query = new Query(Criteria.where("id").is(LOCK_ID));
        Lock lock = mongoTemplate.findOne(query, Lock.class);
        if (null != lock && lock.num < this.num) {
            log.info(lock.toString());
            Update update = new Update().set("id", LOCK_ID).set("num", lock.num + 1);
            mongoTemplate.updateFirst(query, update, Lock.class);
        }
    }*/


   /* @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Lock {
        private String id;
        private Integer num;
    }*/

    /*@Bean
    @ConditionalOnProperty(name = "spring.data.mongodb.transactionEnabled", havingValue = "true")
    public MongoTransactionManager transactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }*/
}
