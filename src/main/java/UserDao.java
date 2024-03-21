import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The UserDao class provides methods for interacting with the database regarding User objects.
 */
public class UserDao {

    protected static final int LOG_ROUNDS = 12;

    /**
     * Method for creating a new user in the database.
     *
     * @param user The user to be created.
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
     * Method for obtaining a user by username.
     *
     * @param username The user name of the user sought.
     * @return User found or null if not exist.
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
     * Method for updating user score.
     *
     * @param userId user ID whose score needs to be updated.
     * @param scoreToAdd Score to be added to the existing user score.
     * @throws SQLException Exception that may occur in case of SQL errors.
     */
    public static void updateScore(int userId, int scoreToAdd) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            int currentScore = getCurrentScore(userId, connection);
            int newScore = currentScore + scoreToAdd;
            updateScoreInDatabase(userId, newScore, connection);
        }
    }

    /**
     * Method for obtaining the current user score.
     *
     * @param userId user ID whose score must be obtained.
     * @param connection The object of connection to the database.
     * @return User's current score.
     * @throws SQLException Exception that may occur in case of SQL errors.
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
     * Method for updating the score in the database.
     *
     * @param userId user ID whose score needs to be updated.
     * @param newScore New user score.
     * @param connection The object of connection to the database.
     * @throws SQLException Exception that may occur in case of SQL errors.
     */
    private static void updateScoreInDatabase(int userId, int newScore, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE user SET score = ? WHERE id = ?")) {
            statement.setInt(1, newScore);
            statement.setInt(2, userId);
            statement.executeUpdate();
        }
    }

    /**
     * Method for obtaining the ranking of users.
     *
     * @return List of users ordered by score in descending order.
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
     * Method for setting a new score for a user.
     *
     * @param userId the user ID to which the score is set.
     * @param newScore New user score.
     * @throws SQLException Exception that may occur in case of SQL errors.
     */
    public static void setScore(int userId, int newScore) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            updateScoreInDatabase(userId, newScore, connection);
        }
    }


}