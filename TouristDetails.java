package org.jersey.demo.messenger.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;

@Entity
@Table(name="TouristDetails")
public class TouristDetails {
	
	@Id
	@Column(name="touristId")
	private int touristId;
	
	@Column(name="touristName")
	private String touristName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="locationCity")
	private String locationCity;
	
	@Column(name="locationCountry")
	private String locationCountry;
	
	public int getTouristId() {
		return touristId;
	}
	public void setTouristId(int touristId) {
		this.touristId = touristId;
	}
	public String getTouristName() {
		return touristName;
	}
	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public String getLocationCountry() {
		return locationCountry;
	}
	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}
	@Override
	public String toString() {
		return "touristId=" + touristId + ", touristName=" + touristName + ", email=" + email
				+ ", locationCity=" + locationCity + ", locationCountry=" + locationCountry;
	}
	
	

}
