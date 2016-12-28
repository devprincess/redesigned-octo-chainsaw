package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table( name= "customer")
public class Customer extends Model {

	@Id
	@Constraints.Max(10)
	public Integer id;

	@Column(name="name")
	@Constraints.Required
	public String name;

	@Column(name="mobile")
	@Constraints.MaxLength(10)
	public String mobile;

	@Column(name="email")
	@Constraints.Email
	public String email;

	@Column(name="pwd")
	@Constraints.Required
	public String pwd;

	@Column(name="gender")
	@Constraints.MaxLength(1)
	public String gender;

	@Column(name="birthdate")
	@Formats.DateTime(pattern="yyyy/MM/dd")
	public Date birthdate = new Date();

	@Constraints.Required
	public Integer idpaymethod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getIdpaymethod() {
		return idpaymethod;
	}

	public void setIdpaymethod(Integer idpaymethod) {
		this.idpaymethod = idpaymethod;
	}

	public static Finder<Integer,Customer> find = new Finder<Integer,Customer>(Customer.class);

}
