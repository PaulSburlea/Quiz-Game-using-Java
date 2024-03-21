import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The RegistrationForm class represents a user registration window in the system.
 */
public class RegistrationForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailFied;

    /**
     * The constructor of the RegistrationForm class.
     */
    public RegistrationForm() {
        super("Registration");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailFied = new JTextField(20);

        JButton registerButton = new JButton("Registration");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String email = emailFied.getText();

                if (!email.toLowerCase().endsWith("@e-uvt.ro")) {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Enter an email of the form @e-uvt.ro");
                    return;
                }

                User user = new User(username, new String(password), email);
                UserDao userDao = new UserDao();
                userDao.createUser(user);

                dispose();

                new LoginForm();
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("E-mail"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy++;
        panel.add(emailFied, gbc);
        gbc.gridy++;
        panel.add(registerButton, gbc);

        setLayout(new FlowLayout());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
