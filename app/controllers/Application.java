package controllers;

import static akka.pattern.Patterns.ask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import javax.inject.Inject;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Update;

import actors.CategoryActor;
import actors.CategoryActorProtocol;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import models.Category;
import models.Product;
import models.ShoppingCart;
import models.ShoppingCartItem;
import models.Stock;
import models.User;
import play.Environment;
import play.Logger;
import play.api.Play;
import play.data.Form;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.formdata.CategoryFormData;
import views.formdata.ProductFormData;
import views.html.*;
import scala.compat.java8.FutureConverters;

import play.Configuration;

public class Application extends Controller{

	@Inject FormFactory formFactory;

	//private static Configuration configuration = Play.current().injector().instanceOf(Configuration .class);;

	public Result uploadCategory(String idcategory) throws IOException {

		System.out.println("UPLOAD CATEGORY!!!"+ idcategory);

		Http.MultipartFormData<File> body = request().body().asMultipartFormData();
		Http.MultipartFormData.FilePart<File> picture = body.getFile("file");

		//Path uploadPath = Environment.simple().rootPath().toPath();

		//final String uploadFolder = "/public/uploads/";

		if (picture != null) {
			String fileName = picture.getFilename();

			System.out.println("Filename is:"+ picture.getFilename());
			String contentType = picture.getContentType();
			File file = picture.getFile();

			//TODO: to test on dev use these lines below (local)
			//Files.deleteIfExists(Paths.get("/home/joana/cdsstore/app/public/images/", (picture.getFilename())));
			//Files.copy(file.toPath(), Paths.get("/home/joana/cdsstore/app/public/images/", (picture.getFilename())));

			//This is for production:
			///home/bas/app_add52b3f-a560-49e4-b925-952452c1db3b/
			try{
				//Files.deleteIfExists(Paths.get("/home/bas/app_add52b3f-a560-49e4-b925-952452c1db3b/public/images/", (picture.getFilename())));
				System.out.println("File to path:"+file.toPath());

				Files.copy(file.toPath(), Paths.get("/home/bas/app_add52b3f-a560-49e4-b925-952452c1db3b/public/images/", (picture.getFilename())));
				Files.deleteIfExists(file.toPath());
			}
			catch(Exception e){
				e.printStackTrace();
			}

			Category c = new Category();
			Category  foundCategory = c.find.byId(Integer.parseInt(idcategory));

			//TODO: update url of the uploaded image before showing the form!

			String updStatement = "update category set name = :name, url = :url  where id=:idcategory";
			Update<Category> update = Ebean.createUpdate(Category.class, updStatement);

			update.set("name", foundCategory.getName());

			System.out.println("name of the category:"+ foundCategory.getName());

			update.set("url", "/assets/images/"+picture.getFilename());
			update.set("idcategory", idcategory);
			int rows = update.execute();
			System.out.println(" rows updated:"+ rows);

			foundCategory.setUrl("/assets/images/"+picture.getFilename());

			Form<CategoryFormData> formData = formFactory.form(CategoryFormData.class);

			CategoryFormData cfd = new CategoryFormData(foundCategory);
			formData = formData.fill(cfd);

			return ok(editcategory.render("Edit Category", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()), foundCategory, formData));


		} else {
			flash("error", "Missing file");
			return badRequest();
		}
	}


	public Result uploadProduct(String idproduct) throws IOException {

		System.out.println("UPLOAD PRODUCT!!!"+ idproduct);

		Http.MultipartFormData<File> body = request().body().asMultipartFormData();
		Http.MultipartFormData.FilePart<File> picture = body.getFile("file");

		//Path uploadPath = Environment.simple().rootPath().toPath();

		//final String uploadFolder = "/public/uploads/";

		if (picture != null) {
			String fileName = picture.getFilename();

			System.out.println("Filename is:"+ picture.getFilename());
			String contentType = picture.getContentType();
			File file = picture.getFile();

			//TODO: to test on dev use these lines below (local)
			//Files.deleteIfExists(Paths.get("/home/joana/cdsstore/app/public/images/", (picture.getFilename())));
			//Files.copy(file.toPath(), Paths.get("/home/joana/cdsstore/app/public/images/", (picture.getFilename())));

			//This is for production:
			///home/bas/app_add52b3f-a560-49e4-b925-952452c1db3b/
			try{

				System.out.println("File to path:"+file.toPath());
				//Files.deleteIfExists(Paths.get("/home/bas/app_add52b3f-a560-49e4-b925-952452c1db3b/public/images/", (picture.getFilename())));
				Files.copy(file.toPath(), Paths.get("/home/bas/app_add52b3f-a560-49e4-b925-952452c1db3b/public/images/", (picture.getFilename())));
				Files.deleteIfExists(file.toPath());
			}
			catch(Exception e){
				e.printStackTrace();
			}

			Product p = new Product();
			Product foundProduct = p.find.byId(Integer.parseInt(idproduct));

			//TODO: update url of the uploaded image before showing the form!

			String updStatement = "update product set name = :name, url = :url  where id=:idproduct";
			Update<Product> update = Ebean.createUpdate(Product.class, updStatement);

			update.set("name", foundProduct.getName());

			System.out.println("name of the category:"+ foundProduct.getName());

			update.set("url", "/assets/images/"+picture.getFilename());
			update.set("idproduct", idproduct);
			int rows = update.execute();
			System.out.println(" rows updated:"+ rows);

			foundProduct.setUrl("/assets/images/"+picture.getFilename());

			Form<ProductFormData> formData = formFactory.form(ProductFormData.class);

			ProductFormData cfd = new ProductFormData(foundProduct);
			formData = formData.fill(cfd);

			List<Category> lc = Category.find.all();

			return ok(editproduct.render("Edit Category", Secured.isLoggedIn(ctx()),  Secured.getUserInfo(ctx()), foundProduct, foundProduct.getIdcategory() ,lc ,formData));


		} else {
			flash("error", "Missing file");
			return badRequest();
		}
	}

}