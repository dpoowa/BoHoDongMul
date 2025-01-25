package com.bohodongmul.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    // Redis에 캐시 저장 (1시간 동안 유효)
    public void saveToCache(String key, Object value) {
    	System.out.println("key"+key);
        redisTemplate.opsForValue().set(key, value, 1, TimeUnit.HOURS); // 1시간 동안 저장
        renameKey("\"" + key + "\"");
    }

    // Redis에서 캐시 조회
    public Object getFromCache(String key) {
        return redisTemplate.opsForValue().get(key); // key로 캐시 조회
    }

    // 캐시 삭제
    public void deleteFromCache(String key) {
        redisTemplate.delete(key); // key에 해당하는 캐시 삭제
    }
    
    public void renameKey(String oldKey) {
    	String newKey = oldKey.replace("\\\"", "");
    	System.out.println("old: " + oldKey + " | newKey: " + newKey);
        try {
            // RedisTemplate을 사용하여 RENAME 명령어 실행
            redisTemplate.opsForValue().getOperations().rename(oldKey, newKey);
        } catch (Exception e) {
            System.err.println("Error renaming key: " + e.getMessage());
        }
    }
}
