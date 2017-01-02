package controllers;

import static akka.pattern.Patterns.ask;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;

import actors.CategoryActor;
import actors.CategoryActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import models.Category;
import models.Product;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import scala.compat.java8.FutureConverters;


/**
 * Category Controller:
 * -------------------
 *
 * This class is the controller for the categories of the products.
 * The class shows the example of using the return of CompletionStage<Result> in the getProductsByCategory method
 * and that is how internally calls are handled in Play Framework.
 * The usual and common way would be returning a Result (Like in the LoginController class controllers/LoginController.java)
 *
 * The message passing in this case is ensured using the categoryActor reference which calls
 * a CategoryActor type and at the moment of receiving it ensures that the actor is of this type.
 * (See the classes related to actor message passing into app/actors/CategoryActor.java and app/actors/CategoryActorProtocol.java
 *
 * @author joana
 *
 */
public class CategoryController extends Controller{

	final ActorRef categoryActor;

	@Inject HttpExecutionContext ec;

	AtomicInteger categoryViews;

	/**
	 * Constructor:
	 * ------------
	 *
	 * The actor system is used in this class, so the system must be set passing
	 * the Props (necessary to know how the message passing will be handled)
	 *
	 *
	 * categoryActor: The actor type that handles this call between the CategoryActor class and this class.
	 * categoryViews: An Atomic counter to check the number of views per each category. This also uses interaction with the database (Seen below).
	 *
	 * @param system
	 * @param categoryViews
	 */
	@Inject
	public CategoryController(ActorSystem system, AtomicInteger categoryViews) {
		this.categoryActor = system.actorOf(CategoryActor.props);
		this.categoryViews= categoryViews;

	}

	/**
	 * getProductsByCategory:
	 * ---------------------
	 * This method receives the id of a category, sets in a new Category object the new id and sends
	 * a message using the Actor/Actor Protocol to the CategoryActor class. The type of message needs to be of type
	 * SayCategory and receives a category object (the one initialized before).
	 *
	 * When the response arrives, the category arrives in an object and then is casted to be used and obtain
	 * the products inside this category. Queries to the database are done with the Ebean ORM methods.
	 *
	 * The return is the CompletionStage<Result> return of an asynchronous computation. The context should be
	 * sent to the source before returning the value.
	 *
	 * @param idcategory
	 * @return CompletionStage<Result>
	 */
	public CompletionStage<Result> getProductsByCategory(String idcategory) {

		Category category = new Category();
		category.setId(Integer.parseInt(idcategory));

		CompletionStage<Result> source = FutureConverters.toJava(ask(categoryActor, new CategoryActorProtocol.SayCategory(category), 1000))
				.thenApplyAsync(new Function<Object, Result>() {
					@Override
					public Result apply(Object response) {

						//in the case the response is of a type Category, I cast it to obtain the Category inside the message.
						Category category = Category.class.cast(response);

						//Use of Ebean ORM to find all the products related to this category.
						//Also notice the use of the Java Framework Collection for List.
						List<Product> productList = Product.find.where().eq("idcategory", category.getId()).findList();

						//I get the views that this category has from the database, and set it to the atomic integer value of categoryViews
						categoryViews.set(category.getViews());

						//after setting the initial value of the views for this category, and as this controller is being accessed, I increase the count
						categoryViews.incrementAndGet();

						//Then I update the value of this new counter for the category to the database using the Ebean ORM functions
						String updStatement = "update category set views = :views where id=:idcategory";
						Update<Category> update = Ebean.createUpdate(Category.class, updStatement);
						update.set("views", categoryViews.get());
						update.set("idcategory", category.getId());
						int rows = update.execute();

						//update the category object as well
						category.setViews(categoryViews.get());

						//Finally I return the values to the view
						return ok(categorydesc.render("Category Description", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()),category, productList, category.getViews()));
					}
				}, ec.current());

		return source;
	}
}

