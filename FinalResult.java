package org.jersey.demo.messenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jersey.demo.messenger.pojo.CommonCountriesCompare;
import org.jersey.demo.messenger.pojo.FinalDestination;
import org.jersey.demo.messenger.pojo.UserPersonalDetails;
import org.json.JSONArray;
import org.json.JSONObject;

import jersey.repackaged.com.google.common.base.Objects;

public class FinalResult {

	private static TourLogger logger = TourLogger.getInstance();

	public static List<FinalDestination> getFinalDestinations(List<CommonCountriesCompare> finalCompareList,UserPersonalDetails personalDetails) {

		CommonCountriesCompare ccc = new CommonCountriesCompare();
		List<String> countryList = new ArrayList<String>();
		List<FinalDestination> fdList = new ArrayList<FinalDestination>();

		int count = 3;
		for (int i=0;i<finalCompareList.size();i++) {
			ccc = finalCompareList.get(i);
			int uncommonContries = ccc.getUncommonCountrySize();

			if (uncommonContries > 0) {
				String uncommonStr = ccc.getUncommonCountry();

				//logger.info("************ UNCOMMON COUNTRIES TOSTRING: "+uncommonStr);
				List<String> uncommonCountriesList = Arrays.asList(ccc.getUncommonCountry().split(","));
				//logger.info("************ UNCOMMON COUNTRIES ARRAYLIST: "+uncommonCountriesList.toString());
				for (int j=0;j<uncommonCountriesList.size();j++) {
					String str = uncommonCountriesList.get(j);
					String uncommonCountry = str.replaceAll("[\\[\\]]", "");
					if (count > 0) {
						List<String> cityList = new ArrayList<String>();
						cityList = getCity(uncommonCountry,ccc);
						FinalDestination fd = new FinalDestination();
						fd.setCountry(uncommonCountry);
						fd.setCity(cityList.toString());
						fd.setId(i);
						fdList.add(fd);
						logger.info("@@@@@@@@@ FINAL CITY LIST FOR COUNTRY "+uncommonCountry+" IS "+cityList.toString());
						count--;
					} else {
						break;
					}
				}
			}
		}
		return fdList;
	}

	private static List<String> getCity(String uncommonCountry, CommonCountriesCompare ccc) {

		String cities = ccc.getCities();
		List<String> finalCity = new ArrayList<String>();
		int count = 1;

		JSONObject cityObj = new JSONObject(cities);
		JSONArray cityArr = cityObj.getJSONArray("City");
		//	logger.info("@@@@@@@@@@@@@@@@@@@@@ FINALRESULT (getCity) CITY "+cityArr.toString());

		for (int i=0;i<cityArr.length();i++) {
			String city = cityArr.getString(i);
			//logger.info("@@@@@@@@@@@@@@@@@@@@@ FINALRESULT (getCity) CITY "+city);
			String[] token = city.split(",");
			String country = token[0];
			logger.info("Country token:"+country+" UnCommon Country: "+uncommonCountry);
			if (Objects.equal(country, uncommonCountry)) {
				if (count > 0) {
					String citySplit = token[1];
					//logger.info("@@@@@@@@@@@@@@@@@@@@@ FINALRESULT (getCity) CITYSPLIT "+citySplit);
					finalCity.add(citySplit);
					count--;
				}
			} else {
				logger.info("@@@@@@@@@ NO SAME COUNTRY @@@@@@@@@@@");
			}
			/*logger.info("############### Country: "+country);
			logger.info("############### City: "+citySplit);*/
		}
		//logger.info("@@@@@@@ FINAL CITY LIST TO SEND "+finalCity.toString());
		return finalCity;
	}


}
