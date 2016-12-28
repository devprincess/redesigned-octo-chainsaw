package actors;

import java.io.Serializable;

import models.Product;

public class ProductActorProtocol {

	public static class SayProduct implements Serializable{
		public final Product product;

		public Product getProduct() {
			return product;
		}

		public SayProduct(Product product) {
			this.product = product;
		}
	}
}