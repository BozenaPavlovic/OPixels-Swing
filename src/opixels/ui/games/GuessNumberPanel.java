package opixels.ui.games;

import opixels.ui.MainFrame;
import opixels.ui.Screen;
import opixels.ui.ScreenNames;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

public class GuessNumberPanel extends JPanel implements Screen {

    private final MainFrame mainFrame;
    private final JTextArea resultArea = new JTextArea(10, 24);
    private final Random random = new Random();
    private final JButton[] numberButtons = new JButton[10];

    private int secretNumber;
    private boolean finished;
    private boolean gameStarted;
    private int totalAttempts;
    private int wins;

    public GuessNumberPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(140, 36));
        startButton.addActionListener(e -> startRound());

        JPanel left = new JPanel(new GridLayout(6, 1, 0, 10));
        left.add(new JLabel("Guess the Number"));
        left.add(new JLabel("Odaberi broj (1-10):"));

        JPanel numberPanel = new JPanel(new GridLayout(2, 5, 5, 5));
        for (int i = 0; i < 10; i++) {
            int number = i + 1;
            numberButtons[i] = new JButton(String.valueOf(number));
            numberButtons[i].setPreferredSize(new Dimension(50, 40));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 14));
            numberButtons[i].setEnabled(false);
            numberButtons[i].addActionListener(e -> checkGuess(number));
            numberPanel.add(numberButtons[i]);
        }
        left.add(numberPanel);

        left.add(startButton);
        left.add(new JLabel(" "));

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Rezultat"), BorderLayout.NORTH);
        right.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);

        totalAttempts = 0;
        wins = 0;
        gameStarted = false;
        finished = true;
        for (JButton button : numberButtons) {
            button.setEnabled(false);
        }
        resultArea.setText("Pritisnite 'Start' za novu igru.\n\nRacunalo:\n-\n\nRezultat:\n-");
    }

    private void startRound() {
        secretNumber = random.nextInt(10) + 1;
        finished = false;
        gameStarted = true;

        for (JButton button : numberButtons) {
            button.setEnabled(true);
        }

        resultArea.setText("Pogodite broj od 1 do 10.\n\n"
                + "Racunalo:\n-\n\n"
                + "Rezultat:\n-"
                + "\n\nUkupno igara: " + totalAttempts
                + "\nPobjede: " + wins);
    }

    private void checkGuess(int guess) {
        if (finished || !gameStarted) {
            return;
        }

        finished = true;
        gameStarted = false;
        totalAttempts++;

        for (JButton button : numberButtons) {
            button.setEnabled(false);
        }

        if (guess == secretNumber) {
            wins++;
            resultArea.setText(
                    "Pogodili ste broj " + secretNumber + ".\n\n"
                            + "Racunalo:\n" + secretNumber + "\n\n"
                            + "Rezultat:\nPOBJEDA"
                            + "\n\nUkupno igara: " + totalAttempts
                            + "\nPobjede: " + wins
            );
            mainFrame.getDataManager().recordGuessNumber(true, totalAttempts);
        } else {
            resultArea.setText(
                    "Niste pogodili broj " + secretNumber + ".\n\n"
                            + "Racunalo:\n" + secretNumber + "\n\n"
                            + "Rezultat:\nIZGUBILI"
                            + "\n\nUkupno igara: " + totalAttempts
                            + "\nPobjede: " + wins
            );
            mainFrame.getDataManager().recordGuessNumber(false, totalAttempts);
        }
    }

    @Override
    public String getScreenName() {
        return ScreenNames.GUESS_NUMBER;
    }

    @Override
    public void onShow() {
        // Ne radimo ništa - brojači ostaju
    }
}