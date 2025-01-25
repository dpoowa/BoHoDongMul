package com.bohodongmul.service;

import java.time.Duration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bohodongmul.configuration.ApikeyConfig;
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
public class BHDMCallAPI {
	
	private final ApikeyConfig apikeyConfig;
	private final WebClient webClient;
	private final ObjectMapper objectMapper;
	private final RedisCacheService cacheService;	
	private final CacheManager cacheManager;
	
	public Mono<ResponseEntity<BHDMItemsVo>> fetchData(HttpServletRequest request, String paramUpkind, String paramPageNo) {
		log.info("Fetching data for upkind: {}", paramUpkind);
		log.info("Fetching data for pageNo: {}", paramPageNo);
		
        String cacheKey = paramUpkind + "_" + paramPageNo;
        Object cachedData = cacheService.getFromCache(cacheKey);
		
        if (cachedData != null) {
            // 캐시된 데이터가 있으면 바로 반환
        	System.out.println("캐싱데이터: " + cachedData.toString());
            return Mono.just(ResponseEntity.ok((BHDMItemsVo) cachedData));
        }
        System.out.println("캐시드데이터가 NULL");
		return webClient.get().uri(buildUrl(request, paramUpkind))
				.retrieve()
				.onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new Exception("Client Error")))
				.onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new Exception("Server Error")))
				.bodyToMono(String.class)
				.retryWhen(Retry.backoff(2, Duration.ofSeconds(2)).maxBackoff(Duration.ofSeconds(10)))
				.map(json -> {
					BHDMItemsVo bhdmItems;
					try {
						bhdmItems = parsingJsonObject(json, BHDMItemsVo.class);
						cacheService.saveToCache(cacheKey, bhdmItems);
						return ResponseEntity.ok(bhdmItems);
					} catch (Exception e) {
						log.error("error parsing JSON", e);
						return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BHDMItemsVo(List.of()));
					}
				});
	}
	
	private String buildUrl(HttpServletRequest request, String paramUpkind) {
		String addParameters = "";
		
		String baseUrl = "https://apis.data.go.kr/1543061/abandonmentPublicSrvc/abandonmentPublic";

		String apikey = "?serviceKey=" + apikeyConfig.getOpenDataApiKey();
		String pageNo = "&pageNo=" + request.getParameter("pageNo");

		String upr_cd = setValueOfParam(request, "upr_cd");
		String org_cd = setValueOfParam(request, "org_cd");
		String numOfRows = "&numOfRows=" + "80";
		String _type = "&_type=" + "json";
		String extraParam = pageNo + numOfRows + _type;
		
		if(!paramUpkind.equals("any")) {
			String upkind = "&upkind=" + paramUpkind;
			addParameters = apikey + upr_cd + org_cd + upkind + extraParam;
		} else {
			addParameters = apikey + upr_cd + org_cd + extraParam;
		}
		log.info("log for built URL: {}", baseUrl + addParameters);
		return baseUrl + addParameters;
	}

	private String setValueOfParam(HttpServletRequest request, String paramName) {
		String paramValue = request.getParameter(paramName);
		return (paramValue != null && !paramValue.equals("any")) ? "&" + paramName + "=" + paramValue : "";
	}

	
	<T> T parsingJsonObject(String json, Class<T> valueType) throws Exception {
	
		try {
			T result = objectMapper.readValue(json, valueType);
			return result;
		} catch (ValueInstantiationException e) {
			throw new Exception("Failed to instantiate the class: " + valueType.getName(), e);
		} catch (JsonParseException e) {
			throw new Exception("JSON parse error: " + e.getMessage(), e);
		} catch (JsonMappingException e) {
			throw new Exception("JSON mapping error: " + e.getMessage(), e);
		} catch (Exception e) {
			throw new Exception("General error while parsing JSON: " + e.getMessage(), e);
		}

	}
}
