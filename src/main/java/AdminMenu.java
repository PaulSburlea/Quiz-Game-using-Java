import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {

    private User currentUser;

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
                // TODO
                JOptionPane.showMessageDialog(AdminMenu.this, "Adauga intrebare...");
            }
        });

        deleteQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                JOptionPane.showMessageDialog(AdminMenu.this, "Sterge intrebare...");
            }
        });

        modifyRankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
                JOptionPane.showMessageDialog(AdminMenu.this, "Modifica clasament...");
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
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

        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(addQuestionButton);
        panel.add(deleteQuestionButton);
        panel.add(modifyRankingButton);
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
