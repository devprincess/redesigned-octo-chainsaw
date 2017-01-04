package actors;

import actors.CategoryActorProtocol.SayCategory;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Category;

/**
 * CategoryActor Class
 * -------------------
 *
 *
 * This CategoryActor class is implemented to show how is possible the use of actors in a web application, for this example
 * I make a simple query by the id of the Item I intend to ask and then return it to the class that asked, which in this case
 * is the CategoryController.
 * @author joana
 *
 */
public class CategoryActor extends UntypedActor {

	public static Props props = Props.create(CategoryActor.class);

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof SayCategory) {
			CategoryActorProtocol.SayCategory c = (SayCategory) msg;
			Category category = Category.find.byId(c.getCategory().getId());
			sender().tell(category, self());
		}
	}
}