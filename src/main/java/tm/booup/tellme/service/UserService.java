package tm.booup.tellme.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tm.booup.tellme.defin.AccountStatus;
import tm.booup.tellme.defin.LoginResult;
import tm.booup.tellme.domain.dto.TMUserDTO;
import tm.booup.tellme.domain.entity.TMUserEntity;
import tm.booup.tellme.exception.LoginException;
import tm.booup.tellme.repository.UserRepository;

@Service
@Slf4j
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public TMUserDTO Login(TMUserDTO userDTO) throws LoginException {
    TMUserEntity userEntity = new TMUserEntity(userDTO);
    TMUserEntity responseUserEntity = userRepository.findById(userEntity);

    boolean isWrongPassword = this.isWrongPassword(userDTO.getPassword(),
        responseUserEntity.getPassword());
    if (isWrongPassword) {
      throw new LoginException(LoginResult.LoginFail.getMessage());
    }

    boolean isActiveAccount = isNotActiveAccount(responseUserEntity.getStatus());
    if (isActiveAccount) {
      throw new LoginException(LoginResult.NonActiveAccount.getMessage());
    }

    return new TMUserDTO(responseUserEntity);
  }

  public void joinIn(TMUserDTO userDTO) {
    TMUserEntity userEntity = new TMUserEntity(userDTO);
    int result = userRepository.insertUser(userEntity);
  }

  private boolean isWrongPassword(String rawPassword, String encodedPassword) {
    return !passwordEncoder.matches(rawPassword, encodedPassword);
  }

  private boolean isNotActiveAccount(String status) {
    return AccountStatus.Wait.getCode().equals(status);
  }
}
