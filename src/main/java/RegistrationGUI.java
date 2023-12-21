import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationGUI {

    private Autentificare autentificare;

    public RegistrationGUI(Autentificare autentificare) {
        this.autentificare = autentificare;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Registration GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JButton registerButton = new JButton("Register");

        panel.setBackground(new Color(240, 240, 240));
        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.WHITE);

        registerButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");

        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        emailLabel.setFont(labelFont);

        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        emailField.setFont(fieldFont);

        registerButton.setFont(labelFont);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                boolean result = autentificare.registerUser(username, password, email);
                if (result) {
                    JOptionPane.showMessageDialog(null, "Utilizator inregistrat cu succes!");
                } else {
                    JOptionPane.showMessageDialog(null, "Eroare la inregistrare.", "Eroare ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(registerButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Autentificare autentificare = new Autentificare();
        SwingUtilities.invokeLater(() -> new RegistrationGUI(autentificare));
    }
}
