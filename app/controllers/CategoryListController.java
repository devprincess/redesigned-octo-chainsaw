package controllers;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import play.mvc.Security;
import static akka.pattern.Patterns.ask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import models.Category;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.Context;
import scala.compat.java8.FutureConverters;
import views.*;
import views.html.*;
import views.formdata.*;

public class CategoryListController extends Controller{

	@Inject FormFactory formFactory;

	@Inject
	public CategoryListController(FormFactory formFactory){
		this.formFactory = formFactory;
	}

	@Security.Authenticated(Secured.class)
	public Result list() {

		Category c = new Category();

		List<Category> lc = c.find.all();
		return ok(categorylist.render("Category List", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()), lc));
	}

}
