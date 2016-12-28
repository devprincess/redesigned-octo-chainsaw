package actors;

import java.io.Serializable;

import models.Category;

public class CategoryActorProtocol {

	public static class SayCategory implements Serializable{
		public final Category category;

		public Category getCategory() {
			return category;
		}

		public SayCategory(Category category) {
			this.category = category;
		}
	}
}
