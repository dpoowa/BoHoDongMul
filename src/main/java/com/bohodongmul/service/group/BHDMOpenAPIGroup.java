package com.bohodongmul.service.group;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bohodongmul.service.BHDMRequestAPIService;
import com.bohodongmul.vo.BHDMItemsVo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BHDMOpenAPIGroup {
	private final BHDMRequestAPIService adoptionGetJson;
	

	public Mono<ResponseEntity<BHDMItemsVo>> executeAdoptionGetJson(HttpServletRequest request) {
		return adoptionGetJson.execute(request);
	}

}
