package com.kmecpp.enjinnews;

import com.kmecpp.osmium.api.config.Configuration;
import com.kmecpp.osmium.api.config.Setting;

@Configuration(path = "plugin.conf")
public class Config {

	@Setting
	public static boolean debug;

	@Setting
	public static String apiKey;

	@Setting
	public static String apiUrl;

}
