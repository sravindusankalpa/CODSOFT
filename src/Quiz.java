import java.util.*;

public class Quiz {
    private List<QuizQuestion> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;
    private Scanner scanner;

    public Quiz() {
        questions = new ArrayList<>();
        scanner = new Scanner(System.in);
        timer = new Timer();
        currentQuestionIndex = 0;
        score = 0;

        // Add quiz questions with options and correct answers
        questions.add(new QuizQuestion("What is the capital of France?",
                List.of("A. London", "B. Paris", "C. Berlin", "D. Rome"), 1));
        questions.add(new QuizQuestion("Which planet is known as the Red Planet?",
                List.of("A. Mars", "B. Jupiter", "C. Venus", "D. Saturn"), 0));
        // Add more questions as needed
    }

    public void startQuiz() {
        System.out.println("Welcome to the Quiz!");

        // Display questions one by one
        for (QuizQuestion question : questions) {
            displayQuestion(question);
        }

        // Display result screen
        displayResult();
    }

    private void displayQuestion(QuizQuestion question) {
        System.out.println("\n" + question.getQuestion());
        for (String option : question.getOptions()) {
            System.out.println(option);
        }

        // Set up timer for 1 minute
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Moving to the next question.");
                evaluateAnswer(-1, question); // Pass the question object as well
            }

        }, 60000); // 1 minute = 60,000 milliseconds

        System.out.print("Your answer (A, B, C, or D): ");
        String userAnswer = scanner.nextLine().toUpperCase();

        evaluateAnswer(convertAnswerToIndex(userAnswer), question);
    }

    private void evaluateAnswer(int userAnswerIndex, QuizQuestion question) {
        timer.cancel(); // Cancel the timer as the answer has been submitted

        if (userAnswerIndex == question.getCorrectOptionIndex()) {
            System.out.println("Correct!");
            score++;
        } else if (userAnswerIndex == -1) {
            System.out.println("No answer provided. Moving to the next question.");
        } else {
            System.out.println("Incorrect!");
        }
    }

    private int convertAnswerToIndex(String answer) {
        if (answer.equals("A")) return 0;
        if (answer.equals("B")) return 1;
        if (answer.equals("C")) return 2;
        if (answer.equals("D")) return 3;
        return -1; // Invalid answer
    }

    private void displayResult() {
        System.out.println("\nQuiz finished!");
        System.out.println("Your score: " + score + "/" + questions.size());

        // Calculate percentage
        double percentage = (double) score / questions.size() * 100;
        System.out.println("Percentage: " + percentage + "%");

        // Display correct and incorrect answers
        System.out.println("\nSummary:");
        for (int i = 0; i < questions.size(); i++) {
            QuizQuestion question = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + question.getQuestion());
            System.out.println("Correct Answer: " + question.getOptions().get(question.getCorrectOptionIndex()));
            if (!scanner.hasNextLine()) scanner.nextLine(); // Clear input buffer
        }

        scanner.close();
    }

    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.startQuiz();
    }
}