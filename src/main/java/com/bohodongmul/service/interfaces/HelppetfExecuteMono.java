package com.bohodongmul.service.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;


public interface HelppetfExecuteMono<T> {
	public Mono<ResponseEntity<T>> execute(HttpServletRequest request);
}
