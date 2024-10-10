import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.Timer;

public class WordPuzzleGame extends JFrame {
    private ArrayList<String> wordList;
    private String currentWord;
    private String jumbledWord;
    private JTextField guessField;
    private JLabel wordLabel, timerLabel, resultLabel;
    private JButton submitButton, startButton;
    private Timer timer;
    private int timeRemaining = 30;
    private int points = 0; // Variable to track points

    public WordPuzzleGame() {
        // Initialize word list
        wordList = new ArrayList<>();
        wordList.add("JAVA");
        wordList.add("SWING");
        wordList.add("PUZZLE");
        wordList.add("GAME");
        wordList.add("RANDOM");
        wordList.add("SUPER");
        wordList.add("DELHI");
        wordList.add("PANJIM");
        wordList.add("RITZ CLASSIC");
        wordList.add("TEMPLE");

        // Setup the frame
        setTitle("Word Puzzle Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // GUI Components
        wordLabel = new JLabel("Word: ");
        timerLabel = new JLabel("Time: 30s");
        resultLabel = new JLabel("");
        guessField = new JTextField(10);
        submitButton = new JButton("Submit");
        startButton = new JButton("Start Game");

        // Add components to the frame
        add(wordLabel);
        add(timerLabel);
        add(new JLabel("Your Guess:"));
        add(guessField);
        add(submitButton);
        add(startButton);
        add(resultLabel);

        // Disable input and buttons until the game starts
        guessField.setEnabled(false);
        submitButton.setEnabled(false);

        // Action listener for Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        // Action listener for Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        setVisible(true);
    }

    private void startGame() {
        // Reset timer, points, and labels
        timeRemaining = 30;
        points = 0;
        timerLabel.setText("Time: 30s");
        resultLabel.setText("");

        // Enable input and button
        guessField.setEnabled(true);
        submitButton.setEnabled(true);

        // Choose a random word and jumble it
        chooseRandomWord();

        // Update the word label with the jumbled word
        wordLabel.setText("Word: " + jumbledWord);

        // Start the timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time: " + timeRemaining + "s");
                if (timeRemaining <= 0) {
                    timer.stop();
                    endGame(false);
                }
            }
        });
        timer.start();
    }

    private void chooseRandomWord() {
        Random rand = new Random();
        currentWord = wordList.get(rand.nextInt(wordList.size()));
        jumbledWord = jumbleWord(currentWord);
    }

    private String jumbleWord(String word) {
        ArrayList<Character> characters = new ArrayList<>();
        for (char c : word.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder jumbled = new StringBuilder();
        for (char c : characters) {
            jumbled.append(c);
        }
        return jumbled.toString();
    }

    private void checkGuess() {
        String guess = guessField.getText().toUpperCase();
        if (guess.equals(currentWord)) {
            timer.stop();
            calculatePoints(); // Calculate points based on time
            endGame(true);
        } else {
            resultLabel.setText("Incorrect! Try again.");
        }
    }

    // points based on the time remaining
    private void calculatePoints() {
        int timeTaken = 30 - timeRemaining; // Time taken to guess
        if (timeTaken <= 5) {
            points = 20;
        } else if (timeTaken <= 10) {
            points = 10;
        } else if (timeTaken <= 20) {
            points = 5;
        } else if (timeTaken <= 25) {
            points = 1;
        } else if (timeTaken == 30) {
            points = 0;
        }
    }

    private void endGame(boolean won) {
        guessField.setEnabled(false);
        submitButton.setEnabled(false);
        if (won) {
            resultLabel.setText("Congratulations! You guessed it right. You got " + points+" points");
        } else {
            resultLabel.setText("Time's up! The word was: " +currentWord+ ". You got "+points+" points");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordPuzzleGame());
    }
}
