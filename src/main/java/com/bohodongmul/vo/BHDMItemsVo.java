package com.bohodongmul.vo;

import java.util.List;

import com.bohodongmul.deserializer.BHDMDeserializer;
import com.bohodongmul.dto.JSONItemDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;

@Getter
@JsonDeserialize(using = BHDMDeserializer.class)
public class BHDMItemsVo {
	
	@JsonProperty("item") 
	private final List<JSONItemDto> item;
	
	public BHDMItemsVo(@JsonProperty List<JSONItemDto> item) {
		this.item = item;
	}
}
