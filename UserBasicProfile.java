package org.jersey.demo.messenger.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserBasicProfile")
public class UserBasicProfile {
	
	@Id
	@Column(name="touristId")
	private int touristId;
	
	@Column(name="fbId")
	private long fbId;
	
	@Column(name="touristName")
	private String touristName;
	
	@Column(name="email")
	private String email;
	
	@Column(name="dob")
	private String dob;
	
	@Column(name="gender")
	private String gender;
	
	public int getTouristId() {
		return touristId;
	}
	public void setTouristId(int touristId) {
		this.touristId = touristId;
	}
	public long getFbId() {
		return fbId;
	}
	public void setFbId(long fbId) {
		this.fbId = fbId;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTouristName() {
		return touristName;
	}
	public void setTouristName(String touristName) {
		this.touristName = touristName;
	}
	@Override
	public String toString() {
		return "touristId=" + touristId + ", fbId=" + fbId + ", touristName=" + touristName
				+ ", email=" + email + ", dob=" + dob + ", gender=" + gender;
	}
	
	
	
	
	
	

}
