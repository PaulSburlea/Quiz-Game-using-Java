import java.util.ArrayList;
import java.util.List;

/**
 * Clasa Question reprezinta o intrebare dintr-un joc de tip quiz.
 */
public class Question {

    private int questionId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctOption;


    /**
     * Constructorul clasei Question.
     *
     * @param questionId ID-ul intrebarii.
     * @param questionText Texul intrebarii.
     * @param option1 Optiunea 1.
     * @param option2 Optiunea 2.
     * @param option3 Optiunea 3.
     * @param option4 Optiunea 4.
     * @param correctOption Varianta corecta (1-4).
     */
    public Question(int questionId, String questionText, String option1, String option2, String option3, String option4, int correctOption) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctOption = correctOption;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    /**
     * Obtine o lista de optiuni.
     *
     * @return Lista de optiuni.
     */
    public List<String> getOptions() {
        List<String> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);
        return options;
    }
}
