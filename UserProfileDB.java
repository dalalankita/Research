package org.jersey.demo.messenger.java;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jersey.demo.messenger.CommonMethods;
import org.jersey.demo.messenger.TourLogger;
import org.jersey.demo.messenger.pojo.PlacesVisited;
import org.jersey.demo.messenger.pojo.TouristDetails;
import org.jersey.demo.messenger.pojo.UserBasicProfile;
import org.jersey.demo.messenger.pojo.UserPersonalDetails;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserProfileDB {

	private static TourLogger logger = TourLogger.getInstance();

	public static void setBasicUserInfo(long fbId, String touristName, String email, String dob, String gender) {

		UserBasicProfile profile = new UserBasicProfile();
		profile.setDob(dob);
		profile.setEmail(email);
		profile.setGender(gender);
		profile.setTouristName(touristName);
		profile.setFbId(fbId);

		try {
			//create session
			Session session= CommonMethods.getSession();
			logger.info("**************** (setTouristDetails)Session created successfull******************");

			//start transaction
			Transaction transaction = session.getTransaction();
			transaction.begin();
			logger.info("**************** (setTouristDetails)Transaction started successfully******************");

			//save object
			session.save(profile);
			logger.info("**************** (setTouristDetails)Session saved successfull******************");

			transaction.commit();
			session.close();
			logger.info("**************** (setTouristDetails)Transaction commited successfull****************** ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setUserPersonalInfo(long fbId, String country, String city) {

		UserPersonalDetails details = new UserPersonalDetails();
		details.setCities(city);
		details.setCountries(country);
		details.setFbId(fbId);

		try {
			//create session
			Session session= CommonMethods.getSession();
			logger.info("**************** (setUserPersonalInfo)Session created successfull******************");

			//start transaction
			Transaction transaction = session.getTransaction();
			transaction.begin();
			logger.info("**************** (setUserPersonalInfo)Transaction started successfully******************");

			//save object
			session.save(details);
			logger.info("**************** (setUserPersonalInfo)Session saved successfull******************");

			transaction.commit();
			session.close();
			logger.info("**************** (setUserPersonalInfo)Transaction commited successfull****************** ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<String> setPlacesVistitedInfo(JSONArray photo, JSONArray tag, long fbId) {

		//logger.info("****************TOURIST PHOTOS: "+photo.toString());

		//logger.info("***************TOURIST TAGGED PHOTOS: "+tag.toString());

		JSONArray cityArr = new JSONArray();
		JSONArray countryArr = new JSONArray();
		JSONObject cityObj = new JSONObject();
		JSONObject countryObj = new JSONObject();
		ArrayList<String> poi = new ArrayList<String>();

		if (photo != null) {
			for (int i=0;i<photo.length();i++) {
				JSONObject photoObj = photo.getJSONObject(i);
				String photoCity=null, photoCountry=null;
				if (photoObj.has("place")) {
					JSONObject locObj = photoObj.getJSONObject("place");
					if (locObj.has("name")) {
						String name = locObj.getString("name");
						if (poi.contains(name)) {
							logger.info("*************** POI EXISTS IN LIST(PHOTOS)***************");
						} else {
							poi.add(name);
						}
					}else {
						logger.info("************ NO POI PRESENT(PHOTOS)***********");
					}
					if (locObj.has("location")) {
						JSONObject loc = locObj.getJSONObject("location");
						if (loc.has("country")) {
							photoCountry = loc.getString("country");
							if (countryArr.toString().contains(photoCountry)) {
								logger.info("*****PHOTO COUNTRY VALUE ALREADY PRESENT********");
							} else {
								countryArr.put(photoCountry);
							}
							//logger.info("****** PHOTOD COUNTRY ARRAY: "+countryArr.toString());
						} else {
							logger.info("********* NO COUNTRY IN PHOTO*********");
						}
						if (loc.has("city")) {
							photoCity = loc.getString("city");
							if (cityArr.toString().contains(photoCity)) {
								logger.info("*****PHOTO CITY VALUE ALREADY PRESENT********");
							} else {
								cityArr.put(photoCountry+","+photoCity);
							}						
							//logger.info("****** PHOTOS CITY ARRAY: "+cityArr.toString());
						} else {
							logger.info("********* NO CITY IN PHOTO*********");
						}
					} else {
						logger.info("********* NO LOCATION IN PHOTO*********");
					}
				} else {
					logger.info("********* NO PLACES IN PHOTO*********");
				}
			}
		}

		if (tag != null) {
			for (int j=0;j<tag.length();j++) {
				JSONObject tagObj = tag.getJSONObject(j);
				String tagCity=null,tagCountry=null;
				if (tagObj.has("place")) {
					JSONObject locObj = tagObj.getJSONObject("place");
					if (locObj.has("name")) {
						String name = locObj.getString("name");
						if (poi.contains(name)) {
							logger.info("*************** POI EXISTS IN LIST(TAGGED PHOTOS)***************");
						} else {
							poi.add(name);
						}
					}else {
						logger.info("************ NO POI PRESENT(TAGGED PHOTOS)***********");
					}
					if (locObj.has("location")) {
						JSONObject loc = locObj.getJSONObject("location");
						if (loc.has("country")) {
							tagCountry = loc.getString("country");
							if (countryArr.toString().contains(tagCountry)) {
								logger.info("*****TAG PHOTO COUNTRY VALUE ALREADY PRESENT********");
							} else {
								countryArr.put(tagCountry);
							}
							//logger.info("****** TAG PHOTOS COUNTRY ARRAY: "+countryArr.toString());
						} else {
							logger.info("********* NO COUNTRY IN TAG PHOTO*********");
						}
						if (loc.has("city")) {
							tagCity = loc.getString("city");
							if (cityArr.toString().contains(tagCity)) {
								logger.info("*****TAG PHOTO CITY VALUE ALREADY PRESENT********");
							} else {
								cityArr.put(tagCountry+","+tagCity);
							}
						//	logger.info("****** TAG PHOTOS CITY ARRAY: "+cityArr.toString());
						} else {
							logger.info("********* NO CITY IN TAG PHOTO*********");
						}
						
					} else {
						logger.info("********* NO LOCATION IN TAG PHOTO*********");
					}
				} else {
					logger.info("********* NO PLACES IN TAG PHOTO*********");
				}
			}
		}

		cityObj.put("City",cityArr);
		countryObj.put("country", countryArr);

		/*logger.info("******* CITY JSON: "+cityObj.toString());
		logger.info("******* COUNTRY JSON: "+countryObj.toString());
		logger.info("****** POI: "+poi.toString());*/

		PlacesVisited visit = new PlacesVisited();
		visit.setFbId(fbId);
		visit.setCities(cityObj.toString());
		visit.setCountry(countryObj.toString());

		try {
			//create session
			Session session= CommonMethods.getSession();
			logger.info("**************** (setPlacesVistitedInfo)Session created successfull******************");

			//start transaction
			Transaction transaction = session.getTransaction();
			transaction.begin();
			logger.info("**************** (setPlacesVistitedInfo)Transaction started successfully******************");

			//save object
			session.save(visit);
			logger.info("**************** (setPlacesVistitedInfo)Session saved successfull******************");

			transaction.commit();
			session.close();
			logger.info("**************** (setPlacesVistitedInfo)Transaction commited successfull****************** ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return poi;


	}

	public static void editUserPersonalInfo(long fbId, String country, String city) {

		List<UserPersonalDetails> list = new ArrayList<UserPersonalDetails>();
		Session session = CommonMethods.getSession();
		String queryStr = "from UserPersonalDetails where fbId = :id";
		Query query = session.createQuery(queryStr);
		query.setParameter("id", fbId);
		list = query.list();
		UserPersonalDetails details = new UserPersonalDetails();
		for (int i=0;i<list.size();i++) {
			details = list.get(i);
			String dbCity = details.getCities();
			if (dbCity.equals(city)) {
				logger.info("**********NO CHANGE IN CURRENT CITY*********");
			} else {
				details.setCities(city);
			}
			String dbCountry = details.getCountries();
			if (dbCountry.equals(country)) {
				logger.info("**********NO CHANGE IN CURRENT COUNTRY**********");
			} else {
				details.setCountries(country);
			}
		}
		Transaction transaction = session.getTransaction();
		transaction.begin();
		logger.info("**************** (editUserPersonalInfo)Transaction started successfully******************");
		session.save(details);
		logger.info("**************** (editUserPersonalInfo)Session saved successfull******************");
		transaction.commit();
		logger.info("**************** (editUserPersonalInfo)Transaction commited successfull****************** ");
		session.close();
	}

	public static void addNewPlaces(long fbId, JSONArray photo, JSONArray tag) {
		List<PlacesVisited> list = new ArrayList<PlacesVisited>();
		JSONObject cityObj = new JSONObject();
		JSONObject countryObj = new JSONObject();
		Session session = CommonMethods.getSession();
		String queryStr = "from PlacesVisited where fbId = :id";
		Query query = session.createQuery(queryStr);
		query.setParameter("id", fbId);
		list = query.list();
		PlacesVisited places = new PlacesVisited();
		for (int i=0;i<list.size();i++) {
			places = list.get(i);

			String countries = places.getCountry();
			JSONObject placeObj = new JSONObject(countries);
			JSONArray placeArr = placeObj.getJSONArray("country");

			String cities = places.getCities();
			JSONObject placeCityObj = new JSONObject(cities);
			JSONArray placeCityArr = placeCityObj.getJSONArray("City");


			for (int j=0;j<photo.length();j++) {
				JSONObject photoObj = photo.getJSONObject(i);
				String photoCity=null, photoCountry=null;
				if (photoObj.has("place")) {
					JSONObject locObj = photoObj.getJSONObject("place");
					if (locObj.has("location")) {
						JSONObject loc = locObj.getJSONObject("location");
						if (loc.has("country")) {
							photoCountry = loc.getString("country");
							if (placeArr.toString().contains(photoCountry)) {
								logger.info("*****PHOTO COUNTRY VALUE ALREADY PRESENT********");
							} else {
								placeArr.put(photoCountry);
							}
							//logger.info("****** PHOTOS COUNTRY ARRAY: "+placeArr.toString());
						} else {
							logger.info("********* NO COUNTRY IN PHOTO*********");
						}
						if (loc.has("city")) {
							photoCity = loc.getString("city");
							if (placeCityArr.toString().contains(photoCountry+","+photoCity)) {
								logger.info("*****PHOTO CITY VALUE ALREADY PRESENT********");
							} else {
								placeCityArr.put(photoCountry+","+photoCity);
							}						
							//logger.info("****** PHOTOS CITY ARRAY: "+placeCityArr.toString());
						} else {
							logger.info("********* NO CITY IN PHOTO*********");
						}
					} else {
						logger.info("********* NO LOCATION IN TAG PHOTO*********");
					}
				}else {
					logger.info("********* NO PLACES IN PHOTO*********");
				}
			}

			for (int j=0;j<tag.length();j++) {
				JSONObject tagObj = tag.getJSONObject(j);
				String tagCity=null,tagCountry=null;
				if (tagObj.has("place")) {
					JSONObject locObj = tagObj.getJSONObject("place");
					if (locObj.has("location")) {
						JSONObject loc = locObj.getJSONObject("location");
						if (loc.has("country")) {
							tagCountry = loc.getString("country");
							if (placeArr.toString().contains(tagCountry)) {
								logger.info("*****TAG PHOTO COUNTRY VALUE ALREADY PRESENT********");
							} else {
								placeArr.put(tagCountry);
							}
							//logger.info("****** TAG PHOTOS COUNTRY ARRAY: "+placeArr.toString());
						} else {
							logger.info("********* NO COUNTRY IN TAG PHOTO*********");
						}
						if (loc.has("city")) {
							tagCity = loc.getString("city"); 
							if (placeCityArr.toString().contains(tagCountry+","+tagCity)) {
								logger.info("*****TAG PHOTO CITY VALUE ALREADY PRESENT********");
							} else {
								placeCityArr.put(tagCountry+","+tagCity);
							}
							//logger.info("****** TAG PHOTOS CITY ARRAY: "+placeCityArr.toString());
						} else {
							logger.info("********* NO CITY IN TAG PHOTO*********");
						}
						
					} else {
						logger.info("********* NO LOCATION IN TAG PHOTO*********");
					}
				} else {
					logger.info("********* NO PLACES IN TAG PHOTO*********");
				}
			}
			cityObj.put("City",placeCityArr);
			countryObj.put("country", placeArr);

			logger.info("******* CITY JSON: "+cityObj.toString());
			logger.info("******* COUNTRY JSON: "+countryObj.toString());

			places.setCities(cityObj.toString());
			places.setCountry(countryObj.toString());

		}

		Transaction transaction = session.getTransaction();
		transaction.begin();
		logger.info("**************** (addNewPlaces)Transaction started successfully******************");
		session.save(places);
		logger.info("**************** (addNewPlaces)Session saved successfull******************");
		transaction.commit();
		logger.info("**************** (addNewPlaces)Transaction commited successfull****************** ");
		session.close();

	}
}
