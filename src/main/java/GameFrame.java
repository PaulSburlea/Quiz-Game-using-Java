import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.TreeMap;

/**
 * The GameFrame class is the window of the quiz game.
 */
public class GameFrame  extends JFrame {

    private final User currentUser;

    private final List<Question> questions;
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
     * Constructor for GameFrame class
     *
     * @param questions List of questions for the game.
     * @param currentUser Current user participating in the game.
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
     * Initialize the user interface for the game window.
     */
    private void setupUI() {
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        timerLabel = new JLabel("Time left: " + timeRemaining);
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
     * Initialize the timer for the game.
     */
    private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time left: " + timeRemaining);
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
     * Reset the timer for the next question.
     */
    private void resetTimer() {
        timeRemaining = 15;
        timerLabel.setText("Time left: " + timeRemaining);
    }

    /**
     * Displays the current question and answer options.
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
     * Check the answer to the current question.
     */
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int correctOption = currentQuestion.getCorrectOption();
        if (answerButtons[correctOption - 1].isSelected()) {
            score++;
        }
    }

    /**
     * Finish the game, stop the timer and display the score.
     */
    private void finishGame() {
        timer.stop();
        int totalScore = score * 10;
        try {
            UserDao.updateScore(currentUser.getId(), totalScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "The game is over! Your score is: " + totalScore);
        dispose();
    }

    /**
     * Deselect the answer options.
     */
    private void clearSelectedOptions() {
        answerGroup.clearSelection();
    }
}
