package actors;

import actors.CategoryActorProtocol.SayCategory;
import akka.actor.Props;
import akka.actor.UntypedActor;
import models.Category;

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