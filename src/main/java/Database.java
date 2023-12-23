import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clasa Database furnizeaza o conexiune la o baza de date MySQL.
 */
public class Database {

    /**
     * Obtine o conexiune la baza de date.
     * @return O conexiune la baza de date MySQL.
     * @throws SQLException Exceptie aruncata in cazul unei erori la obtinerea conexiunii.
     */
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/quiz";
        String user = "root";
        String password = "root";
        return DriverManager.getConnection(url, user, password);
    }
}
