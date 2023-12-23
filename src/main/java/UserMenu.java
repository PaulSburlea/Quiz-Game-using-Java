import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clasa UserMenu reprezinta fereastra meniului pentru utilizator.
 */
public class UserMenu extends JFrame {

    private final User currentUser;

    /**
     * Constructorul clasei.
     *
     * @param user Utilizatorul curent pentru meniu.
     */
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
