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

public class CustomerController extends Controller{

	@Inject FormFactory formFactory;

	@Inject
	public CustomerController(FormFactory formFactory){
		this.formFactory = formFactory;
	}

	@Security.Authenticated(Secured.class)
	public Result profile() {
		Form<CustomerFormData> formData = formFactory.form(CustomerFormData.class);
		//find this user in the database using Ebean ORM
		List<Customer> users = Customer.find.where().eq("email", session("email")).findList();



		System.out.println(users.size());
		if(!users.isEmpty()) {

			CustomerFormData cfd = new CustomerFormData();
			cfd.setName(users.get(0).getName());
			cfd.setEmail(users.get(0).getEmail());
			cfd.setMobile(users.get(0).getMobile());
			cfd.setPwd(users.get(0).getPwd());
			cfd.setBirthdate(users.get(0).getBirthdate());
			cfd.setGender(users.get(0).getGender());

			formData = formData.fill(cfd);
		}
		return ok(profile.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
	}


	@Security.Authenticated(Secured.class)
	public Result updateInfo() {
		// Get the submitted form data from the request object, and run validation.
		Form<CustomerFormData> formData = formFactory.form(CustomerFormData.class);
		CustomerFormData userData = formData.bindFromRequest().get();

		return redirect(routes.LoginController.home());
	}
}
