package org.jersey.demo.messenger.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;

@Entity
@Table(name="Cities")
public class Countries {
	
	@Id
	@Column(name="countryId")
	private int countryId;
	
	@Column(name="touristId")
	private int touristId;
	
	@Column(name="country")
	private String country;
	
	public int getTouristId() {
		return touristId;
	}
	public void setTouristId(int touristId) {
		this.touristId = touristId;
	}
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "touristId=" + touristId + ", countryId=" + countryId + ", country=" + country;
	}
}
