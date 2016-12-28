package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import play.data.validation.Constraints;

@Entity
@Table( name= "stock")
public class Stock extends Model {

	@Id
	@Constraints.Max(10)
	public Integer id;

	@Column(name="idproduct")
	@Constraints.Required
	public Integer idproduct;

	@Column(name="quantity")
	public Integer quantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdproduct() {
		return idproduct;
	}

	public void setIdproduct(Integer idproduct) {
		this.idproduct = idproduct;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public static Finder<Integer,Stock> find = new Finder<Integer,Stock>(Stock.class);

}