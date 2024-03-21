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
 * The AdminMenu class represents the graphical interface for the administrator.
 * This class offers functionalities such as adding and deleting questions,
 * changing the ranking and changing the password.
 */

public class AdminMenu extends JFrame {

    private User currentUser;
    private DefaultTableModel rankingTableModel;
    private JTable rankingTable;

    /**
     * @param user The admin user for which the menu opens.
     */
    public AdminMenu(User user) {
        super("Admin Main Page");
        this.currentUser = user;

        JButton addQuestionButton = new JButton("Add question");
        JButton deleteQuestionButton = new JButton("Delete question");
        JButton modifyRankingButton = new JButton("Modify ranking");
        JButton changePasswordButton = new JButton("Change password");
        JButton logoutButton = new JButton("Log out");


        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame addQuestionFrame = new JFrame("Add question");
                JPanel addQuestionPanel = new JPanel(new GridLayout(6, 2));

                JTextField questionText = new JTextField();
                JTextField option1 = new JTextField();
                JTextField option2 = new JTextField();
                JTextField option3 = new JTextField();
                JTextField option4 = new JTextField();
                JTextField correctOption = new JTextField();

                addQuestionPanel.add(new JLabel("Question:"));
                addQuestionPanel.add(questionText);
                addQuestionPanel.add(new JLabel("Option 1:"));
                addQuestionPanel.add(option1);
                addQuestionPanel.add(new JLabel("Option 2:"));
                addQuestionPanel.add(option2);
                addQuestionPanel.add(new JLabel("Option 3:"));
                addQuestionPanel.add(option3);
                addQuestionPanel.add(new JLabel("Option 4:"));
                addQuestionPanel.add(option4);
                addQuestionPanel.add(new JLabel("Correct oprion (1-4):"));
                addQuestionPanel.add(correctOption);

                int result = JOptionPane.showConfirmDialog(AdminMenu.this, addQuestionPanel, "Add question",
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
                    JOptionPane.showMessageDialog(AdminMenu.this, "Invalid user!");
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

        addButton(addQuestionButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(deleteQuestionButton, panel, gbc, buttonSize);
        gbc.gridy++;
        addButton(modifyRankingButton, panel, gbc, buttonSize);
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


    /**
     * Add a question to the database.
     * @param questionText The question.
     * @param option1 Option 1.
     * @param option2 Option 2.
     * @param option3 Option 3.
     * @param option4 Option 4.
     * @param correctOption The correct variant (1-4).
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
                    JOptionPane.showMessageDialog(AdminMenu.this, "Question successfully added!");
                } else {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Error adding the question!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(AdminMenu.this, "Error connecting to the database!");
        }
    }


    /**
     * Displays a dialog for deleting a question.
     */
    private void showDeleteQuestionDialog() {
        JFrame deleteQuestionFrame = new JFrame("Delete question");
        JPanel deleteQuestionPanel = new JPanel(new BorderLayout());

        JTextArea questionListArea = new JTextArea();
        questionListArea.setEditable(false);
        questionListArea.setText(getQuestionList());
        questionListArea.setRows(10);
        questionListArea.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(questionListArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        deleteQuestionPanel.add(new JLabel("List of questions:\n"), BorderLayout.NORTH);
        deleteQuestionPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JTextField deleteQuestionNumberField = new JTextField();
        inputPanel.add(new JLabel("Enter the question id to be deleted:"), BorderLayout.NORTH);
        inputPanel.add(deleteQuestionNumberField, BorderLayout.CENTER);

        deleteQuestionPanel.add(inputPanel, BorderLayout.SOUTH);

        int result = JOptionPane.showConfirmDialog(
                AdminMenu.this,
                deleteQuestionPanel,
                "Delete question",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            deleteQuestion(deleteQuestionNumberField.getText());
        }
    }


    /**
     * Build a list of questions to display
     * @return The string representing the list of questions.
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
            JOptionPane.showMessageDialog(AdminMenu.this, "Error connecting to the database!");
        }
        return questionList.toString();
    }


    /**
     * Delete a question in the database.
     * @param questionNumber Question ID to delete.
     */
    private void deleteQuestion(String questionNumber) {
        try {
            Connection connection = Database.getConnection();
            String query = "DELETE FROM questions WHERE question_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(questionNumber));

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Question successfully deleted!");
                } else {
                    JOptionPane.showMessageDialog(AdminMenu.this, "Error deleting the question!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(AdminMenu.this, "Error connecting to the database!");
        }
    }


    /**
     * The method that loads the ranking of users and displays it.
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
