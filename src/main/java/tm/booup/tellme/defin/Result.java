package tm.booup.tellme.defin;

public enum Result {
  OK();

  public enum Login {
    LoginFail("로그인에 실패했습니다.", -1),
    NonActiveAccount("이메일 인증을 완료 해 주세요.", -2),
    ActiveError("계정 활성화 중 오류가 발생했습니다.", -3),
    SignUpError("등록 중 오류가 발생했습니다.", -4);

    String message;
    int code;

    Login(String message, int code) {
      this.message = message;
      this.code = code;
    }

    public int getCode() {
      return this.code;
    }

    public String getMessage() {
      return this.message;
    }
  }

  public enum Active {

  }
}
