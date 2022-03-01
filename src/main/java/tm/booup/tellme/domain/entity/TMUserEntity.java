package tm.booup.tellme.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import tm.booup.tellme.domain.dto.TMUserDTO;

@Data
@NoArgsConstructor
public class TMUserEntity {

  private String id;
  private String email;
  private String name;
  private String password;
  private String status;
  private String insDate;
  private String insUser;
  private String updDate;
  private String updUser;

  public TMUserEntity(TMUserDTO userDTO) {
    this.id = userDTO.getId();
    this.email = userDTO.getEmail();
    this.name = userDTO.getName();
    this.password = userDTO.getPassword();
    this.status = userDTO.getStatus();
    this.insDate = userDTO.getInsDate();
    this.insUser = userDTO.getInsUser();
    this.updDate = userDTO.getUpdDate();
    this.updUser = userDTO.getUpdUser();
  }
}
