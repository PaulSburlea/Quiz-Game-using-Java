import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

/**
 * Clasa RankingFrameAdmin reprezinta o fereastra care afiseaza clasamentul utilizatorilor pentru administrator,
 * oferind optiunea de a modifca scorurile utilizatorilor.
 */
public class RankingFrameAdmin extends JFrame {

    private DefaultTableModel rankingTableModel;
    private final JTable rankingTable;

    /**
     * Constructorul clasei RankingFrameAdmin.
     *
     * @param ranking Lista de utilizatori pentru care se afiseaza clasamentul.
     */
    public RankingFrameAdmin(List<User> ranking) {
        super("Clasament Admin");

        rankingTableModel = new DefaultTableModel();
        rankingTableModel.addColumn("Loc");
        rankingTableModel.addColumn("Jucator");
        rankingTableModel.addColumn("Scor");

        rankingTable = new JTable(rankingTableModel);
        JScrollPane scrollPane = new JScrollPane(rankingTable);

        JButton modifyButton = new JButton("Modifica scor");
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
     * Afiseaza un dialog pentru modificarea scorului unui utilizator selectat.
     */
    private void showModifyScoreDialog() {
        int selectRow = rankingTable.getSelectedRow();
        if (selectRow >= 0) {
            String username = rankingTable.getValueAt(selectRow, 1).toString();
            String currentScore = rankingTable.getValueAt(selectRow, 2).toString();

            try {
                User selectedUser = UserDao.getUserByUsername(username);

                if (selectedUser != null) {
                    String newScore = JOptionPane.showInputDialog(this, "Introduceti noul scor pentru userul " + username, currentScore);

                    if (newScore != null && !newScore.isEmpty()) {
                        modifyUserScore(Integer.toString(selectedUser.getId()), newScore);
                        loadRanking(UserDao.getRanking());
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Utilizatorul nu a fost gasit în baza de date!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID utilizator invalid!");
            }
        }
    }


    /**
     * Incarca clasamentul in tabel.
     *
     * @param ranking Lista de utilizatori pentru care se construieste clasamentul.
     */
    private void loadRanking(List<User> ranking) {
        if (rankingTableModel == null) {
            rankingTableModel = new DefaultTableModel();
            rankingTableModel.addColumn("Loc");
            rankingTableModel.addColumn("Jucator");
            rankingTableModel.addColumn("Scor");
        }

        rankingTableModel.setRowCount(0);

        for (int i = 0; i < ranking.size(); i++) {
            User user = ranking.get(i);
            rankingTableModel.addRow(new Object[]{i + 1, user.getUsername(), user.getScore()});
        }
    }


    /**
     * Modifica scorul unui utilizator in baza de date si reincarca clasamentul.
     *
     * @param userId ID-ul utilizatorului pentru care se modifica scorul.
     * @param newScore Noul scor al utlizatorului.
     */
    private void modifyUserScore(String userId, String newScore) {
        try {
            int userIdInt = Integer.parseInt(userId);
            int newScoreInt = Integer.parseInt(newScore);


            if (userIdInt <= 0 || newScoreInt < 0) {
                JOptionPane.showMessageDialog(this, "ID utilizator sau scor incorect!");
                return;
            }

            UserDao.setScore(userIdInt, newScoreInt);

            loadRanking(UserDao.getRanking());

            JOptionPane.showMessageDialog(this, "Scorul utilizatorului a fost modificat cu succes!");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "ID utilizator sau scor incorect!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Eroare SQL la modificarea scorului utilizatorului: " + e.getMessage());
        }
    }


}
