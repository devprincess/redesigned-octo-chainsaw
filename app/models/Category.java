package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table( name= "category")
public class Category extends Model {

	@Id
	@Constraints.Max(10)
	public Integer id;

	@Column(name="name")
	@Constraints.Required
	public String name;

	public Integer views;

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

	public static Finder<Integer,Category> find = new Finder<Integer,Category>(Category.class);

}