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
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import scala.compat.java8.FutureConverters;

import play.Configuration;

public class Application extends Controller{


	//private static Configuration configuration = Play.current().injector().instanceOf(Configuration .class);;

	public Result upload() throws IOException {
		Http.MultipartFormData<File> body = request().body().asMultipartFormData();
		Http.MultipartFormData.FilePart<File> picture = body.getFile("picture");

		//Path uploadPath = Environment.simple().rootPath().toPath();

		//final String uploadFolder = "/public/uploads/";

		if (picture != null) {
			String fileName = picture.getFilename();
			String contentType = picture.getContentType();
			File file = picture.getFile();

			// added lines
			//	String myUploadPath = configuration.getString("myUploadPath");

			//file.renameTo(new File(myUploadPath, fileName));

			Files.copy(file.toPath(), Paths.get("/home/joana/cdsstore/app/public/images/", ("copy_" + file.getName())));
			Files.deleteIfExists(file.toPath());

			return ok("file saved as " + file.getAbsolutePath());
		} else {
			flash("error", "Missing file");
			return badRequest();
		}
	}

}