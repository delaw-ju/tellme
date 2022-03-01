package tm.booup.tellme.common;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptTests {

  @Test
  public void passwordTest() {
    String password = "1234";
    String encodedPassword = "$2a$10$xy5.Rhr1VORsd0L3Sz0tBuWsKKRcjyJGTvWtjOWuYPDE0MSjk/BeG";
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    assert passwordEncoder.matches(password, encodedPassword);
  }

}
