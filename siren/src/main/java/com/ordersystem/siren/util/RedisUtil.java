package com.ordersystem.siren.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object o, long milliseconds){
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        redisTemplate.opsForValue().set(key, o, milliseconds, TimeUnit.MILLISECONDS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
    public boolean delete(String key){
        return redisTemplate.delete(key);
    }
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
}
