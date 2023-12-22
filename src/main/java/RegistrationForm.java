import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailFied;

    public RegistrationForm() {
        super("Inregistrare");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        emailFied = new JTextField(20);

        JButton registerButton = new JButton("Inregistrare");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();
                String email = emailFied.getText();

                if (!email.toLowerCase().endsWith("@e-uvt.ro")) {
                    JOptionPane.showMessageDialog(RegistrationForm.this, "Introduceti un email de forma @e-uvt.ro");
                    return;
                }

                User user = new User(username, new String(password), email);
                UserDao userDao = new UserDao();
                userDao.createUser(user);

                dispose();

                new LoginForm();
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Parola:"));
        panel.add(passwordField);
        panel.add(new JLabel("Email"));
        panel.add(emailFied);
        panel.add(registerButton);

        setLayout(new FlowLayout());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(panel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm());
    }
}
