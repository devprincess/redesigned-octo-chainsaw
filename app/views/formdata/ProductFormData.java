package views.formdata;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Product;

public class ProductFormData {

	@Required
	private String name;

	@Required
	private String description;

	@Required
	private Double price;

	@Required
	private Integer idcategory;

	private Integer views;

	@Required
	private String url;



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
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



	public ProductFormData(){

	}

	public ProductFormData(Product p){
		this.name = p.getName();
		this.url = p.getUrl();
		this.views = p.getViews();
		this.description = p.getDescription();
		this.price = p.getPrice();
		this.idcategory = p.getIdcategory();
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
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


}