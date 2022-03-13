package tm.booup.tellme.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultMap {

  private int code;

  private String message;

  private Object result;

  public ResultMap(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public ResultMap(int code, String message, Object result) {
    this.code = code;
    this.message = message;
    this.result = result;
  }
}
