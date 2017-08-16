package org.jersey.demo.messenger.pojo;



public class CommonCountriesCompare {
	
	int id;
	long fbId;
	String commonCountry;
	String uncommonCountry;
	int commonSize;
	int userCountrySize;
	int uncommonCountrySize;
	String cities;
	
	
	
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	public int getUncommonCountrySize() {
		return uncommonCountrySize;
	}
	public void setUncommonCountrySize(int uncommonCountrySize) {
		this.uncommonCountrySize = uncommonCountrySize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getFbId() {
		return fbId;
	}
	public void setFbId(long fbId) {
		this.fbId = fbId;
	}
	public String getCommonCountry() {
		return commonCountry;
	}
	public void setCommonCountry(String commonCountry) {
		this.commonCountry = commonCountry;
	}
	public String getUncommonCountry() {
		return uncommonCountry;
	}
	public void setUncommonCountry(String uncommonCountry) {
		this.uncommonCountry = uncommonCountry;
	}
	public int getCommonSize() {
		return commonSize;
	}
	public void setCommonSize(int commonSize) {
		this.commonSize = commonSize;
	}
	public int getUserCountrySize() {
		return userCountrySize;
	}
	public void setUserCountrySize(int userCountrySize) {
		this.userCountrySize = userCountrySize;
	}
	@Override
	public String toString() {
		return "id=" + id + ", fbId=" + fbId + ", commonCountry=" + commonCountry
				+ ", uncommonCountry=" + uncommonCountry + ", commonSize=" + commonSize + ", userCountrySize="
				+ userCountrySize + ", uncommonCountrySize=" + uncommonCountrySize + ", cities=" + cities;
	}
	
	
}
