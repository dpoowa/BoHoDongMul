package com.bohodongmul.vo;

import java.util.List;

import com.bohodongmul.deserializer.BHDMDeserializer;
import com.bohodongmul.dto.JSONItemDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;

@Getter
@JsonDeserialize(using = BHDMDeserializer.class)
/**
 * 보호 동물 조회 API 응답 데이터를 담는 VO이다. JSON 데이터를 HelpPetfAdoptionItemsVo 
 * 객체로 매핑할 때 AdoptionDeserializer를 통해 "item" 필드만을 매핑하도록 설정된다.
 */
public class BHDMItemsVo {
	
	@JsonProperty("item") 
	private final List<JSONItemDto> adoptionItemDto;
	
	// 생성자의 매개변수 items는 AdoptionDeserializer에서 리턴해줄 때 전달받은 객체이다.
	// Json을 트리 노드로 변환하여 "item" 필드만 저장한 객체를 
	// AdoptionItemDto 타입의 List로 변환한 것이다.
	public BHDMItemsVo(List<JSONItemDto> items) {
		this.adoptionItemDto = items;
	}
}
