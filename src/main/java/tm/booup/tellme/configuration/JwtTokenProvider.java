package tm.booup.tellme.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tm.booup.tellme.domain.dto.TMUserDTO;
import tm.booup.tellme.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

  private static final String ISSUER = "TELL_ME";
  private final long tokenValidTime = 30 * 60 * 1000L;
  private String secretKey = "$2a$10$8JGCIEcXAwsE0eXxy0VjROx64PVcWTyqhxSQCwoHl5o4Y6ikxO.Tu";
  @Autowired
  private UserService userService;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String createToken(String userPk, List<String> roles) {
    Date now = new Date();
    String token = null;
    try {
      token = JWT.create()
          .withIssuer(ISSUER)
          .withSubject(userPk)
          .withClaim("roles", roles)
          .withIssuedAt(now)
          .withExpiresAt(new Date(now.getTime() + tokenValidTime))
          .sign(Algorithm.HMAC256(secretKey));
    } catch (Exception e) {
      log.error("Token Create Error : {}", e.getMessage());
    }
    return token;
  }

  public Authentication getAuthentication(String token) {
    TMUserDTO userDTO = userService.findById(this.getUserPk(token));
    return new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
  }

  public String getUserPk(String token) {
    return JWT.decode(token).getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("X-AUTH-TOKEN");
  }

  public boolean validateToken(String jwtToken) {
    Date now = new Date();
    try {
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
          .withIssuer(ISSUER)
          .acceptExpiresAt(now.getTime() + tokenValidTime)
          .build();
      verifier.verify(jwtToken);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
