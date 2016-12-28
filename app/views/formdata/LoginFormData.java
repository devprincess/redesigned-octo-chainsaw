package views.formdata;

import play.data.validation.ValidationError;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing class for the login form.
 */
public class LoginFormData {

  /** The submitted email. */
  public String email = "";
  /** The submitted password. */
  public String password = "";

  /** Required for form instantiation. */
  public LoginFormData() {
  }

}