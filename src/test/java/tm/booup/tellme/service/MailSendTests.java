package tm.booup.tellme.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tm.booup.tellme.configuration.MailAuth;

@SpringBootTest
public class MailSendTests {

  private static final String TITLE = "Active user account in TellMe";
  private static final String CONTENT = "click to link if you want active your account \n";
  private MailAuth mailAuth;

  @BeforeEach
  public void before() {
    mailAuth = new MailAuth("team.booup", "ztszlmxmtwwnkipv");
  }

  @Test
  public void mailSend() throws MessagingException, UnsupportedEncodingException {
    Properties prop = System.getProperties();
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.port", "587");

    Session session = Session.getDefaultInstance(prop, mailAuth);
    MimeMessage msg = new MimeMessage(session);

    msg.setSentDate(new Date());
    msg.setFrom(new InternetAddress("team.booup@gmail.com", ""));
    InternetAddress to = new InternetAddress("aguitarj@naver.com");
    msg.setRecipient(Message.RecipientType.TO, to);
    msg.setSubject("제목", "UTF-8");
    msg.setText("안녕하세요 테스트 메일입니다.", "UTF-8");
    Transport.send(msg);
  }

}
