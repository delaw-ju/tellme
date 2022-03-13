package tm.booup.tellme.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tm.booup.tellme.configuration.MailAuth;

@Service
@Slf4j
public class MailService {

  private static final String TITLE = "Active user account in TellMe";
  private static final String SENDER = "TellMe";
  private static final String CONTENT = "click to link if you want active your account \n";
  private static final String LINK = "http://172.30.1.48:8080/active/";

  @Value("${google-email.id}")
  private String id;

  @Value("${google-email.password}")
  private String password;

  @Value("${google-email.email}")
  private String email;

  public void mailSend(String pin, int userId, String userEmail)
      throws MessagingException, UnsupportedEncodingException {
    Properties prop = System.getProperties();
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.port", "587");

    MailAuth mailAuth = new MailAuth(id, password);

    String content = CONTENT + LINK + pin + "/" + userId;

    Session session = Session.getDefaultInstance(prop, mailAuth);
    MimeMessage msg = new MimeMessage(session);
    msg.setSentDate(new Date());
    msg.setFrom(new InternetAddress(email, SENDER));
    InternetAddress to = new InternetAddress(userEmail);
    msg.setRecipient(Message.RecipientType.TO, to);
    msg.setSubject(TITLE, StandardCharsets.UTF_8.name());
    msg.setText(content, StandardCharsets.UTF_8.name());
    Transport.send(msg);
  }
}
