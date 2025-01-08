package com.bohodongmul.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bohodongmul.service.group.BHDMServiceGroupMain;
import com.bohodongmul.vo.BHDMItemsVo;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bohodongmuls")
public class BHDMRestController {
	
	final BHDMServiceGroupMain serviceGroup;
	
	public BHDMRestController(BHDMServiceGroupMain serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
		
	@GetMapping
	public Mono<ResponseEntity<BHDMItemsVo>> adoptionGetJson(HttpServletRequest request)
			throws Exception {
		return serviceGroup.executeAdoptionGetJson(request);
	}

}
