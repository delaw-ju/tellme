package tm.booup.tellme.configuration;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuth extends Authenticator {

  private final PasswordAuthentication passwordAuthentication;

  public MailAuth(String id, String password) {
    passwordAuthentication = new PasswordAuthentication(id, password);
  }

  @Override
  public PasswordAuthentication getPasswordAuthentication() {
    return passwordAuthentication;
  }
}
