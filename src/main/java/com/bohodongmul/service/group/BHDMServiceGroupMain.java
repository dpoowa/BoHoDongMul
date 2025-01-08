package com.bohodongmul.service.group;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bohodongmul.vo.BHDMItemsVo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BHDMServiceGroupMain {
	private final BHDMOpenAPIGroup adoptionGroup;
		

	public Mono<ResponseEntity<BHDMItemsVo>> executeAdoptionGetJson(HttpServletRequest request) {
		return adoptionGroup.executeAdoptionGetJson(request);
	}

	
}
