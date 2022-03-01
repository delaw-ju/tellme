package tm.booup.tellme.defin;

public enum AccountStatus {
  Wait("대기중", "W"),
  Normal("정상", "N"),
  Delete("삭제", "D");

  String statusString;

  String code;

  AccountStatus(String statusString, String code) {
    this.statusString = statusString;
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public String getStatusString() {
    return statusString;
  }
}

