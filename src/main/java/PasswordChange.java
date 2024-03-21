import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * PasswordChange class initializes a dialog window for changing the current user password.
 */
public class PasswordChange extends JDialog {

    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private User currentUser;

    /**
     * PasswordChange class constructor.
     *
     * @param parent Main window.
     * @param currentUser Current user to change password.
     */
    public PasswordChange(JFrame parent, User currentUser) {
        super(parent, "Change password", true);
        this.currentUser = currentUser;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        currentPasswordField = new JPasswordField(20);
        newPasswordField = new JPasswordField(20);
        confirmNewPasswordField = new JPasswordField(20);

        panel.add(new JLabel("Current password:"), gbc);
        gbc.gridy++;
        panel.add(currentPasswordField, gbc);
        gbc.gridy++;
        panel.add(new JLabel("New password:"), gbc);
        gbc.gridy++;
        panel.add(newPasswordField, gbc);
        gbc.gridy++;
        panel.add(new JLabel("Confirm new password:"), gbc);
        gbc.gridy++;
        panel.add(confirmNewPasswordField, gbc);

        JButton changePasswordButton = new JButton("Change password");

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

    /**
     * Method that checks and changes the current user password.
     */
    private void changePassword() {
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

        if (BCrypt.checkpw(currentPassword, currentUser.getPassword())) {
            if (newPassword.equals(confirmNewPassword)) {
                updatePassword(newPassword);
                JOptionPane.showMessageDialog(this, "Password changed successfully!");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "New passwords don't coincide!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Current password incorrect!");
        }
    }

    /**
     * method that updates the user's password in the database.
     *
     * @param newPassword New password.
     */
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
