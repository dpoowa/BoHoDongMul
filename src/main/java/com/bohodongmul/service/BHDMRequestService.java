package com.bohodongmul.service;

import java.time.Duration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bohodongmul.configuration.ApikeyConfig;
import com.bohodongmul.service.interfaces.HelppetfExecuteMono;
import com.bohodongmul.vo.BHDMItemsVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Service
@AllArgsConstructor
@Slf4j
public class BHDMRequestService implements HelppetfExecuteMono<BHDMItemsVo>{
	
	private final BHDMCallAPI callAPI;
	private final CacheManager cacheManager;
	
	@Override
	public Mono<ResponseEntity<BHDMItemsVo>> execute(HttpServletRequest request) {
		try {
			return fetchBHDMData(request);
		} catch (Exception e) {
			e.printStackTrace();
			return Mono.error(new RuntimeException("Json 데이터를 불러오는 것에 실패하였습니다.", e));
		}
		
	}
	
	public Mono<ResponseEntity<BHDMItemsVo>> fetchBHDMData(HttpServletRequest request) throws Exception {
		String upkind = request.getParameter("upkind");
		String pageNo = request.getParameter("pageNo");
		String key = upkind + "_" + pageNo;
        System.out.println(key);
        Cache cache = cacheManager.getCache("bhdmData");
        if (cache != null && cache.get(key) != null) {
            log.info("Cache HIT - Key: {}", key);
        } else {
            log.info("Cache MISS - No cached data for key: {}", key);
        }
		
		return callAPI.fetchData(request, upkind, pageNo);
	}
	
}
