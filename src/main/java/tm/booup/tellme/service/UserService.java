package tm.booup.tellme.service;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tm.booup.tellme.defin.AccountStatus;
import tm.booup.tellme.defin.Result;
import tm.booup.tellme.domain.dto.TMUserDTO;
import tm.booup.tellme.domain.entity.TMUserEntity;
import tm.booup.tellme.exception.LoginException;
import tm.booup.tellme.repository.UserRepository;

@Service
@Slf4j
//TODO: JWT 적용
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private MailService mailService;

  public TMUserDTO login(TMUserDTO userDTO) throws LoginException {
    TMUserEntity userEntity = new TMUserEntity(userDTO);
    TMUserEntity responseUserEntity = userRepository.findByEmail(userEntity.getEmail());

    boolean isWrongPassword = this.isWrongPassword(userDTO.getPassword(),
        responseUserEntity.getPassword());
    if (isWrongPassword) {
      throw new LoginException(Result.Login.LoginFail.getMessage());
    }

    boolean isActiveAccount = isNotActiveAccount(responseUserEntity.getStatus());
    if (isActiveAccount) {
      throw new LoginException(Result.Login.NonActiveAccount.getMessage());
    }

    return new TMUserDTO(responseUserEntity);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void signup(TMUserDTO userDTO)
      throws MessagingException, UnsupportedEncodingException, LoginException {
    TMUserEntity userEntity = new TMUserEntity(userDTO);
    String pin = UUID.randomUUID().toString();
    userEntity.setAuthPin(pin);
    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    int result = userRepository.insertUser(userEntity);
    this.judgementDataWrite(1, result, Result.Login.SignUpError.getMessage());
    //mailService.mailSend(pin, userEntity.getId(), userEntity.getEmail());
  }

  public void activeAccount(String pin, int id) throws LoginException {
    TMUserEntity userEntity = new TMUserEntity();
    userEntity.setAuthPin(pin);
    userEntity.setId(id);
    int result = userRepository.updateUserStatus(userEntity);
    this.judgementDataWrite(1, result, Result.Login.ActiveError.getMessage());
  }

  public void deleteAccount(String email) throws Exception {
    int result = userRepository.deleteUser(email);
    if (result <= 0) {
      throw new Exception();
    }
  }

  public TMUserDTO findById(String userId) {
    TMUserEntity responseUserEntity = userRepository.findById(userId);
    return new TMUserDTO(responseUserEntity);
  }

  private void judgementDataWrite(int expectResult, int actualResult, String message)
      throws LoginException {
    if (expectResult != actualResult) {
      throw new LoginException(message);
    }
  }

  private boolean isWrongPassword(String rawPassword, String encodedPassword) {
    return !passwordEncoder.matches(rawPassword, encodedPassword);
  }

  private boolean isNotActiveAccount(String status) {
    return !AccountStatus.Normal.getCode().equals(status);
  }
}
