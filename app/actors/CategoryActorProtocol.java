package actors;

import java.io.Serializable;

import models.Category;
import play.mvc.Http;
import play.mvc.Http.Context;
/**
 * CategoryActorProtocol Class:
 * ---------------------------
 *
 * To be used in the CategoryActorProtocol, if the actor receives a message containing this type,
 * then the response will be successful. (Communicator with actors between CategoryActor and CategoryController)
 *
 * @author joana
 *
 */
public class CategoryActorProtocol {

	public static class SayCategory implements Serializable{
		public final Category category;

		public SayCategory(Category category){
			this.category = category;
		}
		public Category getCategory() {
			return category;
		}

	}
}
