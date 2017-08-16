package org.jersey.demo.messenger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.jersey.demo.messenger.pojo.FinalDestination;
import org.json.JSONArray;
import org.json.JSONObject;

public class Itinerary {

	private static TourLogger logger = TourLogger.getInstance();

	public static JSONObject getIteneraryForCity(List<FinalDestination> finalDestination) {
		
		JSONArray poiArr = new JSONArray();
		JSONObject poiObj = new JSONObject();

		for (int i=0;i<finalDestination.size();i++) {
			FinalDestination finalDest = finalDestination.get(i);
			String city = finalDest.getCity();
			List<String> cityList = Arrays.asList(city.split(","));

			for (int j=0;j<cityList.size();j++) {
				String str = cityList.get(j);
				String str2 = str.replaceAll("[\\[\\]]", "");
				str2 = str2.trim();
				String finalCity = str2.replaceAll(" ", "+");
				//logger.info("*********** ITINERARY CITY NAME: "+finalCity);
				JSONObject obj = new JSONObject();
				obj = getCityPOI(finalCity,"key"+j);				
				poiArr.put(obj);
				//logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				//logger.info("*******JSON RESULT************* "+finalCity+" ----> "+poiArr.toString());
			}			
		}
		poiObj.put("poi", poiArr);
		return poiObj;		
	}

	private static JSONObject getCityPOI(String finalCity, String key) {

		JSONObject obj = new JSONObject();
		StringBuilder jsonResults = new StringBuilder();
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+finalCity+"+point+of+interest&language=en"
					+ "&key=AIzaSyDD6akSTDD7KTt1qOHrAgL9oP3eg9rpz4Q");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			int read;
			char[] buff = new char[1024];

			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
			//logger.info("*******JSON RESULT************* "+finalCity+" ----> "+jsonResults.toString());
			//logger.info("===========================================================================================");

			JSONObject obj1 = new JSONObject(jsonResults.toString());
			obj.put(key, obj1);


		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

}





