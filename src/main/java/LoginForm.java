import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mindrot.jbcrypt.BCrypt;


/**
 * The LoginForm class initializes the user authentication window.
 */
public class LoginForm extends JFrame {

    private User currentUser;

    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructor for the LoginForm class.
     *
     * @param user Current user.
     */
    public LoginForm(User user) {
        this.currentUser = user;
        initialize();
    }

    /**
     * Constructor for the LoginForm class.
     */
    public LoginForm() {
        super("Log in");
        initialize();
    }

    /**
     * Initialize the user interface for the authentication window.
     */
    private void initialize() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Log in");
        loginButton.addActionListener(new ActionListener() {

            private void openUserMenu(User user) {
                if (user.isAdmin()) {
                    SwingUtilities.invokeLater(() -> new AdminMenu(user));
                } else {
                    SwingUtilities.invokeLater(() -> new UserMenu(user));
                }
            }
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                UserDao userDao = new UserDao();
                User user = userDao.getUserByUsername(username);

                if (user != null && BCrypt.checkpw(new String(password), user.getPassword())) {
                    dispose();
                    openUserMenu(user);
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Incorrect username or password!");
                }
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
        panel.add(usernameField, gbc);
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        panel.add(passwordField, gbc);
        gbc.gridy++;
        panel.add(loginButton, gbc);

        setLayout(new FlowLayout());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
