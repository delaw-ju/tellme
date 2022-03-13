package tm.booup.tellme.defin;

public enum ResultStatus {
  OK(200, "정상적으로 처리되었습니다."),
  UNEXPECTED_ERROR(-999, "시스템 오류가 발생했습니다.");

  int code;
  String message;

  ResultStatus(int code, String message) {

  }
}
