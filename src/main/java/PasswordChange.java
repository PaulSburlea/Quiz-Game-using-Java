import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clasa PasswordChange reprezinta o fereastra de dialog pentru schimbarea parolei utilizatorului curent.
 */
public class PasswordChange extends JDialog {

    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmNewPasswordField;
    private User currentUser;

    /**
     * Constructorul clasei PasswordChange.
     *
     * @param parent Fereastra principala parinte.
     * @param currentUser Utilizatorul curent pentru schimbarea parolei.
     */
    public PasswordChange(JFrame parent, User currentUser) {
        super(parent, "Schimba parola", true);
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

        panel.add(new JLabel("Parola curenta:"), gbc);
        gbc.gridy++;
        panel.add(currentPasswordField, gbc);
        gbc.gridy++;
        panel.add(new JLabel("Parola noua:"), gbc);
        gbc.gridy++;
        panel.add(newPasswordField, gbc);
        gbc.gridy++;
        panel.add(new JLabel("Confirma parola noua:"), gbc);
        gbc.gridy++;
        panel.add(confirmNewPasswordField, gbc);

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

    /**
     * Metoda care verifica si schimba parola utilizatorului curent.
     */
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

    /**
     * metoda care actualizeaza parola utilizatorului in baza de date.
     *
     * @param newPassword Parola noua.
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
