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

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Adăugăm o margine între butoane
        gbc.fill = GridBagConstraints.BOTH; // Asigurăm că butoanele se extind pe ambele axe

        // Dimensiunea preferată a butoanelor
        Dimension buttonSize = new Dimension(200, 50);

        // Adăugăm butoanele în panou
        addButton(startGameButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(viewRankingButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(changePasswordButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(logoutButton, panel, gbc, buttonSize);

        // Centrăm panoul în cadrul ferestrei
        add(panel, BorderLayout.CENTER);

        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButton(JButton button, JPanel panel, GridBagConstraints gbc, Dimension size) {
        button.setPreferredSize(size); // Setăm dimensiunea preferată a butonului
        panel.add(button, gbc);
    }
}
