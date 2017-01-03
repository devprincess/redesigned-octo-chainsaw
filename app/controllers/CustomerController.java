package controllers;

import static akka.pattern.Patterns.ask;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;

import models.Category;
import models.Customer;
import models.Product;
import models.Stock;
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

		if(!users.isEmpty()) {
			CustomerFormData cfd = new CustomerFormData(users.get(0));
			formData = formData.fill(cfd);
		}
		return ok(profile.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
	}


	@Security.Authenticated(Secured.class)
	public Result updateInfo() {

		Form<CustomerFormData> formData = formFactory.form(CustomerFormData.class);
		CustomerFormData userData = formData.bindFromRequest().get();

		if (Secured.isLoggedIn(ctx())){
			Customer c = Customer.find.where().eq("email", session("email")).findList().get(0);

			//update info of the user using Ebean ORM
			String updStatement = "update customer set name = :name, mobile = :mobile, email= :email, pwd= :pwd, gender= :gender, birthdate= :birthdate, address= :address  where id=:idcustomer";
			Update<Customer> update = Ebean.createUpdate(Customer.class, updStatement);

			update.set("name", userData.getName());
			update.set("mobile", userData.getMobile());
			update.set("email", userData.getEmail());
			update.set("pwd", userData.getPwd());
			update.set("gender", userData.getGender());
			update.set("birthdate", userData.getBirthdate());
			update.set("address", userData.getAddress());
			update.set("idcustomer", c.getId());
			int rows = update.execute();

			//since the user might have changed his email, need to clear and set the session again with this change.
			session().clear();
			session("email", c.getEmail());

			formData = formData.fill(userData);
			flash("success", "The info has been updated.");
		}
		else{
			flash("error", "Cannot update the data.");
		}
		return redirect("/profile");
	}
}
