package opixels.ui.games;

import opixels.ui.MainFrame;
import opixels.ui.Screen;
import opixels.ui.ScreenNames;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

public class GuessNumberPanel extends JPanel implements Screen {

    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);

    private final MainFrame mainFrame;
    private final JTextField guessField = new JTextField(5);
    private final JTextArea resultArea = new JTextArea(10, 24);
    private final Random random = new Random();

    private int secretNumber;
    private int attempts;
    private boolean finished;

    public GuessNumberPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        JButton chooseButton = new JButton("Odaberi");
        chooseButton.setPreferredSize(BUTTON_SIZE);
        chooseButton.addActionListener(e -> checkGuess());

        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(BUTTON_SIZE);
        startButton.addActionListener(e -> startRound());

        JPanel left = new JPanel(new GridLayout(6, 1, 0, 10));
        left.add(new JLabel("Guess the Number"));
        left.add(new JLabel("Unos (1-10):"));
        left.add(guessField);
        left.add(chooseButton);
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
    }

    private void startRound() {
        secretNumber = random.nextInt(10) + 1;
        attempts = 0;
        finished = false;
        guessField.setText("");
        resultArea.setText("Pogodite broj od 1 do 10.\n\nRacunalo:\n-\n\nRezultat:\n-");
    }

    private void checkGuess() {
        if (finished) {
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(guessField.getText().trim());
        } catch (NumberFormatException e) {
            resultArea.setText("Pogodite broj od 1 do 10.\n\nRacunalo:\n-\n\nRezultat:\nUnesite cijeli broj.");
            return;
        }

        attempts++;

        if (guess == secretNumber) {
            resultArea.setText(
                    "Pogodili ste broj " + secretNumber + ".\n\n"
                            + "Racunalo:\n" + secretNumber + "\n\n"
                            + "Rezultat:\nWIN"
            );
            finished = true;
            mainFrame.getDataManager().recordGuessNumber(true, attempts);
        } else if (guess < secretNumber) {
            resultArea.setText(
                    "Pogodite broj od 1 do 10.\n\nRacunalo:\n-\n\nRezultat:\nVeći."
            );
        } else {
            resultArea.setText(
                    "Pogodite broj od 1 do 10.\n\nRacunalo:\n-\n\nRezultat:\nManji."
            );
        }

        guessField.setText("");
    }

    @Override
    public String getScreenName() {
        return ScreenNames.GUESS_NUMBER;
    }

    @Override
    public void onShow() {
        startRound();
    }
}
