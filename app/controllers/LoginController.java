package controllers;

import static akka.pattern.Patterns.ask;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javax.inject.Inject;
import models.Category;
import models.Customer;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import scala.compat.java8.FutureConverters;
import views.*;
import views.html.*;
import views.formdata.*;

/**
 *Login Controller:
 *---------------------
 *
 * This class handles the access to the system by interacting with the Secured class (app/controllers/Secured.java)
 * All the methods in this class are returning the requests in the typical way that Play Framework does, that is
 * returning a Result object. Internally, Play framework does this calls asynchronously with the use of CompletionStage
 * and that behaviour is shown in the controllers : ProductController and CategoryController.
 *
 * Also the use of Lists are handled using the Java Collections Framework and Generics.
 *
 * @author joana
 *
 */
public class LoginController extends Controller{

	//@Inject AsyncController async;
	@Inject FormFactory formFactory;
	AtomicInteger usersCounter;

	@Inject
	public LoginController(FormFactory formFactory, AtomicInteger usersCounter){
		this.formFactory = formFactory;
		this.usersCounter = usersCounter;
	}
	/**
	 * Provides the Index page.
	 * @return The Index page.
	 */
	public Result index() {
		return ok(index.render("Home", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx())));
	}

	/**
	 * Provides the Login page (only to unauthenticated users).
	 * @return The Login page.
	 */
	public Result login() {
		if (Secured.isLoggedIn(ctx())){
			return redirect(routes.LoginController.home());
		}
		else{
			Form<LoginFormData> formData = formFactory.form(LoginFormData.class);
			return ok(login.render("Login", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
		}
	}


	/**
	 * Processes a login form submission from an unauthenticated user.
	 * First we bind the HTTP POST data to an instance of LoginFormData.
	 * The binding process will invoke the LoginFormData.validate() method.
	 * If errors are found, re-render the page, displaying the error data.
	 * If errors not found, render the page with the good data.
	 * @return The index page with the results of validation.
	 */
	public Result postLogin() {

		// Get the submitted form data from the request object, and run validation.
		Form<LoginFormData> formData = formFactory.form(LoginFormData.class);
		LoginFormData loginData = formData.bindFromRequest().get();

		//find this user in the database using Ebean ORM
		List<Customer> users = Customer.find.where().eq("email", loginData.email).eq("pwd", loginData.password).findList();

		//if user exists, save the cookie and go to profile page
		if (!users.isEmpty()){
			session().clear();
			session("email", loginData.email);
			usersCounter.incrementAndGet();
			return redirect(routes.LoginController.home());
		}
		else{
			//if this user does not exist, return to the login website
			flash("error", "Login credentials not valid.");
			return redirect(routes.LoginController.login());
		}
	}

	/**
	 * Logs out (only for authenticated users) and returns them to the Index page.
	 * @return A redirect to the Index page.
	 */
	@Security.Authenticated(Secured.class)
	public Result logout() {

		usersCounter.decrementAndGet();
		session().clear();
		return redirect(routes.LoginController.index());
	}

	/**
	 * Provides the Profile page (only to authenticated users).
	 * @return The Profile page.
	 */
	@Security.Authenticated(Secured.class)
	public Result home() {
		List<Category> categories = Category.find.all();
		return ok(home.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), categories, usersCounter.get()));
	}

}
