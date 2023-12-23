/**
 * Clasa User reprezinta o entitate care contine informatii despre un utilizator in cadrul sistemului.
 */
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private int score;

    /**
     * Constructor implicit al clasei User.
     */
    public User() {}

    /**
     * Constructor cu parametii al clasei User pentru un utilizator cu drepturi de administrator.
     *
     * @param username Numele de utilizator al utiliaztorului,
     * @param password Parola utilizatorului.
     * @param email Adresa de email al utilizatorului.
     * @param isAdmin Indicator pentru drepturile de administrator ale utilizatorului.
     */
    public User(String username, String password, String email, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * Constructor cu parametri al clasei User pentru un utilizator fara drepturi de administrator.
     *
     * @param username Numele de utilizator al utiliaztorului,
     * @param password Parola utilizatorului.
     * @param email Adresa de email al utilizatorului.
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
