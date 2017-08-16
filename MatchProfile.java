package org.jersey.demo.messenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jersey.demo.messenger.pojo.CommonCountriesCompare;
import org.jersey.demo.messenger.pojo.FinalDestination;
import org.jersey.demo.messenger.pojo.PlacesVisited;
import org.jersey.demo.messenger.pojo.UserPersonalDetails;
import org.json.JSONArray;
import org.json.JSONObject;

public class MatchProfile {

	private static TourLogger logger = TourLogger.getInstance();

	public static JSONObject getUserData(long fbId) {
		List<PlacesVisited> otherUserData = new ArrayList<PlacesVisited>();
		List<CommonCountriesCompare> commonList = new ArrayList<CommonCountriesCompare>();
		List<CommonCountriesCompare> finalCompareList = new ArrayList<CommonCountriesCompare>();
		List<FinalDestination> finalDestination = new ArrayList<FinalDestination>();
		PlacesVisited place = new PlacesVisited();
		JSONObject itineraryObj = new JSONObject();

		String sqlQuery = "from PlacesVisited";
		Session session = CommonMethods.getSession();
		Query query = session.createQuery(sqlQuery);
		otherUserData = query.list();

		for (int i=0;i<otherUserData.size();i++) {
			place = otherUserData.get(i);
			long facebookId = place.getFbId();
			if (facebookId == fbId) {
				otherUserData.remove(place);
				break;
			}
		}

		commonList = getCommonCountries(otherUserData,place);
		UserPersonalDetails personalDetails = CommonMethods.getUserPersonalDetails(fbId);
		finalCompareList = getFinalCompareList(commonList,personalDetails);
		finalDestination = FinalResult.getFinalDestinations(finalCompareList, personalDetails);
		//logger.info("************** FINAL DESTINATION LIST: "+finalDestination.toString());
		itineraryObj = Itinerary.getIteneraryForCity(finalDestination);
		logger.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		logger.info("*******JSON RESULT*************  ----> "+itineraryObj.toString());

		return itineraryObj;
	}

	private static List<CommonCountriesCompare> getFinalCompareList(List<CommonCountriesCompare> commonList,UserPersonalDetails personalDetails) {

		List<UserPersonalDetails> list = new ArrayList<UserPersonalDetails>();
		List<CommonCountriesCompare> finalList = new ArrayList<CommonCountriesCompare>();
		UserPersonalDetails details = new UserPersonalDetails();
		CommonCountriesCompare ccc = new CommonCountriesCompare();
		String country = personalDetails.getCountries();
		Session session = CommonMethods.getSession();
		String queryStr = "from UserPersonalDetails where countries = :country";
		Query query = session.createQuery(queryStr);
		query.setParameter("country", country);
		list = query.list();
		logger.info("****** USER EXTRACTED FROM DB: "+list.toString());
		for (int i=0;i<list.size();i++) {
			details = list.get(i);
			long personalFbId = details.getFbId();

			for (int j=0;j<commonList.size();j++) {
				ccc = commonList.get(j);
				long cccFbId = ccc.getFbId();

				if (cccFbId == personalFbId) {
					finalList.add(ccc);
					logger.info("******* SAME FBID: FINAL LIST "+finalList.toString());
					break;
				} else {
					logger.info("***** MATCH PROFILE (getFinalCompareList) : DIFFERENT FBID********");
				}
			}
		}
		
		if (finalList.size() == 0) {
			finalList.addAll(commonList);
		}
		return finalList;
	}

	private static List<CommonCountriesCompare> getCommonCountries(List<PlacesVisited> otherUserData, PlacesVisited place) {


		List<String> common = new ArrayList<String>();
		List<String> uncommon = new ArrayList<String>();

		String countries = place.getCountry();		
		String cities = place.getCities();
		JSONObject placeObj = new JSONObject(countries);
		JSONArray placeArr = (JSONArray) placeObj.get("country");
		long fbId=0;
		List<CommonCountriesCompare> commonCountryList = new ArrayList<CommonCountriesCompare>();

		//logger.info("USER PLACES COUNTRY: "+placeArr.toString());

		for (int j=0;j<otherUserData.size();j++) {
			PlacesVisited visit = otherUserData.get(j);

			//Get Common Countries
			String countryStr = visit.getCountry();
			String city = visit.getCities();
			fbId = visit.getFbId();
			JSONObject countryObj = new JSONObject(countryStr);
			JSONArray countryArr = countryObj.getJSONArray("country");

			//logger.info("OTHER USER COUNTRY: "+countryArr.toString());

			for (int i=0;i<countryArr.length();i++) {
				String country = countryArr.getString(i);
				//logger.info("OTHER USER COUNTRY ITERATE: "+country);

				if (placeArr.toString().contains(country)) {
					common.add(country);
				} else {
					uncommon.add(country);
				}
			}


			int commonListSize = common.size();
			int uncommonListSize = uncommon.size();
			int placeArrSize = placeArr.length();

			float compareVal = (60/100)*placeArrSize;

			logger.info("COMMON COUNTRIES: "+common.toString());
			logger.info("UNCOMMON COUNTRIES: "+uncommon.toString());
			logger.info("COMMONSIZE: "+commonListSize+" USER PLACE SIZE: "+placeArrSize);
			logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			if (commonListSize > compareVal) {
				CommonCountriesCompare commonCountry = new CommonCountriesCompare();
				commonCountry.setFbId(fbId);
				commonCountry.setCities(city);
				commonCountry.setCommonCountry(common.toString());
				commonCountry.setUncommonCountry(uncommon.toString());
				commonCountry.setCommonSize(commonListSize);
				commonCountry.setUserCountrySize(placeArrSize);
				commonCountry.setUncommonCountrySize(uncommonListSize);
				commonCountryList.add(commonCountry);	
				//logger.info("*******************************: "+commonCountryList.toString());
			}

			common.clear();
			uncommon.clear();
		}
		//logger.info("FINAL LIST: "+commonCountryList.toString());
		Collections.sort(commonCountryList, new Comparator<CommonCountriesCompare>() {

			@Override
			public int compare(CommonCountriesCompare o1, CommonCountriesCompare o2) {
				Long para1 = (long) o1.getCommonSize();
				Long para2 = (long) o2.getCommonSize();
				return para2.compareTo(para1);
			}
		});
		//logger.info("ARRAYLIST.STRING TO ARRAYLIST: "+commonCountryList.toString());
		for (int l=0;l<commonCountryList.size();l++) {
			CommonCountriesCompare ccc = commonCountryList.get(l);
			//logger.info("++++++++++++++++ : "+ccc.toString());
		}
		return commonCountryList;
	}

}
