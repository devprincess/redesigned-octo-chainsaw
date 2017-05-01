package controllers;

import play.mvc.Controller;

import javax.inject.Inject;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import play.mvc.Security;
import static akka.pattern.Patterns.ask;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import models.Category;
import models.Product;
import models.User;
import play.data.Form;
import play.mvc.Http.Context;
import scala.compat.java8.FutureConverters;
import views.*;
import views.html.*;
import views.formdata.*;

public class ProductListController extends Controller {

	@Inject FormFactory formFactory;

	@Inject
	public ProductListController(FormFactory formFactory){
		this.formFactory = formFactory;
	}

	@Security.Authenticated(Secured.class)
	public Result list() {

		Product prod = new Product();

		List<Product> lp = prod.find.all();
		return ok(productlist.render("Product List", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), lp));
	}

}
