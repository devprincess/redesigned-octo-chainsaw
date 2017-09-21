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
import models.ShoppingCartItem;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.Context;
import scala.compat.java8.FutureConverters;
import views.*;
import views.html.*;
import views.formdata.*;
import java.util.ListIterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Update;
import models.Product;
import models.ShoppingCart;
import models.Stock;

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


	@Security.Authenticated(Secured.class)
	public Result deleteCategory(String idCategory) {

		String deleteProductsstatement = "DELETE from product WHERE idcategory =:idcategory";

		List<Product> listProducts = Product.find.where().eq("idcategory", idCategory).findList();

		if (!listProducts.isEmpty()){
			SqlUpdate deleteProductUpd = Ebean.createSqlUpdate(deleteProductsstatement);
			deleteProductUpd.setParameter("idcategory", Integer.parseInt(idCategory));
			int row1 = deleteProductUpd.execute();
		}

		String deleteCategoryStatement = "DELETE from category WHERE id =:idcategory";
		SqlUpdate deleteCategoryUpd = Ebean.createSqlUpdate(deleteCategoryStatement);
		deleteCategoryUpd.setParameter("idcategory", Integer.parseInt(idCategory));
		int row2 = deleteCategoryUpd.execute();

		List<Category> listCategories = Category.find.all();

		return ok(categorylist.render("Category List", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), listCategories));

	}

	//@TODO: check on this one

	@Security.Authenticated(Secured.class)
	public Result insertNewCategory() {

		Form<CustomerFormData> formData = formFactory.form(CustomerFormData.class);
		CustomerFormData userData = formData.bindFromRequest().get();

		if (Secured.isLoggedIn(ctx())){
			User c = User.find.where().eq("email", session("email")).findList().get(0);

			String updStatement = "update user set name = :name, mobile = :mobile, email= :email, pwd= :pwd, gender= :gender, birthdate= :birthdate, address= :address  where id=:idcustomer";
			Update<User> update = Ebean.createUpdate(User.class, updStatement);

			update.set("name", userData.getName());
			update.set("mobile", userData.getMobile());
			update.set("email", userData.getEmail());
			update.set("pwd", userData.getPwd());
			update.set("gender", userData.getGender());
			update.set("birthdate", userData.getBirthdate());
			update.set("address", userData.getAddress());
			update.set("idcustomer", c.getId());
			int rows = update.execute();

			session().clear();
			session("email", c.getEmail());

			formData = formData.fill(userData);
			flash("success", "Your information has been updated.");
		}
		else{
			flash("error", "Cannot update the data.");
		}

		return ok(profile.render("Profile", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), formData));
	}
}
