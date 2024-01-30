import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa WelcomePage reprezinta fereastra de bun venit cu optinile de inregistrare si autentificare.
 */
public class WelcomePage extends JFrame {

    /**
     * Constructorul clasei WelcomePage.
     */
    public WelcomePage() {
        super("Bine ati venit!");

        JLabel titleLabel = new JLabel("Alegeti una dintre optiunile de conectare:\n");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton registerButton = new JButton("Inregistrare");
        JButton loginButton = new JButton("Autentificare");

        int maxButtonWidth = 200;

        registerButton.setMaximumSize(new Dimension(maxButtonWidth, Short.MAX_VALUE));
        loginButton.setMaximumSize(new Dimension(maxButtonWidth, Short.MAX_VALUE));

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegistrationForm();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm();
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Adăugăm o margine între butoane
        gbc.fill = GridBagConstraints.BOTH; // Asigurăm că butoanele se extind pe ambele axe

        // Adăugăm butoanele în panou
        buttonPanel.add(titleLabel, gbc);
        gbc.gridy++;
        buttonPanel.add(registerButton, gbc);
        gbc.gridy++;
        buttonPanel.add(loginButton, gbc);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
