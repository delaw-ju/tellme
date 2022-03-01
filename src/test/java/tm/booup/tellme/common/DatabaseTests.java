package tm.booup.tellme.common;

import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.jupiter.api.Test;

public class DatabaseTests {

    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://localHost:3306/tellme";
    private static final String USER = "tellme_admin";
    private static final String PASSWORD = "12121234";

    @Test
    public void testConnection() throws Exception {
      Class.forName(DRIVER);
      try {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        assert connection != null;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
}
