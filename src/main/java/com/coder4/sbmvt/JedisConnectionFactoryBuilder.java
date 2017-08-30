/**
 * @(#)JedisConnectionFactoryConfiguration.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author coder4
 */
public class JedisConnectionFactoryBuilder {

    public static JedisConnectionFactory build(MyRedisProperties properties) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(properties.getHost());
        jedisConnectionFactory.setPort(properties.getPort());
        jedisConnectionFactory.setDatabase(properties.getDatabase());
        jedisConnectionFactory.setPassword(properties.getPassword());
        jedisConnectionFactory.setUsePool(true);
        // TODO Pool Config
        jedisConnectionFactory.setPoolConfig(new JedisPoolConfig());
        return jedisConnectionFactory;
    }
}