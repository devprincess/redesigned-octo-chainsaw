package controllers;

import static akka.pattern.Patterns.ask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import models.Category;
import models.Customer;
import models.Product;
import models.ShoppingCart;
import models.ShoppingCartItem;
import models.Stock;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.compat.java8.FutureConverters;
import scala.concurrent.duration.Duration;
import views.html.*;

public class ShoppingCartController extends Controller{


	public ShoppingCartController(){

	}

	@Security.Authenticated(Secured.class)
	public Result addProduct(String idproduct) {

		Customer c = Customer.find.where().eq("email", session("email")).findList().get(0);
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

				for (int i=0; i<existingItems.size(); i++){
					ShoppingCartItem s = ShoppingCartItem.find.byId(existingItems.get(i).getId());
					String updStatement = "update shoppingcartitem set quantity = :quantity where shopping_cart_id=:shopping_cart_id and idproduct=:idproduct";
					Update<ShoppingCartItem> update = Ebean.createUpdate(ShoppingCartItem.class, updStatement);
					update.set("shopping_cart_id", s.getId());
					update.set("idproduct", Integer.parseInt(idproduct));
					update.set("quantity", s.getQuantity()+1);
					int rows = update.execute();

				}
			}
			else{
				List<ShoppingCartItem> newListItems = Stream.concat(sp.getItems().stream(), items.stream()).collect(Collectors.toList());
				sp.setItems(newListItems);
			}

		}

		Ebean.save(sp);
		return redirect(routes.ShoppingCartController.getProducts());
	}


	@Security.Authenticated(Secured.class)
	public Result getProducts(){

		Customer c = Customer.find.where().eq("email", session("email")).findList().get(0);

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
