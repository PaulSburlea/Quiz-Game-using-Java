import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/quiz";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
