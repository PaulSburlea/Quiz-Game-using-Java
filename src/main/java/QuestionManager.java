import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The QuestionManager class provides functionality for managing questions in the database.
 */
public class QuestionManager {

    /**
     * Returns a list of random questions from the database.
     *
     * @param numberOfQuestions Number of questions you want.
     * @return List of random questions.
     */
    public static List<Question> getRandomQuestions(int numberOfQuestions) {
        List<Question> questions = new ArrayList<>();

        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM questions ORDER BY RAND() LIMIT ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, numberOfQuestions);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    int questionId = resultSet.getInt("question_id");
                    String questionText = resultSet.getString("question_text");
                    String option1 = resultSet.getString("option1");
                    String option2 = resultSet.getString("option2");
                    String option3 = resultSet.getString("option3");
                    String option4 = resultSet.getString("option4");
                    int correctOption = resultSet.getInt("correct_option");

                    Question question = new Question(questionId, questionText, option1, option2, option3, option4, correctOption);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questions);

        return questions;
    }
}
