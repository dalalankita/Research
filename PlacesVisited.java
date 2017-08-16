package org.jersey.demo.messenger.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

@Entity
@Table(name="PlacesVisited")
public class PlacesVisited {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="fbId")
	private long fbId;
	
	@Column(name="country")
	private String country;
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	@Override
	public String toString() {
		return "id=" + id + ", fbId=" + fbId + ", country=" + country + ", cities=" + cities;
	}
	
	
	
	
	
	

}
