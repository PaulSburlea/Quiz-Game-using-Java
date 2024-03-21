import javax.swing.*;

/**
 * Main Class contains the main method for starting the application.
 */
public class Main {
    /**
     * The main method that starts the application by creating an instance of the WelcomePage class.
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}