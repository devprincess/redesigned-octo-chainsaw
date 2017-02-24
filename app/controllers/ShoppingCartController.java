package controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Update;
import models.User;
import models.Product;
import models.ShoppingCart;
import models.ShoppingCartItem;
import models.Stock;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;


/**
 * ShoppingCart Controller:
 * -------------------
 *
 * This class manages the page for accessing the shoppingcart (addition of new products, stock updates)
 * It also includes the ability of deleting an specific item from the shopping cart and
 * it lists the products available in the shopping cart.
 *
 * @author joana
 *
 */
public class ShoppingCartController extends Controller{


	@Security.Authenticated(Secured.class)
	public Result deleteProduct(String idproduct) {

		User c = User.find.where().eq("email", session("email")).findList().get(0);
		List<ShoppingCart> lsp= ShoppingCart.find.where().eq("idcustomer", c.getId()).findList();
		ShoppingCart sp = new ShoppingCart();
		sp = lsp.get(0);

		List<ShoppingCartItem> moreThanOneItem = ShoppingCartItem.find.where().eq("shopping_cart_id", sp.getId()).eq("idproduct", idproduct).gt("quantity", 1).findList();

		String statement = "DELETE from shoppingcartitem WHERE shopping_cart_id =:shopping_cart_id and idproduct =:idproduct";
		if (moreThanOneItem.isEmpty()){
			SqlUpdate deleteProductUpd = Ebean.createSqlUpdate(statement);
			deleteProductUpd.setParameter("shopping_cart_id", sp.getId());
			deleteProductUpd.setParameter("idproduct", Integer.parseInt(idproduct));
			int row1 = deleteProductUpd.execute();
		}
		else{
			statement = "update shoppingcartitem set quantity = quantity - 1 where shopping_cart_id =:shopping_cart_id and idproduct=:idproduct";
			Update<ShoppingCartItem> updateProductUpd = Ebean.createUpdate(ShoppingCartItem.class, statement);
			updateProductUpd.set("shopping_cart_id", sp.getId());
			updateProductUpd.set("idproduct", Integer.parseInt(idproduct));
			int rows = updateProductUpd.execute();
		}

		String stockStatement = "update stock set quantity = quantity + 1 where idproduct=:idproduct";
		Update<Stock> updateStockUpd = Ebean.createUpdate(Stock.class, stockStatement);
		updateStockUpd.set("idproduct", Integer.parseInt(idproduct));
		int rows = updateStockUpd.execute();

		return redirect(routes.ShoppingCartController.getProducts());
	}


	@Security.Authenticated(Secured.class)
	public Result addProduct(String idproduct) {

		User c = User.find.where().eq("email", session("email")).findList().get(0);
		List<ShoppingCart> lsp= ShoppingCart.find.where().eq("idcustomer", c.getId()).findList();
		ShoppingCart sp = new ShoppingCart();

		List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
		ShoppingCartItem newItem = new ShoppingCartItem();
		newItem.setIdproduct(Integer.parseInt(idproduct));
		newItem.setQuantity(1);
		items.add(newItem);
		sp.setIdcustomer(c.getId());

		if(lsp.isEmpty()){
			sp.setItems(items);
		}
		else{
			sp = lsp.get(0);

			List<ShoppingCartItem> existingItems = sp.getItems();

			if(sp.containsSpecificProduct(Integer.parseInt(idproduct))){

				String updStatement = "update shoppingcartitem set quantity = quantity + 1 where shopping_cart_id=:shopping_cart_id and idproduct=:idproduct";
				Update<ShoppingCartItem> update = Ebean.createUpdate(ShoppingCartItem.class, updStatement);
				update.set("shopping_cart_id", sp.getId());
				update.set("idproduct", Integer.parseInt(idproduct));
				int rows = update.execute();
			}
			else{
				List<ShoppingCartItem> newListItems = Stream.concat(sp.getItems().stream(), items.stream()).collect(Collectors.toList());
				sp.setItems(newListItems);
			}

		}

		Ebean.save(sp);

		List<Stock> stockList = Stock.find.where().eq("idproduct", Integer.parseInt(idproduct)).gt("quantity", 0).findList();
		if (!stockList.isEmpty()){
			String updStatement = "update stock set quantity = quantity - 1 where idproduct=:idproduct";
			Update<Stock> update = Ebean.createUpdate(Stock.class, updStatement);
			update.set("idproduct", Integer.parseInt(idproduct));
			int rows = update.execute();
		}

		return redirect(routes.ShoppingCartController.getProducts());
	}


	@Security.Authenticated(Secured.class)
	public Result getProducts(){

		User c = User.find.where().eq("email", session("email")).findList().get(0);

		List<ShoppingCart> lsp= ShoppingCart.find.where().eq("idcustomer", c.getId()).findList();
		ShoppingCart sp = new ShoppingCart();

		if(!lsp.isEmpty()) {
			sp = lsp.get(0);
		}

		List<ShoppingCartItem> lp = sp.getItems();

		ListIterator<ShoppingCartItem> itr = lp.listIterator();
		List<Product> products = new ArrayList<Product>();

		while(itr.hasNext()){
			ShoppingCartItem item = itr.next();
			Product p = Product.find.byId(item.getIdproduct());
			products.add(p);
		}

		return ok(shoppingcart.render("ShoppingCart", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), lp));
	}

}
