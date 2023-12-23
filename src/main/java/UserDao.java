import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clasa UserDao furnizeaza metode pentru interactiunea cu baza de date referitoare la obiectele de tip User.
 */
public class UserDao {

    protected static final int LOG_ROUNDS = 12;

    /**
     * Metoda pentru crearea unui nou utilizator in baza de date.
     *
     * @param user Utilizatorul care trebuie creat.
     */
    public void createUser(User user) {
        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, user.getUsername());
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(LOG_ROUNDS));
                statement.setString(2, hashedPassword);

                statement.setString(3, user.getEmail());
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pentru obtinerea unui utilizator dupa numele de utilizator.
     *
     * @param username Numele de utilizator al utilizatorului cautat.
     * @return Utilizatorul gasit sau null daca nu exista.
     */
    public static User getUserByUsername(String username) {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM user WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User(
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("email"),
                                resultSet.getBoolean("isAdmin")
                        );
                        user.setId(resultSet.getInt("id"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metoda pentru actualizarea scorului utilizatorului.
     *
     * @param userId ID-ul utilizatorului ale carui scor trebuie actualizat.
     * @param scoreToAdd Scorul care trebuie adaugat la scorul existent al utilizatorului.
     * @throws SQLException Exceptie care poate aparea in cazul unor erori SQL.
     */
    public static void updateScore(int userId, int scoreToAdd) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            int currentScore = getCurrentScore(userId, connection);
            int newScore = currentScore + scoreToAdd;
            updateScoreInDatabase(userId, newScore, connection);
        }
    }

    /**
     * Metoda pentru obtinerea scorului curent al utilizatorului.
     *
     * @param userId ID-ul utilizatorului ale carui scor trebuie obtinut.
     * @param connection Obiectul de conexiune la baza de date.
     * @return Scorul curent al utilizatorului.
     * @throws SQLException Exceptie care poate aparea in cazul unor erori SQL.
     */
    private static int getCurrentScore(int userId, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT score FROM user WHERE id = ?")) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("score");
                } else {
                    throw new SQLException("User not found");
                }
            }
        }
    }

    /**
     * Metoda pentru actualizarea scorului in baza de date.
     *
     * @param userId  ID-ul utilizatorului ale carui scor trebuie actualizat.
     * @param newScore Noul scor al utilizatorului.
     * @param connection Obiectul de conexiune la baza de date.
     * @throws SQLException Exceptie care poate aparea in cazul unor erori SQL.
     */
    private static void updateScoreInDatabase(int userId, int newScore, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE user SET score = ? WHERE id = ?")) {
            statement.setInt(1, newScore);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    /**
     * Metoda pentru obtinerea clasamentului utiliaztorilor.
     *
     * @return Lista de utilizatori ordonata dupa scor in ordine descrescatoare.
     */
    public static List<User> getRanking() {
        List<User> ranking = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM user WHERE isAdmin = false ORDER BY score DESC LIMIT 10";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User (
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("email")
                        );
                        user.setId(resultSet.getInt("id"));
                        user.setScore(resultSet.getInt("score"));
                        ranking.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ranking;
    }


    /**
     * Metoda pentru setarea unui nou scor pentru un utilizator.
     *
     * @param userId ID-ul utilizatorului caruia i se seteaza scorul.
     * @param newScore Noul scor al utilizatorului.
     * @throws SQLException Exceptie care poate aparea in cazul unor erori SQL.
     */
    public static void setScore(int userId, int newScore) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            updateScoreInDatabase(userId, newScore, connection);
        }
    }


}