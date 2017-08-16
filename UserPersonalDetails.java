package org.jersey.demo.messenger.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserPersonalDetails")
public class UserPersonalDetails {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="fbId")
	private long fbId;
	
	@Column(name="countries")
	private String countries;
	
	@Column(name="cities")
	private String cities;
	
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
	
	public String getCountries() {
		return countries;
	}
	public void setCountries(String countries) {
		this.countries = countries;
	}
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	@Override
	public String toString() {
		return "id=" + id + ", fbId=" + fbId + ", countries=" + countries + ", cities=" + cities;
	}	
}
