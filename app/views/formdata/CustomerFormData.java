package views.formdata;

import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Backing class for updating the profile of a customer.
 **/
public class CustomerFormData {

	private String name;

	private String mobile;

	private String email;

	private String pwd;

	private String gender;

	private Date birthdate;

	private String address;

	public CustomerFormData(){

	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPwd() {
		return pwd;
	}



	public void setPwd(String pwd) {
		this.pwd = pwd;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Date getBirthdate() {
		return birthdate;
	}



	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}

}
