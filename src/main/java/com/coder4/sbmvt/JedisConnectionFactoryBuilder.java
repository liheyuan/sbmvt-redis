/**
 * @(#)JedisConnectionFactoryConfiguration.java, Aug 30, 2017.
 * <p>
 * Copyright 2017 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.coder4.sbmvt;

import com.coder4.sbmvt.MyRedisProperties.Pool;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author coder4
 */
public class JedisConnectionFactoryBuilder {

    public static JedisConnectionFactory build(MyRedisProperties properties) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        // host port pass from url
        if (StringUtils.hasText(properties.getUrl())) {
            configureConnectionFromUrl(jedisConnectionFactory, properties.getUrl());
        } else {
            throw new IllegalArgumentException("invalid 'redis.url' ");
        }

        // pool config
        jedisConnectionFactory.setUsePool(true);
        JedisPoolConfig poolConfig = properties.getPool() != null
                ? getJedisPoolConfig(properties.getPool()) : new JedisPoolConfig();
        jedisConnectionFactory.setPoolConfig(poolConfig);

        return jedisConnectionFactory;
    }

    private static void configureConnectionFromUrl(JedisConnectionFactory factory, String url) {
        if (url.startsWith("rediss://")) {
            factory.setUseSsl(true);
        }
        try {
            URI uri = new URI(url);
            factory.setHostName(uri.getHost());
            factory.setPort(uri.getPort());
            if (uri.getUserInfo() != null) {
                String password = uri.getUserInfo();
                int index = password.lastIndexOf(":");
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
                factory.setPassword(password);
            }
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Malformed 'redis.url' " + url,
                    ex);
        }
    }

    private static JedisPoolConfig getJedisPoolConfig(Pool pool) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        config.setMaxWaitMillis(pool.getMaxWait());
        return config;
    }
}