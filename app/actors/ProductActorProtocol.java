package actors;

import java.io.Serializable;

import models.Product;
/**
 * ProductActorProtocol Class:
 * ---------------------------
 *
 * To be used in the ProductActorProtocol, if the actor receives a message containing this type,
 * then the response will be successful. (Communicator with actors between ProductActor and ProductController)
 *
 * @author joana
 *
 */
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