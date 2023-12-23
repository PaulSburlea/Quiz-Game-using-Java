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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(titleLabel);
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(buttonPanel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setLocationRelativeTo(null);
        setVisible(true);
    }
}
