package org.jersey.demo.messenger.resources;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jersey.demo.messenger.CommonMethods;
import org.jersey.demo.messenger.TourLogger;
import org.jersey.demo.messenger.pojo.DemoLocation;
import org.jersey.demo.messenger.pojo.PlacesVisited;
import org.jersey.demo.messenger.pojo.UserBasicProfile;
import org.jersey.demo.messenger.pojo.UserPersonalDetails;
import org.json.JSONArray;
import org.json.JSONObject;

public class DummyData {

	private static TourLogger logger = TourLogger.getInstance();
	static String[] locale = Locale.getISOCountries();

	public static void addDummyData() {
		DataFactory df = new DataFactory();
		//List<DemoLocation> countryList = new ArrayList<DemoLocation>();
		Session session = CommonMethods.getSession();
		try {
			//Get User basic profile
			for (int i = 0; i < 500; i++) {
				List<DemoLocation> countryList = new ArrayList<DemoLocation>();
				//User profile
				//logger.info("**************** User Basic Profile ****************");
				String name = df.getFirstName() + " "+ df.getLastName();
				//logger.info("****** Name: "+name);
				String emailId = df.getEmailAddress();
				//logger.info("****** Email: "+emailId);
				Date date = df.getBirthDate();
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
				String dob = sdf.format(date);
				//logger.info("****** DOB: "+dob);
				long fbId = df.getNumberBetween(1, 10000000);
				String gender ="female";

				//logger.info("**************** User Personal Details **************");
				String city = df.getCity();
				//logger.info("****** Lives in City: "+city);
				String country = getUserCurrentCountry();
				//logger.info("****** Lives in Country: "+country);
				countryList = getPlacesVisitedDummy();
				logger.info("***** Places visited: "+countryList.toString());


				JSONArray cityArr = new JSONArray();
				JSONArray countryArr = new JSONArray();
				JSONObject cityObj = new JSONObject();
				JSONObject countryObj = new JSONObject();

				for (int j=0;j<countryList.size();j++) {
					DemoLocation loc = countryList.get(j);
					countryArr.put(loc.getCountry());
					cityArr.put(loc.getCity());
				}
				cityObj.put("City", cityArr);
				countryObj.put("country", countryArr);
				countryList.clear();

				logger.info("+++++++++++++++++++++++++++++++++ USER BASIC PROFILE +++++++++++++++++++++++++++++++++++++++++++++++");
				logger.info("****** FBId: "+fbId);
				logger.info("****** Name: "+name);
				logger.info("****** Email: "+emailId);
				logger.info("****** DOB: "+dob);
				logger.info("****** Gender: "+gender);
				logger.info("+++++++++++++++++++++++++++++++++ USER PERSONAL PROFILE +++++++++++++++++++++++++++++++++++++++++++++");
				logger.info("******* Current location country: "+country);
				logger.info("******* Current Location city: "+city);
				logger.info("+++++++++++++++++++++++++++++++++ Places Visited +++++++++++++++++++++++++++++++++++++++++++++");
				logger.info("******* Countries visited: "+countryObj.toString());
				logger.info("******* Cities visited: "+cityObj.toString());
				logger.info("=======================================================================================================");		

				addBasicProfile(fbId,name,emailId,dob,gender,session);
				addPersonalProfile(fbId, country, city,session);
				addPlacesVisited(fbId,countryObj,cityObj,session);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addPlacesVisited(long fbId, JSONObject countryObj, JSONObject cityObj, Session session) {

		PlacesVisited visit = new PlacesVisited();
		visit.setCities(cityObj.toString());
		visit.setCountry(countryObj.toString());
		visit.setFbId(fbId);

		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.save(visit);
		transaction.commit();

	}

	private static void addPersonalProfile(long fbId, String country, String city, Session session) {

		UserPersonalDetails details = new UserPersonalDetails();
		details.setCities(city);
		details.setCountries(country);
		details.setFbId(fbId);

		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.save(details);
		transaction.commit();
	}

	private static void addBasicProfile(long fbId, String name, String emailId, String dob, String gender, Session session) {

		UserBasicProfile profile = new UserBasicProfile();
		profile.setDob(dob);
		profile.setEmail(emailId);
		profile.setFbId(fbId);
		profile.setGender(gender);
		profile.setTouristName(name);

		Transaction transaction = session.getTransaction();
		transaction.begin();
		session.save(profile);
		transaction.commit();		
	}

	private static String getUserCurrentCountry() {
		Random r = new Random();
		int Low = 1;
		int High = 250;
		int Result = r.nextInt(High-Low) + Low;

		String countryDetails = locale[Result];		
		Locale obj = new Locale("", countryDetails);

		/*System.out.println("Country Code = " + obj.getCountry()
		+ ", Country Name = " + obj.getDisplayCountry());*/

		String country = obj.getDisplayCountry();

		return country;
	}

	private static List<DemoLocation> getPlacesVisitedDummy() {

		List<DemoLocation> locList = new ArrayList<DemoLocation>();
		Random r = new Random();
		Random rr = new Random();
		int low = 1;
		int high = 250;

		int countLow = 1;
		int countHigh = 5;
		int noOfCountries = rr.nextInt(countHigh-countLow) + countLow;
		locList.clear();
		try {
			for (int i=0;i<noOfCountries;i++) {
				int result = r.nextInt(high-low) + low;
				//logger.info("******** RANDOM NO 1: "+noOfCountries+" NO 2: "+result);
				String countryDetails = locale[result];
				Locale obj = new Locale("", countryDetails);

				String country = obj.getDisplayCountry();
				String code = obj.getCountry();

				//logger.info("*** Country: "+country+" code: "+code);

				URL url = new URL("https://restcountries.eu/rest/v2/alpha/"+code);
				//logger.info("********* URL: "+url.toString());

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				InputStreamReader in = new InputStreamReader(conn.getInputStream());

				StringBuilder jsonResults = new StringBuilder();
				int read;
				char[] buff = new char[1024];

				while ((read = in.read(buff)) != -1) {
					jsonResults.append(buff, 0, read);
				}
				//logger.info("********** OUTPUT: "+jsonResults.toString());
				JSONObject obj1 = new JSONObject(jsonResults.toString());
				String city = obj1.getString("capital");
				//logger.info("*********** CAPITAL : "+city);

				DemoLocation loc = new DemoLocation();
				loc.setCountry(country);
				loc.setCity(country+","+city);
				locList.add(loc);
			} 
		}catch (IOException e) {
			e.printStackTrace();
		}

		return locList;
	}
}
