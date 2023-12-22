import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mindrot.jbcrypt.BCrypt;


public class LoginForm extends JFrame {

    private User currentUser;

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm(User user) {
        this.currentUser = user;
        initialize();
    }

    public LoginForm() {
        super("Autentificare");
        initialize();
    }

    private void initialize() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Autentificare");
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
                    JOptionPane.showMessageDialog(LoginForm.this, "Username sau parola incorecte!");
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("Username"));
        panel.add(usernameField);
        panel.add(new JLabel("Parola"));
        panel.add(passwordField);
        panel.add(loginButton);

        setLayout(new FlowLayout());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User defaultUser = userDao.getUserByUsername("defaultUser");
        SwingUtilities.invokeLater(() -> new LoginForm(defaultUser));
    }
}