package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table( name= "shippingmethod")
public class ShippingMethod extends Model {

	@Id
	@Constraints.Max(10)
	public Integer id;

	@Column(name="name")
	@Constraints.Required
	public String name;

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
