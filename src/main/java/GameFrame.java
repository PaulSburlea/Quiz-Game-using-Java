import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

/**
 * Clasa GameFrame reprezinta fereastra jocului de tip quiz.
 */
public class GameFrame  extends JFrame {

    private User currentUser;

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    private JLabel questionLabel;
    private ButtonGroup answerGroup;
    private JRadioButton[] answerButtons;
    private JButton nextButton;
    private Timer timer;
    private int timeRemaining;
    private JLabel timerLabel;

    /**
     * Constructor pentru clasa GameFrame
     *
     * @param questions Lista cu intrebari pentru joc.
     * @param currentUser Utilizatorul curent care participa la joc.
     */
    public GameFrame(List<Question> questions, User currentUser) {
        super("Game");
        this.currentUser = currentUser;

        this.questions =questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timeRemaining = 15;

        setupUI();
        displayQuestion();
        setupTimer();

        setSize(700, 600);;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializeaza interfata utlizatorului pentru fereastra jocului.
     */
    private void setupUI() {
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        timerLabel = new JLabel("Timp ramas: " + timeRemaining);
        add(timerLabel, BorderLayout.WEST);

        answerGroup = new ButtonGroup();
        answerButtons = new JRadioButton[4];

        JPanel answerPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            answerButtons[i] = new JRadioButton();
            answerPanel.add(answerButtons[i]);
            answerGroup.add(answerButtons[i]);
        }

        add(answerPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    displayQuestion();
                    resetTimer();
                } else {
                    finishGame();
                }
            }
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(timerLabel, BorderLayout.WEST);
        bottomPanel.add(nextButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }


    /**
     * Initializeaza cronometrul pentru joc.
     */
    private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Timp ramas: " + timeRemaining);
                if (timeRemaining == 0) {
                    checkAnswer();
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        displayQuestion();
                        resetTimer();
                    } else {
                        finishGame();
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * Reseteaza cronomentrul pentru urmatoarea intrebare.
     */
    private void resetTimer() {
        timeRemaining = 15;
        timerLabel.setText("Timp ramas: " + timeRemaining);
    }

    /**
     * Afiseaza intrebarea curenta si optiunile de raspuns.
     */
    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getQuestionText());

        List<String> options = currentQuestion.getOptions();
        for (int i = 0; i < 4; i++) {
            answerButtons[i].setText(options.get(i));
        }
        clearSelectedOptions();
    }

    /**
     * Verifica raspunsul la intrebarea curenta.
     */
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int correctOption = currentQuestion.getCorrectOption();
        if (answerButtons[correctOption - 1].isSelected()) {
            score++;
        }
    }

    /**
     * Finalizeaza jocul, opreste cronometrul si afiseaza scorul.
     */
    private void finishGame() {
        timer.stop();
        int totalScore = score * 10;
        try {
            UserDao.updateScore(currentUser.getId(), totalScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Jocul s-a incheiat! Punctajul tau este: " + totalScore);
        dispose();
    }

    /**
     * Deselecteaza optiunile de raspuns.
     */
    private void clearSelectedOptions() {
        answerGroup.clearSelection();
    }
}
