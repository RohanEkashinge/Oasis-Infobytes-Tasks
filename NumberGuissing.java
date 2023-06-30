import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class NumberGuissing implements ActionListener {
    private JFrame frame;
    private JButton startBtn;
    private int minRange = 1;
    private int maxRange = 100;
    private int maxAttempts = 5;
    private int score = 0;
    private int rounds = 0;

    public NumberGuissing() {
        frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        startBtn = new JButton("Start Game");
        startBtn.addActionListener(this);

        frame.add(startBtn);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == startBtn) {
            startGame();
        }
    }

    private void startGame() {
        rounds = 0;
        score = 0;
        JOptionPane.showMessageDialog(null, "Welcome to the Number Guessing Game!");

        do {
            rounds++;
            int targetNumber = generateRandomNumber(minRange, maxRange);
            int attempts = 0;

            JOptionPane.showMessageDialog(null, "Round " + rounds + "\nGuess the number between "
                    + minRange + " and " + maxRange + "!");

            while (true) {
                String guessString = JOptionPane.showInputDialog(null, "Enter your guess:");

                if (guessString == null) {
                    JOptionPane.showMessageDialog(null, "Game canceled. Thanks for playing!");
                    return;
                }

                try {
                    int guess = Integer.parseInt(guessString);
                    attempts++;

                    if (guess < targetNumber) {
                        JOptionPane.showMessageDialog(null, "Lower! Try again.");
                    } else if (guess > targetNumber) {
                        JOptionPane.showMessageDialog(null, "Higher! Try again.");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Congratulations! You guessed the number in " + attempts + " attempts.");
                        score += calculateScore(maxAttempts, attempts);
                        break;
                    }

                    if (attempts >= maxAttempts) {
                        JOptionPane.showMessageDialog(null,
                                "Out of attempts! The number was " + targetNumber + ".");
                        break;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid number.");
                }
            }

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again",
                    JOptionPane.YES_NO_OPTION);
            if (choice != JOptionPane.YES_OPTION) {
                break;
            }
        } while (true);

        JOptionPane.showMessageDialog(null, "Game Over!\nTotal Rounds: " + rounds + "\nFinal Score: " + score);
    }

    private int generateRandomNumber(int minRange, int maxRange) {
        Random random = new Random();
        return random.nextInt(maxRange - minRange + 1) + minRange;
    }

    private int calculateScore(int maxAttempts, int attempts) {
        int score = (maxAttempts - attempts + 1) * 10;
        return score > 0 ? score : 0;
    }

    public static void main(String[] args) {
       NumberGuissing obj = new NumberGuissing();
    }
}
