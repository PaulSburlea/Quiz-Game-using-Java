import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Class RankingFrameAdmin is a window that displays the ranking of users for the administrator,
 * offering the option to change user scores.
 */
public class RankingFrameAdmin extends JFrame {

    private DefaultTableModel rankingTableModel;
    private final JTable rankingTable;

    /**
     * RankingFrameAdmin class constructor.
     *
     * @param ranking The list of users for which the ranking is displayed.
     */
    public RankingFrameAdmin(List<User> ranking) {
        super("Admin Leaderboard");

        rankingTableModel = new DefaultTableModel();
        rankingTableModel.addColumn("Place");
        rankingTableModel.addColumn("Player");
        rankingTableModel.addColumn("Score");

        rankingTable = new JTable(rankingTableModel);
        JScrollPane scrollPane = new JScrollPane(rankingTable);

        JButton modifyButton = new JButton("Modify score");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showModifyScoreDialog();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(modifyButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        loadRanking(ranking);
    }

    /**
     * Displays a dialog for changing the score of a selected user.
     */
    private void showModifyScoreDialog() {
        int selectRow = rankingTable.getSelectedRow();
        if (selectRow >= 0) {
            String username = rankingTable.getValueAt(selectRow, 1).toString();
            String currentScore = rankingTable.getValueAt(selectRow, 2).toString();

            try {
                User selectedUser = UserDao.getUserByUsername(username);

                if (selectedUser != null) {
                    String newScore = JOptionPane.showInputDialog(this, "Enter the new user score " + username, currentScore);

                    if (newScore != null && !newScore.isEmpty()) {
                        modifyUserScore(Integer.toString(selectedUser.getId()), newScore);
                        loadRanking(UserDao.getRanking());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "The user was not found in the database!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid user ID!");
            }
        }
    }


    /**
     * Upload your ranking to the table.
     *
     * @param ranking The list of users for whom the ranking is built.
     */
    private void loadRanking(List<User> ranking) {
        if (rankingTableModel == null) {
            rankingTableModel = new DefaultTableModel();
            rankingTableModel.addColumn("Place");
            rankingTableModel.addColumn("Player");
            rankingTableModel.addColumn("Score");
        }

        rankingTableModel.setRowCount(0);

        for (int i = 0; i < ranking.size(); i++) {
            User user = ranking.get(i);
            rankingTableModel.addRow(new Object[]{i + 1, user.getUsername(), user.getScore()});
        }
    }


    /**
     * Modifies a user's score in the database and reloads the ranking.
     *
     * @param userId the user ID for which the score is modified.
     * @param newScore The new user score.
     */
    private void modifyUserScore(String userId, String newScore) {
        try {
            int userIdInt = Integer.parseInt(userId);
            int newScoreInt = Integer.parseInt(newScore);


            if (userIdInt <= 0 || newScoreInt < 0) {
                JOptionPane.showMessageDialog(this, "User ID or incorrect score!");
                return;
            }

            UserDao.setScore(userIdInt, newScoreInt);

            loadRanking(UserDao.getRanking());

            JOptionPane.showMessageDialog(this, "The user score has been successfully changed!");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "User ID or incorrect score!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL error when changing user score: " + e.getMessage());
        }
    }


}
