import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PasswordChange extends JDialog {

    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private User currentUser;

    public PasswordChange(JFrame parent, User currentUser) {
        super(parent, "Schimba parola", true);
        this.currentUser = currentUser;

        JPanel panel = new JPanel(new GridLayout(3, 2));

        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmNewPasswordField = new JPasswordField(20);

        panel.add(new JLabel("Parola curenta:"));
        panel.add(currentPasswordField);
        panel.add(new JLabel("Parola noua:"));
        panel.add(newPasswordField);
        panel.add(new JLabel("Confirma parola noua:"));
        panel.add((confirmNewPasswordField));

        JButton changePasswordButton = new JButton("Schimba parola");

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });

        setLayout(new FlowLayout());
        add(panel);
        add(changePasswordButton);

        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void changePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        if (BCrypt.checkpw(currentPassword, currentUser.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                updatePassword(newPassword);
                JOptionPane.showMessageDialog(this, "Parola schimbata cu succes!");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Noile parole nu coincid!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Parola curenta incorecta!");
        }
    }

    private void updatePassword(String newPassword) {
    try(Connection connection = Database.getConnection()) {
        String query = "UPDATE user SET password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(UserDao.LOG_ROUNDS));
            statement.setString(1, hashedPassword);
            statement.setInt(2, currentUser.getId());
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}
