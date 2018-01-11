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

	private Integer views;

	@Required
	private String url;

	public ProductFormData(){

	}

	public ProductFormData(Product p){
		this.name = p.getName();
		this.url = p.getUrl();
		this.views = p.getViews();
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