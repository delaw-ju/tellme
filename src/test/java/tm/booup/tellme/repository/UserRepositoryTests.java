package tm.booup.tellme.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tm.booup.tellme.domain.entity.TMUserEntity;

@SpringBootTest
public class UserRepositoryTests {

  @Autowired
  UserRepository userRepository;

  TMUserEntity user;

  @BeforeEach
  public void before() {
    user = new TMUserEntity();
  }

  @Test
  public void findById() {
    TMUserEntity result = userRepository.findById(user);
    assert result != null;
  }
}
