package actors;

import java.io.Serializable;

import models.Category;
import play.mvc.Http;
import play.mvc.Http.Context;

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
