package controllers;

import static akka.pattern.Patterns.ask;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import javax.inject.Inject;

import actors.ProductActor;
import actors.ProductActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import models.Product;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import scala.compat.java8.FutureConverters;
import scala.concurrent.duration.Duration;
import views.html.*;

public class ProductController extends Controller{

	final ActorRef helloActor;

	@Inject HttpExecutionContext ec;

	@Inject public ProductController(ActorSystem system) {
		helloActor = system.actorOf(ProductActor.props);
	}

	public CompletionStage<Result> getProduct(String idproduct) {

		Product product = new Product();
		product.setId(Integer.parseInt(idproduct));

		CompletionStage<Result> source = FutureConverters.toJava(ask(helloActor, new ProductActorProtocol.SayProduct(product), 1000))
				.thenApplyAsync(new Function<Object, Result>() {
					@Override
					public Result apply(Object response) {
						Product product = Product.class.cast(response);
						//return ok("The name of the product is:"+product.getPrice());
						return ok(productdesc.render("Product Description", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()),product));
					}
				}, ec.current());

		return source;
	}
}
