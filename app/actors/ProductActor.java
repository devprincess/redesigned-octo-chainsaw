package actors;

import java.util.List;

import actors.ProductActorProtocol.SayProduct;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Category;
import models.Product;

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