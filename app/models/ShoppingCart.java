package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import play.data.validation.Constraints;

/**
 * Entity bean for ShoppingCart to access to the table shoppingcart.
 * This class generates the DDL statements for running the evolutions for Ebean ORM. (See conf/evolutions/1.sql)
 * @author joana
 *
 */
@Entity
@Table( name= "shoppingcart")
public class ShoppingCart extends Model{

	@Id
	@Constraints.Max(10)
	private Integer id;

	@Column(name="idcustomer")
	@Constraints.Max(10)
	private Integer idcustomer;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ShoppingCartItem> items;

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

	public List<ShoppingCartItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingCartItem> items) {
		this.items = items;
	}


	public boolean containsSpecificProduct(Integer idproduct){

		boolean b = false;

		for (int i=0; i<this.getItems().size(); i++){
			if (this.getItems().get(i).getIdproduct() == idproduct){
				b= true;
				break;
			} else {
				b=false;
			}
		}
		return b;
	}

	public static Finder<Integer,ShoppingCart> find = new Finder<>(ShoppingCart.class);
}
