package com.bohodongmul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bohodongmul.configuration.ApikeyConfig;


@Controller
public class BHDMController {
	
	final ApikeyConfig apikeyConfig;
	
	public BHDMController(ApikeyConfig apikeyConfig) {
		this.apikeyConfig = apikeyConfig;
	}
	
	@GetMapping("/") // 입양 센터 메인 페이지
	public String adoptionMain(Model model) {
		model.addAttribute("apiKey", apikeyConfig.getKakaoApiKey());
		return "/bohodongmul/bhdm_main";
	}
	
}
