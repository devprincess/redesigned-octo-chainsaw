package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

/**
 * Entity bean for ShippingMethod to access to the table shippingmethod.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "shippingmethod")
public class ShippingMethod extends Model {

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="name")
	@Constraints.Required
	private String name;

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

	public static Finder<Integer,ShippingMethod> find = new Finder<>(ShippingMethod.class);
}
