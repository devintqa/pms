package com.psk.pms.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonHelper {
	
	private static final Logger LOGGER = Logger.getLogger(MailClient.class);

	@SuppressWarnings("unchecked")
	public static Map<String, String> jsonToMap(String json) {
		Map<String, String> map = null;
		ObjectMapper mapper;

		try {
			map = new HashMap<String, String>();
			mapper = new ObjectMapper();
			map = mapper.readValue(json, HashMap.class);
		} catch (IOException e) {
			LOGGER.error("Json Helper Map: Error while reading json object", e);
		}
		return map;
	}
}
