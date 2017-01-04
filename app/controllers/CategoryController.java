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

	private final ActorRef categoryActor;

	@Inject HttpExecutionContext ec;

	private final AtomicInteger categoryViews;

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

						Category category = Category.class.cast(response);

						List<Product> productList = Product.find.where().eq("idcategory", category.getId()).findList();

						categoryViews.set(category.getViews());
						categoryViews.incrementAndGet();

						String updStatement = "update category set views = :views where id=:idcategory";
						Update<Category> update = Ebean.createUpdate(Category.class, updStatement);
						update.set("views", categoryViews.get());
						update.set("idcategory", category.getId());
						int rows = update.execute();

						category.setViews(categoryViews.get());
						return ok(categorydesc.render("Category Description", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()),category, productList, category.getViews()));
					}
				}, ec.current());

		return source;
	}
}

