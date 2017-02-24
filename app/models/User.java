package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

/**
 * Entity bean for Customer to access to the table customer.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "user")
public class User extends Model {

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="name")
	@Constraints.Required
	private String name;

	@Column(name="mobile")
	@Constraints.MaxLength(10)
	private String mobile;

	@Column(name="email")
	@Constraints.Email
	private String email;

	@Column(name="pwd")
	@Constraints.Required
	private String pwd;

	@Column(name="gender")
	@Constraints.MaxLength(1)
	private String gender;

	@Column(name="birthdate")
	@Formats.DateTime(pattern="yyyy/MM/dd")
	private Date birthdate = new Date();

	@Column(name="address")
	private String address;

	@Constraints.Required
	private Integer idpaymethod;

	@Constraints.Required
	private Integer idrole;

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

	public Integer getIdrole() {
		return idrole;
	}

	public void setIdrole(Integer idrole) {
		this.idrole = idrole;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static Finder<Integer,User> find = new Finder<Integer,User>(User.class);

}
