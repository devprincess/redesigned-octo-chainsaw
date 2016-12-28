package controllers;

import static akka.pattern.Patterns.ask;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;

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

public class CategoryController extends Controller{

	final ActorRef categoryActor;

	@Inject HttpExecutionContext ec;

	@Inject public CategoryController(ActorSystem system) {
		categoryActor = system.actorOf(CategoryActor.props);
	}

	public CompletionStage<Result> getProductsByCategory(String idcategory) {

		Category category = new Category();
		category.setId(Integer.parseInt(idcategory));

		CompletionStage<Result> source = FutureConverters.toJava(ask(categoryActor, new CategoryActorProtocol.SayCategory(category), 1000))
				.thenApplyAsync(new Function<Object, Result>() {
					@Override
					public Result apply(Object response) {
						Category category = Category.class.cast(response);
						//need to do a join query to send the products as well
						List<Product> productList = Product.find.where().eq("idcategory", category.id).findList();
						return ok(categorydesc.render("Category Description", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()),category, productList));
					}
				}, ec.current());

		return source;
	}
}

