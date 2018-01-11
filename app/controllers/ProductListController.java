package controllers;

import play.mvc.Controller;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Update;

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


	@Security.Authenticated(Secured.class)
	public Result deleteProduct(String idProduct) {

		String deleteProductStatement = "DELETE from product WHERE id =:idproduct";
		SqlUpdate deleteProductUpd = Ebean.createSqlUpdate(deleteProductStatement);
		deleteProductUpd.setParameter("idproduct", Integer.parseInt(idProduct));
		int row2 = deleteProductUpd.execute();

		return redirect(routes.ProductListController.list());

	}

	@Security.Authenticated(Secured.class)
	public Result editProduct(String idproduct) {

		Product p = new Product();
		Product foundProduct = p.find.byId(Integer.parseInt(idproduct));

		Form<ProductFormData> formData = formFactory.form(ProductFormData.class);

		ProductFormData cfd = new ProductFormData(foundProduct);
		formData = formData.fill(cfd);

		return ok(editproduct.render("Edit Category", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()), foundProduct, formData));

	}


	@Security.Authenticated(Secured.class)
	public Result updateProduct(String idproduct) {

		Form<ProductFormData> formDataProduct = formFactory.form(ProductFormData.class);

		try{
			ProductFormData ProductData = formDataProduct.bindFromRequest().get();

			System.out.println("Product data:");
			System.out.println("Name:"+ ProductData.getName());
			System.out.println("Description data:"+ ProductData.getDescription());
			System.out.println("Url data:"+ ProductData.getUrl());
			System.out.println("Price data:"+ ProductData.getPrice());
			System.out.println("Idcategory data:"+ ProductData.getIdcategory());

			Product p = Product.find.byId(Integer.parseInt(idproduct));

			if (Secured.isLoggedIn(ctx())){

				String updStatement = "update product set name = :name, description = :description, price = :price, idcategory = :idcategory, url = :url  where id=:idproduct";
				Update<Product> update = Ebean.createUpdate(Product.class, updStatement);

				update.set("name", ProductData.getName());
				update.set("url", ProductData.getUrl());
				update.set("description", ProductData.getDescription());
				update.set("price", ProductData.getPrice());
				update.set("idcategory", ProductData.getIdcategory());
				update.set("idproduct", p.getId());
				int rows = update.execute();

				formDataProduct = formDataProduct.fill(ProductData);
				flash("success", "El producto se ha actualizado exitosamente.");
			}
			else{
				flash("error", "Error: no se pudo actualizar la categoría");
			}

			return ok(editproduct.render("Edit Product", Secured.isLoggedIn(ctx()), Secured.getUserInfo(ctx()), p ,formDataProduct));
		}
		catch(Exception e){
			return badRequest(formDataProduct.errorsAsJson());
		}


	}

}
