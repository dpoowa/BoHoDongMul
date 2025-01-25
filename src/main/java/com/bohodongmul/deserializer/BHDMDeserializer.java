package com.bohodongmul.deserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bohodongmul.dto.JSONItemDto;
import com.bohodongmul.vo.BHDMItemsVo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

public class BHDMDeserializer extends JsonDeserializer<BHDMItemsVo> {
	
	public BHDMDeserializer() {
	}
	
	@Override
	public BHDMItemsVo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonNode node = p.getCodec().readTree(p);

		JsonNode itemNode = node.findValue("item");
		
		if (itemNode == null || !itemNode.isArray()) {
			throw new IOException("invalid or missing 'item' field");
		}
		
		List<JSONItemDto> items = Arrays.stream(
				objectMapper.treeToValue(itemNode, JSONItemDto[].class)
				).collect(Collectors.toList());

		return new BHDMItemsVo(items);
	}

}
