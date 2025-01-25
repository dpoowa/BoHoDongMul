package com.bohodongmul.service.group;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bohodongmul.service.BHDMRequestService;
import com.bohodongmul.vo.BHDMItemsVo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BHDMServiceGroup {
	private final BHDMRequestService bhdmAPI;
	
	public Mono<ResponseEntity<BHDMItemsVo>> getData(HttpServletRequest request) {
		return bhdmAPI.execute(request);
	}
}
