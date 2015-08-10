package com.psk.pms.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelper {

	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(String json) {
		Map<String, String> map = null;
		ObjectMapper mapper;

		try {
			map = new HashMap<String, String>();
			mapper = new ObjectMapper();
			map = mapper.readValue(json, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
