package controllers;

import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import models.Customer;
import play.Application;
import play.Configuration;
import play.api.Play;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import play.Environment;
import play.Logger;

/**
 * Implement authorization for this system.
 * getUserName() and onUnauthorized override superclass methods to restrict
 * access to the profile() page to logged in users.
 *
 * getUser(), isLoggedIn(), and getUserInfo() provide static helper methods so that controllers
 * can know if there is a logged in user.
 */
public class Secured extends Security.Authenticator {


	private static Configuration configuration = Play.current().injector().instanceOf(Configuration .class);;
	/**
	 * Used by authentication annotation to determine if user is logged in.
	 * @param ctx The context.
	 * @return The email address of the logged in user, or null if not logged in.
	 */
	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

	/**
	 * Instruct authenticator to automatically redirect to login page if unauthorized.
	 * @param context The context.
	 * @return The login page.
	 */
	@Override
	public Result onUnauthorized(Context context) {
		return redirect(routes.LoginController.login());
	}

	/**
	 * Return the email of the logged in user, or null if no logged in user.
	 *
	 * @param ctx the context containing the session
	 * @return The email of the logged in user, or null if user is not logged in.
	 */
	public static String getUser(Http.Context ctx) {

		String previousTick = ctx.session().get("userTime");
		if (previousTick != null && !previousTick.equals("")) {
			long previousT = Long.valueOf(previousTick);
			long currentT = new Date().getTime();
			long timeout = Long.valueOf(configuration.getString("sessionTimeOut")) * 1000 * 60;

			if ((currentT - previousT) > timeout) {
				// session expired
				ctx.session().clear();
				return null;
			}
		}

		String tickString = Long.toString(new Date().getTime());
		ctx.session().put("userTime", tickString);
		return ctx.session().get("email");
	}

	/**
	 * True if there is a logged in user, false otherwise.
	 * @param ctx The context.
	 * @return True if user is logged in.
	 */
	public static boolean isLoggedIn(Context ctx) {
		return (getUser(ctx) != null);
	}

	/**
	 * Return the UserInfo of the logged in user, or null if no user is logged in.
	 * @param ctx The context.
	 * @return The UserInfo, or null.
	 */

	public static Customer getUserInfo(Context ctx) {
		final List<Customer> customers = Customer.find.where().eq("email", getUser(ctx)).findList();
		return (isLoggedIn(ctx) ? customers.get(0) : null);
	}
}