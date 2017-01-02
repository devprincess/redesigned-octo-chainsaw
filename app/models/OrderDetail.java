package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import play.data.validation.Constraints;

/**
 * Entity bean for OrderDetail to access to the table o_orderdetail.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "o_orderdetail")
public class OrderDetail extends Model{

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="idorder")
	@Constraints.Max(10)
	private Integer idorder;

	@Column(name="idproduct")
	@Constraints.Max(10)
	private Integer idproduct;

	@Column(name="quantity")
	private Integer quantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdorder() {
		return idorder;
	}

	public void setIdorder(Integer idorder) {
		this.idorder = idorder;
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

	public static Finder<Integer,OrderDetail> find = new Finder<>(OrderDetail.class);

}
