package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table( name= "product")
public class Product extends Model {

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="name")
	private String name;

	@Column(name="description", length=800)
	private String description;

	@Column(name="price")
	private Double price;

	@Column(name="idcategory")
	@Constraints.Required
	private Integer idcategory;

	@Column(name="views")
	private Integer views;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getIdcategory() {
		return idcategory;
	}

	public void setIdcategory(Integer idcategory) {
		this.idcategory = idcategory;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public static Finder<Integer,Product> find = new Finder<>(Product.class);
}
