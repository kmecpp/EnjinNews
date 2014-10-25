package me.kmecpp.enjinnews.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLShortener {

	public static String shorten(String longUrl) {
		String api = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyASlUW_1acfGk7PcDdBb-qU3oEY3bv9kOY";
		String shortUrl = "";
		try {
			URLConnection conn = new URL(api).openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write("{\"longUrl\":\"" + longUrl + "\"}");
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				if (line.indexOf("id") > -1) {
					shortUrl = line.substring(8, line.length() - 2);
				}
			}
			wr.close();
			rd.close();
		} catch (MalformedURLException localMalformedURLException) {
		} catch (IOException localIOException) {
		}
		return shortUrl;
	}

}
