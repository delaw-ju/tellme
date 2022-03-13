package tm.booup.tellme.defin;

public enum SessionKey {

  UserInfo("USER_INFO");

  private final String key;

  SessionKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}
