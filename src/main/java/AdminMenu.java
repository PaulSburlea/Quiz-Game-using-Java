import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.NumericShaper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *  Clasa AdminMenu reprezinta interfata grafica pentru administrator.
 *  Aceasta clasa ofera functionalitati precum adaugarea si stergerea de intrebari,
 *  modificarea clasamentului si schimbarea parolei.
 */

public class AdminMenu extends JFrame {

    private User currentUser;
    private DefaultTableModel rankingTableModel;
    private JTable rankingTable;

    /**
     * @param user Utilizatorul administrator pentru care se deschide meniul.
     */
    public AdminMenu(User user) {
        super("Pagina Principala Admin");
        this.currentUser = user;

        JButton addQuestionButton = new JButton("Adauga intrebare");
        JButton deleteQuestionButton = new JButton("Sterge intrebare");
        JButton modifyRankingButton = new JButton("Modifica clasament");
        JButton changePasswordButton = new JButton("Schimba parola");
        JButton logoutButton = new JButton("Deconectare");


        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame addQuestionFrame = new JFrame("Adauga intrebare");
                JPanel addQuestionPanel = new JPanel(new GridLayout(6, 2));

                JTextField questionText = new JTextField();
                JTextField option1 = new JTextField();
                JTextField option2 = new JTextField();
                JTextField option3 = new JTextField();
                JTextField option4 = new JTextField();
                JTextField correctOption = new JTextField();

                addQuestionPanel.add(new JLabel("Intrebare:"));
                addQuestionPanel.add(questionText);
                addQuestionPanel.add(new JLabel("Optiune 1:"));
                addQuestionPanel.add(option1);
                addQuestionPanel.add(new JLabel("Optiune 2:"));
                addQuestionPanel.add(option2);
                addQuestionPanel.add(new JLabel("Optiune 3:"));
                addQuestionPanel.add(option3);
                addQuestionPanel.add(new JLabel("Optiune 4:"));
                addQuestionPanel.add(option4);
                addQuestionPanel.add(new JLabel("Varianta corecta (1-4):"));
                addQuestionPanel.add(correctOption);

                int result = JOptionPane.showConfirmDialog(AdminMenu.this, addQuestionPanel, "Adauga intrebare",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    addQuestion(
                            questionText.getText(),
                            option1.getText(),
                            option2.getText(),
                            option3.getText(),
                            option4.getText(),
                            Integer.parseInt(correctOption.getText())
                    );
                }


            }
        });

        deleteQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDeleteQuestionDialog();
            }
        });

        modifyRankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<User> ranking = UserDao.getRanking();
                new RankingFrameAdmin(ranking);
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    new PasswordChange(AdminMenu.this, currentUser);
                } else {
                    JOptionPane.showMessageDialog(AdminMenu.this, "User invalid!");
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
        addButton(addQuestionButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(deleteQuestionButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(modifyRankingButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(changePasswordButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(logoutButton, panel, gbc, buttonSize);

// Centrăm panoul în cadrul ferestrei
        add(panel, BorderLayout.CENTER);

// Restul codului rămâne neschimbat
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    private void addButton(JButton button, JPanel panel, GridBagConstraints gbc, Dimension size) {
        button.setPreferredSize(size); // Setăm dimensiunea preferată a butonului
        panel.add(button, gbc);
    }


    /**
     * Adauga o intrebare in baza de date.
     * @param questionText Intrebarea.
     * @param option1 Optiunea 1.
     * @param option2 Optiunea 2.
     * @param option3 Optiunea 3.
     * @param option4 Optiunea 4.
     * @param correctOption Varianta corecta (1-4).
     */
    private void addQuestion(String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        try {
            Connection connection = Database.getConnection();
            String query = "INSERT INTO questions (question_text, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, questionText);
                preparedStatement.setString(2, option1);
                preparedStatement.setString(3, option2);
                preparedStatement.setString(4, option3);
                preparedStatement.setString(5, option4);
                preparedStatement.setInt(6, correctOption);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Intrebare adaugata cu succes!");
                } else {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Eroare la adaugarea intrebarii!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(AdminMenu.this, "Eroare la conectarea la baza de date!");
        }
    }


    /**
     * Afiseaza un dialog pentru stergerea unei intrebari.
     */
    private void showDeleteQuestionDialog() {
        JFrame deleteQuestionFrame = new JFrame("Sterge intrebare");
        JPanel deleteQuestionPanel = new JPanel(new BorderLayout());

        JTextArea questionListArea = new JTextArea();
        questionListArea.setEditable(false);
        questionListArea.setText(getQuestionList());
        questionListArea.setRows(10);
        questionListArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(questionListArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        deleteQuestionPanel.add(new JLabel("Lista intrebarilor:\n"), BorderLayout.NORTH);
        deleteQuestionPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField deleteQuestionNumberField = new JTextField();
        inputPanel.add(new JLabel("Introduceti id-ul intrebarii de sters:"), BorderLayout.NORTH);
        inputPanel.add(deleteQuestionNumberField, BorderLayout.CENTER);

        deleteQuestionPanel.add(inputPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                AdminMenu.this,
                deleteQuestionPanel,
                "Sterge intrebare",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            deleteQuestion(deleteQuestionNumberField.getText());
        }
    }


    /**
     * Construieste o lista de intrebari pentru afisare
     * @return Sirul de caractere reprezentand lista de intrebari.
     */
    private String getQuestionList() {
        StringBuilder questionList = new StringBuilder();
        try {
            Connection connection = Database.getConnection();
            String query = "SELECT question_id, question_text FROM questions";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                {

                    while (resultSet.next()) {
                        int questionId = resultSet.getInt("question_id");
                        String questionText = resultSet.getString("question_text");
                        questionList.append(questionId).append(". ").append(questionText).append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(AdminMenu.this, "Eroare la conectarea la baza de date!");
        }
        return questionList.toString();
    }


    /**
     * Sterge o intrebare dn baza de date.
     * @param questionNumber ID-ul intrebarii de sters.
     */
    private void deleteQuestion(String questionNumber) {
        try {
            Connection connection = Database.getConnection();
            String query = "DELETE FROM questions WHERE question_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(questionNumber));

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Intrebare stearsa cu succes!");
                } else {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Eroare la stergerea intrebarii!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(AdminMenu.this, "Eroare la conectarea la baza de date!");
        }
    }


    /**
     * Incarca clasamentul utilizatorilor si il afiseaza.
     */
    private void loadRanking() {
        List<User> ranking = UserDao.getRanking();

        rankingTableModel.setRowCount(0);;

        for (int i=0; i<ranking.size(); i++) {
            User user = ranking.get(i);
            rankingTableModel.addRow(new Object[]{i+1, user.getUsername(), user.getScore()});
        }
    }


}
