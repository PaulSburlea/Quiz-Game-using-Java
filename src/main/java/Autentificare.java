import java.sql.*;

public class Autentificare {

    private Connection connection;

    public Autentificare() {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password, String email) {
        try {
            if (!isValidEmail(email)) {
                System.out.println("Email invalid, Emailul trebuie sa fie de forma [prenume].[nume]@e-uvt.ro");
                return false;
            }

            String query = "INSERT INTO user (username, password, email) VALUE (?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean isValidEmail (String email){
        try {
            return email.matches("[a-zA-Z]+\\.[a-zA-Z]+\\d{0,2}@e-uvt.ro");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean userAutentificat(String username, String password) {
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getEmail(String username) {
        try {
            String query = "SELECT email FROM user WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isAdmin (String username) {
        try {
            String query = "SELECT isAdmin FROM user WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next() && resultSet.getBoolean("isAdmin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
