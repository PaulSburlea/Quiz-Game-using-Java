import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The RankingFrame class is a window that displays the ranking of users.
 */
public class RankingFrame extends JFrame {

    /**
     * RankingFrame class constructor.
     *
     * @param ranking The list of users for whom the ranking is displayed.
     */
    public RankingFrame(List<User> ranking) {
        super("Leaderboard");

        JTable table = createRankingTable(ranking);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Creates and returns a JTable object with the ranking data.
     *
     * @param ranking The list of users for whom the ranking is built.
     * @return The JTable item that contains the ranking.
     */
    private JTable createRankingTable(List<User> ranking) {
        String[] columnName = {"Place", "Player", "Score"};

        Object[][] data = new Object[ranking.size()][3];
        for (int i=0; i<ranking.size(); i++) {
            User user = ranking.get(i);
            data[i][0] = i+1;
            data[i][1] = user.getUsername();
            data[i][2] = user.getScore();
        }
        return new JTable(data, columnName);
    }
}
