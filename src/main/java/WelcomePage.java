import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * WelcomePage Class initializes the welcome window with the registration and authentication options.
 */
public class WelcomePage extends JFrame {

    /**
     * WelcomePage class contructor.
     */
    public WelcomePage() {
        super("Welcome");

        JLabel titleLabel = new JLabel("Choose one of the connection options:\n");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Log in");

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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

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
