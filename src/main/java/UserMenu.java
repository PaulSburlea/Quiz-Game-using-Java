import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JFrame {

    private User currentUser;

    public UserMenu(User user) {
        super("Meniu Utilizator");

        this.currentUser = user;

        JButton startGameButton = new JButton("Incepe joc");
        JButton viewRankingButton = new JButton("Vezi clasament");
        JButton changePasswordButton = new JButton("Schimba parola");
        JButton logoutButton = new JButton("Deconectare");

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                JOptionPane.showMessageDialog(UserMenu.this, "Jocul a inceput!");
            }
        });

        viewRankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                JOptionPane.showMessageDialog(UserMenu.this, "Vezi clasament...");
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                if (currentUser != null) {
                    new PasswordChange(UserMenu.this, currentUser);
                } else {
                    JOptionPane.showMessageDialog(UserMenu.this, "User invalid");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginForm();
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(startGameButton);
        panel.add(viewRankingButton);
        panel.add(changePasswordButton);
        panel.add(logoutButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
