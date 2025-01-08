package com.bohodongmul.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:com/properties/apikey.properties")
@Getter
public class ApikeyConfig {

	@Value("${kakaoApi.key}")
	private String kakaoApiKey;

	@Value("${openDataApi.key}")
	private String openDataApiKey;

}
