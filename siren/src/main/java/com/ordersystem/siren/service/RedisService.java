package com.ordersystem.siren.service;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisService {
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

}
