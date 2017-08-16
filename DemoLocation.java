package org.jersey.demo.messenger.pojo;

public class DemoLocation {
	
	String country;
	String city;
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "country=" + country + ", city=" + city;
	}
}
