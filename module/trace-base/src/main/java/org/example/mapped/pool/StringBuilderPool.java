/**
 * liyu.caelus 2024/12/31
 * Copyright
 */
package org.example.mapped.pool;

import cn.hutool.core.text.StrBuilder;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.time.Duration;

/**
 * StringBuilder pools
 *
 * @author liyu.caelus 2024/12/31
 */
public class StringBuilderPool {

    private static final GenericObjectPool<StrBuilder> pools;

    static {
        StringBuilderFactory factory = new StringBuilderFactory();
        pools = new GenericObjectPool<>(factory, factory.getConf());
    }

    private static class StringBuilderFactory extends BasePooledObjectFactory<StrBuilder> {

        public GenericObjectPoolConfig<StrBuilder> getConf() {
            GenericObjectPoolConfig<StrBuilder> conf = new GenericObjectPoolConfig<>();
            conf.setMaxTotal(256);
            conf.setMinIdle(1);
            conf.setMaxIdle(128);
            conf.setMaxWait(Duration.ofSeconds(5));
            return conf;
        }

        @Override
        public StrBuilder create() {
            return new StrBuilder();
        }

        @Override
        public PooledObject<StrBuilder> wrap(StrBuilder sb) {
            return new DefaultPooledObject<>(sb);
        }
    }

    public static StrBuilder getObject() {
        try {
            return pools.borrowObject();
        } catch (Exception e) {
            return new StrBuilder();
        }
    }

    public static void returnObject(StrBuilder sb) {
        sb.clear();
        try {
            pools.returnObject(sb);
        } catch (Exception ignored) {

        }
    }
}
