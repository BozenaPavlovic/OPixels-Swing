package opixels.ui.games.guess_number;

import opixels.ui.core.MainFrame;
import opixels.ui.core.Screen;
import opixels.ui.core.ScreenNames;
import opixels.ui.games.events.GuessNumberEvent;
import opixels.ui.games.listeners.GuessNumberListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Random;

public class GuessNumberPanel extends JPanel implements Screen, GuessNumberListener {

    private final MainFrame mainFrame;
    private final GuessNumberLeftPanel leftPanel;
    private final GuessNumberRightPanel rightPanel;
    private final Random random = new Random();

    private int secretNumber;
    private boolean finished;
    private boolean gameStarted;
    private int totalAttempts;
    private int wins;

    public GuessNumberPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        leftPanel = new GuessNumberLeftPanel();
        rightPanel = new GuessNumberRightPanel();

        leftPanel.setListener(this);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        totalAttempts = 0;
        wins = 0;
        gameStarted = false;
        finished = true;
        leftPanel.enableButtons(false);
    }

    @Override
    public void onStartGame() {
        secretNumber = random.nextInt(5) + 1;
        finished = false;
        gameStarted = true;
        leftPanel.enableButtons(true);
        rightPanel.displayGameStarted(totalAttempts, wins);
    }

    @Override
    public void onGuessSubmitted(GuessNumberEvent event) {
        if (finished || !gameStarted) {
            return;
        }

        int guess = event.getGuess();
        finished = true;
        gameStarted = false;
        totalAttempts++;

        leftPanel.enableButtons(false);

        String result;
        if (guess == secretNumber) {
            wins++;
            result = "POBJEDA - Pogodili ste broj " + secretNumber;
            mainFrame.getDataManager().recordGuessNumber(true, totalAttempts);
        } else {
            result = "IZGUBILI - Niste pogodili broj " + secretNumber;
            mainFrame.getDataManager().recordGuessNumber(false, totalAttempts);
        }

        rightPanel.displayResult(String.valueOf(secretNumber), result, totalAttempts, wins);
    }

    @Override
    public String getScreenName() {
        return ScreenNames.GUESS_NUMBER;
    }

    @Override
    public void onShow() {
    }
}