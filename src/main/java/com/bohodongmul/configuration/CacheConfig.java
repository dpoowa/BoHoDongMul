package com.bohodongmul.configuration;

import java.time.Duration;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableCaching
@EnableScheduling
@Slf4j
public class CacheConfig {
	
	private final CacheManager cacheManager;
	
	public CacheConfig(@Lazy CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public void logCacheStatus(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName); 
		if(cache != null && cache.get(key) != null) {
			log.info("Cache HIT - Found cached data for key: {}" + key);
		} else {
			log.info("Cache MISS - No cached data for key: {}" + key);
		}
	}
	

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(serializer);  // Key에 대한 Serializer
        template.setValueSerializer(serializer); // Value에 대한 Serializer
        return template;
    }
	
//	@CacheEvict(value = "rentBikeStatus", allEntries = true)
//	@Scheduled(fixedRate = 300000) // 5분마다 실행
//	public void clearBikeCache() {
//		System.out.println("'rentBikeStatus'캐시를 갱신합니다.");
//	}
	
}
