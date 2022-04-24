package tm.booup.tellme.controller;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import tm.booup.tellme.defin.SessionKey;
import tm.booup.tellme.domain.ResultMap;
import tm.booup.tellme.domain.dto.TMUserDTO;
import tm.booup.tellme.exception.LoginException;
import tm.booup.tellme.service.UserService;


@Slf4j
@RestController
public class LoginController extends BaseController {

  @Autowired
  private UserService userService;

  @PostMapping("signin")
  public ResultMap login(HttpServletRequest request, @RequestBody TMUserDTO paramUser) {
    try {
      TMUserDTO userDTO = userService.login(paramUser);
      HttpSession session = request.getSession();
      session.setAttribute(SessionKey.UserInfo.getKey(), userDTO);
      return new ResultMap(200, "성공", userDTO);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResultMap(-1, "오류");
    }
  }

  @PostMapping("signup")
  public ResultMap signup(@RequestBody TMUserDTO paramUser) {
    try {
      userService.signup(paramUser);
      return new ResultMap(200, "성공");
    } catch (MessagingException e) {
      log.error(e.getMessage());
      return new ResultMap(-3, e.getMessage());
    } catch (UnsupportedEncodingException e) {
      log.error(e.getMessage());
      return new ResultMap(-2, e.getMessage());
    } catch (LoginException e) {
      log.error(e.getMessage());
      return new ResultMap(-1, e.getMessage());
    }
  }

  @GetMapping("active/{pin}/{id}")
  public RedirectView activeAccount(@PathVariable("pin") String pin
      , @PathVariable("id") int id) {
    try {
      userService.activeAccount(pin, id);
      return new RedirectView("https://localhost:3000");
    } catch (LoginException e) {
      log.error(e.getMessage());
      return new RedirectView("https://localhost:3000/error");
    }
  }

  @DeleteMapping("delete/{email}")
  public ResultMap deleteAccount(@PathVariable("email") String email) {
    try {
      userService.deleteAccount(email);
      return new ResultMap(200, "성공");
    } catch (Exception e) {
      return new ResultMap(-1, "실패");
    }
  }


}
