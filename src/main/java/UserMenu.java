import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * UserMenu class initializes menu window for user.
 */
public class UserMenu extends JFrame {

    private final User currentUser;

    /**
     * UserMenu constructor
     *
     * @param user Current user for menu.
     */
    public UserMenu(User user) {
        super("User Menu");

        this.currentUser = user;

        JButton startGameButton = new JButton("Start Game");
        JButton viewRankingButton = new JButton("Leaderboard");
        JButton changePasswordButton = new JButton("Change Password");
        JButton logoutButton = new JButton("Logout");

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Question> randomQuestions = QuestionManager.getRandomQuestions(10);
                new GameFrame(randomQuestions, currentUser);
            }
        });

        viewRankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> ranking = UserDao.getRanking();
                new RankingFrame(ranking);
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        Dimension buttonSize = new Dimension(200, 50);

        addButton(startGameButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(viewRankingButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(changePasswordButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(logoutButton, panel, gbc, buttonSize);

        add(panel, BorderLayout.CENTER);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButton(JButton button, JPanel panel, GridBagConstraints gbc, Dimension size) {
        button.setPreferredSize(size);
        panel.add(button, gbc);
    }
}
