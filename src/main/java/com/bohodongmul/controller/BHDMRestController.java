package com.bohodongmul.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bohodongmul.service.group.BHDMServiceGroup;
import com.bohodongmul.vo.BHDMItemsVo;

import reactor.core.publisher.Mono;

@RestController
//@CrossOrigin(origins = "http://localhost:9001")
public class BHDMRestController {
	
	final BHDMServiceGroup serviceGroup;
	
	public BHDMRestController(BHDMServiceGroup serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
		
	@GetMapping("/bohodongmul/api/data")
	public Mono<ResponseEntity<BHDMItemsVo>> adoptionGetJson(HttpServletRequest request)
			throws Exception {
		return serviceGroup.getData(request);
	}

}
