package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.DateTime;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import play.data.format.Formats;
import play.data.validation.Constraints;

/**
 * Entity bean for Order to access to the table o_order.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "o_order")
public class Order extends Model{

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="idcustomer")
	@Constraints.Max(10)
	private Integer idcustomer;

	@Column(name="idorderstatus")
	private Integer idorderstatus;

	@Column(name="date")
	@Formats.DateTime(pattern="yyyy/MM/dd")
	private DateTime date;

	@Column(name="idshippingmethod")
	private Integer idshippingmethod;

	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderDetail> details;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdcustomer() {
		return idcustomer;
	}

	public void setIdcustomer(Integer idcustomer) {
		this.idcustomer = idcustomer;
	}

	public Integer getIdorderstatus() {
		return idorderstatus;
	}

	public void setIdorderstatus(Integer idorderstatus) {
		this.idorderstatus = idorderstatus;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public List<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<OrderDetail> details) {
		this.details = details;
	}

	public static Finder<Integer,Order> find = new Finder<>(Order.class);

}
