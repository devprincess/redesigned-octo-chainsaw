package views.formdata;

import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Category;

public class CategoryFormData {

	private String name;

	private Integer nproducts;

	private Integer views;

	private String url;

	public CategoryFormData(){

	}

	public CategoryFormData(Category c){
		this.name = c.getName();
		this.nproducts = c.getNproducts();
		this.url = c.getUrl();
		this.views = c.getViews();
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

	public void setNproducts(Integer nproducts){
		this.nproducts = nproducts;
	}

	public Integer getNproducts(){
		return nproducts;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}