package views.formdata;

import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Product;

public class ProductInsertFormData {

	@Required
	private String name;

	@Required
	private String description;

	@Required
	private Double price;

	private String url;

	public ProductInsertFormData(){

	}

	public ProductInsertFormData(Product p){
		this.name = p.getName();
		this.description = p.getDescription();
		this.price = p.getPrice();
		this.url = p.getUrl();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getPrice(){
		return price;
	}

	public void setPrice(Double price){
		this.price = price;
	}

}