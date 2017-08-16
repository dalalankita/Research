package org.jersey.demo.messenger.pojo;

public class FinalDestination {
	
	int id;
	String country;
	String city;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
		return "id=" + id + ", country=" + country + ", city=" + city;
	}
	

}
