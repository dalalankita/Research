package org.jersey.demo.messenger.resources;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.jersey.demo.messenger.CommonMethods;
import org.jersey.demo.messenger.MatchProfile;
import org.jersey.demo.messenger.TourLogger;
import org.jersey.demo.messenger.java.UserProfileDB;
import org.json.JSONArray;
import org.json.JSONObject;


@Path("/tourplan")
public class MessageResource {

	private static TourLogger logger = TourLogger.getInstance();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getMsg(String tourDetails) {
		logger.info("************* REST API ***************");
		JSONObject finalObj = new JSONObject();
		try {
			JSONObject tourObj = new JSONObject(tourDetails);
			logger.info("TOUR DETAILS OBJECT: "+tourDetails.toString());

			String touristName = tourObj.getString("name");
			String email = tourObj.getString("email");
			String dob = tourObj.getString("birthday");
			String gender = tourObj.getString("gender");
			long fbId = tourObj.getLong("id");
			JSONObject locObj = tourObj.getJSONObject("location");

			String location = locObj.getString("name");
			int i=0;
			String[] token = location.split(",");
			String country = token[1];
			String city = token[0];
			
			//DummyData.addDummyData();
			
			ProfileCompare.compare();

			//logger.info("CITY: "+city+" COUNTRY: "+country);
			//logger.info("Name: "+touristName+" Email: "+email);

			boolean userExist = CommonMethods.checkUserExist(fbId);

			JSONArray photo = new JSONArray();
			if (tourObj.has("photos")) {
				photo = tourObj.getJSONObject("photos").optJSONArray("data");
				//logger.info("TOURIST PHOTOS: "+photo.toString());
			}

			JSONArray tag = new JSONArray();
			if (tourObj.has("tagged_places")) {
				tag = tourObj.getJSONObject("tagged_places").optJSONArray("data");
				//logger.info("TOURIST TAGGED PHOTOS: "+tag.toString());
			}

			/*if (userExist == false) {
				UserProfileDB.setBasicUserInfo(fbId,touristName, email, dob,gender);
				UserProfileDB.setUserPersonalInfo(fbId, country, city);
				ArrayList<String> poi = UserProfileDB.setPlacesVistitedInfo(photo,tag,fbId);
			} else {
				UserProfileDB.editUserPersonalInfo(fbId,country,city);
				UserProfileDB.addNewPlaces(fbId,photo,tag);
			}*/
			
			
			//finalObj = MatchProfile.getUserData(fbId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
		//return finalObj.toString();
	}
}
