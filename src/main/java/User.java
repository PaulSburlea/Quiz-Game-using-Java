/**
 * The User class is an entity that contains information about a user within the system.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private int score;


    public User() {}

    /**
     * Admin User constructor
     *
     * @param username Username of the utility, the,
     * @param password User password.
     * @param email User's email address.
     * @param isAdmin Indicator for user administrator rights.
     */
    public User(String username, String password, String email, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * User constructor
     *
     * @param username Username of the utility, the,
     * @param password User password.
     * @param email User's email address.
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
