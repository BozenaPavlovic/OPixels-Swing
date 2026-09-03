package opixels.ui.games.speed_clicker;

import opixels.ui.core.MainFrame;
import opixels.ui.core.Screen;
import opixels.ui.core.ScreenNames;
import opixels.ui.games.listeners.SpeedClickerListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SpeedClickerPanel extends JPanel implements Screen, SpeedClickerListener {
    private static final int GAME_SECONDS = 10;
    private static final int TARGET_CLICKS = 10;

    private final MainFrame mainFrame;
    private final SpeedClickerLeftPanel leftPanel;
    private final SpeedClickerRightPanel rightPanel;

    private int timeLeft = GAME_SECONDS;
    private int clicks;
    private boolean running;
    private boolean recorded;
    private int totalAttempts;
    private int wins;

    public SpeedClickerPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        leftPanel = new SpeedClickerLeftPanel();
        rightPanel = new SpeedClickerRightPanel();

        leftPanel.setListener(this);

        JPanel center = new JPanel(new BorderLayout());
        center.add(leftPanel.getPlayArea(), BorderLayout.CENTER);

        add(leftPanel, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        totalAttempts = 0;
        wins = 0;
    }

    @Override
    public void onStartGame() {
        if (running) {
            return;
        }
        running = true;
        recorded = false;
        clicks = 0;
        timeLeft = GAME_SECONDS;
        leftPanel.setTimerLabel(timeLeft);
        leftPanel.setClicksLabel(0);
        leftPanel.moveSquare();
        rightPanel.displayGameStart(totalAttempts, wins);
        
        leftPanel.startTimer(this::tick);
    }

    @Override
    public void onGameClicked() {
        if (!running) {
            return;
        }
        clicks++;
        leftPanel.setClicksLabel(clicks);
        if (clicks >= TARGET_CLICKS) {
            endGame();
        } else {
            leftPanel.moveSquare();
        }
    }

    private void tick() {
        timeLeft--;
        leftPanel.setTimerLabel(timeLeft);
        if (timeLeft <= 0) {
            endGame();
        }
    }

    private void endGame() {
        leftPanel.stopTimer();
        running = false;
        if (!recorded) {
            totalAttempts++;
            boolean win = clicks >= TARGET_CLICKS;
            if (win) {
                wins++;
            }
            mainFrame.getDataManager().recordSpeedClicker(clicks);
            recorded = true;

            rightPanel.displayResult(clicks, totalAttempts, wins, win);
        }
    }

    @Override
    public String getScreenName() {
        return ScreenNames.SPEED_CLICKER;
    }

    @Override
    public void onShow() {
        leftPanel.stopTimer();
        running = false;
        recorded = false;
        clicks = 0;
        timeLeft = GAME_SECONDS;
        leftPanel.setTimerLabel(GAME_SECONDS);
        leftPanel.setClicksLabel(0);
        leftPanel.resetSquarePosition();
        rightPanel.reset(totalAttempts, wins);
    }
}
