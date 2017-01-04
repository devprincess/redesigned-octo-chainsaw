package actors;

import actors.ProductActorProtocol.SayProduct;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Product;

/**
 * ProductActor Class
 * -------------------
 *
 *
 * This class is implemented to show how is possible the use of actors in a web application, for this example
 * I make a simple query by the id of the Item I intend to ask and then return it to the class that asked, which in this case
 * is the ProductController.
 * @author joana
 *
 */
public class ProductActor extends UntypedActor {

	public static Props props = Props.create(ProductActor.class);

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof SayProduct) {
			ProductActorProtocol.SayProduct p = (SayProduct) msg;
			Product prod = Product.find.byId(p.product.getId());
			sender().tell(prod, self());
		}
	}
}