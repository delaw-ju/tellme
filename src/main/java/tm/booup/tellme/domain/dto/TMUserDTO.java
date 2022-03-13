package tm.booup.tellme.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import tm.booup.tellme.domain.entity.TMUserEntity;

@Data
@NoArgsConstructor
public class TMUserDTO {

  private int id;
  private String email;
  private String name;
  private String password;
  private String status;
  private String insDate;
  private String insUser;
  private String updDate;
  private String updUser;
  private String authorities;

  public TMUserDTO(TMUserEntity userEntity) {
    this.id = userEntity.getId();
    this.name = userEntity.getName();
    this.email = userEntity.getEmail();
    this.password = userEntity.getPassword();
    this.status = userEntity.getStatus();
    this.authorities = userEntity.getAuthorities();
  }
}
