import javax.swing.*;

/**
 * Clasa Main contine metoda principala pentru pornirea aplicatiei.
 */
public class Main {
    /**
     * Metoda principala care porneste aplicatia prin crearea unei instante a clasei WelcomePage.
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}