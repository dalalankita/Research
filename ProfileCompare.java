package org.jersey.demo.messenger.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jersey.demo.messenger.CommonMethods;
import org.jersey.demo.messenger.TourLogger;
import org.jersey.demo.messenger.pojo.PlacesVisited;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileCompare {

	private static TourLogger logger = TourLogger.getInstance();

	public static void compare() {
		List<PlacesVisited> visit = new ArrayList<PlacesVisited>();
		Session session = CommonMethods.getSession();
		String sqlStr = "from PlacesVisited";
		Query query = session.createQuery(sqlStr);
		visit = query.list();
		FileWriter file =null;
		String fileName = System.getProperty("user.home")+"/profileMatch.csv";
		try {
			file = new FileWriter(fileName);
			file.append("fbId1,fbId2,Percentage");	
			file.append("\n");
			for (int i=0;i<visit.size();i++) {
				PlacesVisited place = visit.get(i);
				long fbId = place.getFbId();
				String countries = place.getCountry();
				JSONObject placeObj = new JSONObject(countries);
				JSONArray placeArr = (JSONArray) placeObj.get("country");

				for (int j=0;j<visit.size();j++) {
					PlacesVisited place1 = visit.get(j);
					List<String> common = new ArrayList<String>();
					long fbId1 = place1.getFbId();
					if (fbId == fbId1) {
						logger.info("********** SAME RECORD ***********");
					} else {
						String countries1 = place1.getCountry();
						JSONObject placeObj1 = new JSONObject(countries1);
						JSONArray placeArr1 = (JSONArray) placeObj1.get("country");

						for (int k=0;k<placeArr1.length();k++) {
							String country = placeArr1.getString(k);

							if (placeArr.toString().contains(country)) {
								common.add(country);
							}
						}
						/*int countr1 = placeArr.length();
						int country2 = common.size();
						float perSimilar = (country2/countr1)*100;
						file.append(String.valueOf(fbId));
						file.append(",");
						file.append(String.valueOf(fbId1));
						file.append(",");
						file.append(String.valueOf(perSimilar));
						file.append("\n");*/
						if (common.isEmpty() == false){
							int countr1 = placeArr.length();
							int country2 = common.size();
							float perSimilar = (country2 * 100)/countr1;
							file.append(String.valueOf(fbId));
							file.append(",");
							file.append(String.valueOf(fbId1));
							file.append(",");
							file.append(String.valueOf(perSimilar));
							file.append("\n");
							logger.info("********** COMMON: "+common.toString());
							logger.info("********** PLACE TO COMPARE: "+placeArr.toString()+"|||| "+placeArr1.toString());	
						}
					}
				}
			}
			logger.info("CSV file was created successfully !!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
