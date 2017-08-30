package com.coder4.sbmvt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;


public class RedisJsonSerializer<T> implements RedisSerializer<T> {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    protected ObjectMapper objectMapper;

    protected Class<T> cls;

    public RedisJsonSerializer(Class<T> cls) {
        objectMapper = new ObjectMapper();
        this.cls = cls;
    }

    @Override
    public byte[] serialize(T o) throws SerializationException {
        byte[] defReturn = new byte[1];
        try {
            if (o == null) {
                return defReturn;
            }
            return objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            LOG.error("objectMapper writeValueAsBytes exception", e);
            return defReturn;
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        try {
            if (bytes == null) {
                return null;
            }
            return objectMapper.readValue(bytes, cls);
        } catch (Exception e) {
            LOG.error("objectMapper readValue exception", e);
            return null;
        }
    }

}