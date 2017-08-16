package org.jersey.demo.messenger.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Entity;

@Entity
@Table(name="Cities")
public class Cities {

	@Id
	@Column(name="cityId")
	int cityId;

	@Column(name="touristId")
	private int touristId;

	@Column(name="city")
	String city;

	public int getTouristId() {
		return touristId;
	}
	public void setTouristId(int touristId) {
		this.touristId = touristId;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "touristId=" + touristId + ", cityId=" + cityId + ", City=" + city;
	}



}
