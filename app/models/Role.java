package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

/**
 * Entity bean for Role table.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "role")
public class Role extends Model {

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="name")
	@Constraints.Required
	private String name;

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Finder<Integer,Role> find = new Finder<Integer,Role>(Role.class);

}
