import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Database class provides a connection to a MySQL database.
 */
public class Database {

    /**
     * Gets a connection to the database.
     * @return A connection to the MySQL database.
     * @throws SQLException Exception discarded in case of an error when getting the connection.
     */
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/quiz";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
