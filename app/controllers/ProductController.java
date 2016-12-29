package controllers;

import static akka.pattern.Patterns.ask;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;

import actors.ProductActor;
import actors.ProductActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import models.Category;
import models.Product;
import models.Stock;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;
import scala.concurrent.duration.Duration;
import services.AtomicCounter;
import services.Counter;
import views.html.*;


/**
 * Product Controller:
 * -------------------
 *
 * This class is the controller for the products.
 * The class is using the call of Completion<Result> and that is how internally calls are
 * ensured to be asynchronous called in Play Framework. The usual and common way would be
 * returning a Result (Like in the LoginController) but here it is using the Actor and
 * Message passing protocol in a way to show how it is handled.
 *
 * The message passing in this case is ensured using the productActor reference which calls
 *  a productActor type and at the moment of receiving it ensures that the actor is of this type.
 */

public class ProductController extends Controller{

	final ActorRef productActor;

	//HttpExecutionContext: Context needed to be sent in the CompletionStage<Result> call.
	@Inject HttpExecutionContext ec;

	//Atomic counter to handle the number of views per each product.
	@Inject AtomicCounter productViews;

	/**
	 * Constructor:
	 * ------------
	 *
	 * The actor system is used in this class, so the system must be set passing
	 * the Props (necessary to know how the message passing will be handled)
	 *
	 *
	 * productActor: The actor type that handles this call between the ProductActor class and this class.
	 * categoryViews: An Atomic counter to check the number of views per each category. This also uses interaction with the database (Seen below).
	 *
	 * @param system
	 * @param atomicCounter
	 */
	@Inject public ProductController(ActorSystem system, AtomicCounter productViews) {
		this.productActor = system.actorOf(ProductActor.props);
		this.productViews = productViews;
	}

	/**
	 * getProduct:
	 * ----------
	 * This method receives the id of a product, sets in a new Product object the new id and sends
	 * a message with the Actor/Actor Protocol to the ProductActor class. The type of message needs to be of type
	 * SayProduct and receives a product object (the one initialized before).
	 *
	 * When the response arrives, the product arrives in an object and then it is casted to be used and obtain
	 * the description of the product.
	 *
	 * The return is the CompletionStage<Result> return of an asynchronous computation. The context should be
	 * sent to the source before returning the value.
	 *
	 * @param idproduct
	 * @return CompletionStage<Result>
	 */
	public CompletionStage<Result> getProduct(String idproduct) {

		Product product = new Product();
		product.setId(Integer.parseInt(idproduct));

		CompletionStage<Result> source = FutureConverters.toJava(ask(productActor, new ProductActorProtocol.SayProduct(product), 1000))
				.thenApplyAsync(new Function<Object, Result>() {
					@Override
					public Result apply(Object response) {
						Product product = Product.class.cast(response);

						//Set the value to the atomic integer first
						productViews.set(product.getViews());

						//Increment this value since it is a new visit
						productViews.nextCount();

						//update this value in the database for the next try (Use of Ebean ORM Method to update)
						String updStatement = "update product set views = :views where id=:idproduct";
						Update<Product> update = Ebean.createUpdate(Product.class, updStatement);
						update.set("views", productViews.get());
						update.set("idproduct", product.getId());
						int rows = update.execute();

						//update the product object as well
						product.setViews(productViews.get());

						//get the number of items (stock) available for this product
						Stock stock = Ebean.find(Stock.class)
								.select("idproduct, quantity")
								.where()
								.eq("idproduct", product.getId())
								.findList().get(0);

						//Pass all the calculated values to the view in views package: productdesc.scala.html
						return ok(productdesc.render("Product Description", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()),product, product.getViews(), stock.getQuantity()));
					}
				}, ec.current());

		return source;
	}
}
