import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Clasa RankingFrame reprezinta o fereastra care afiseaza clasamentul utilizatorilor.
 */
public class RankingFrame extends JFrame {

    /**
     * Constructorul clasei RankingFrame.
     *
     * @param ranking Lista de utilizatori pentru care se afiseaza clasamentul.
     */
    public RankingFrame(List<User> ranking) {
        super("Clasament");

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
     * Creeaza si returneaza un obiect JTable cu datele clasamentului.
     *
     * @param ranking Lista de utilizatori pentru care se construieste clasamentul.
     * @return Obiectul JTable care contine clasamentul.
     */
    private JTable createRankingTable(List<User> ranking) {
        String[] columnName = {"Loc", "Jucator", "Scor"};

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
