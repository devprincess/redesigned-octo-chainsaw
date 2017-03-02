package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

/**
 * Entity bean for Category to access to the table category.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "category")
public class Category extends Model {

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="name")
	@Constraints.Required
	private String name;

	@Column(name="views")
	private Integer views;

	@Column(name="url")
	private String url;

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static Finder<Integer,Category> find = new Finder<Integer,Category>(Category.class);

}