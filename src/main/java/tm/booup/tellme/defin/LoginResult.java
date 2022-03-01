package tm.booup.tellme.defin;

public enum LoginResult {
  LoginFail("로그인에 실패했습니다."),
  NonActiveAccount("이메일 인증을 완료 해 주세요.");

  String message;

  LoginResult(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
