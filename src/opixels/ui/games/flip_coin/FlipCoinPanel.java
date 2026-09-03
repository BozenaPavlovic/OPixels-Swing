package opixels.ui.games.flip_coin;

import opixels.ui.core.MainFrame;
import opixels.ui.core.Screen;
import opixels.ui.core.ScreenNames;
import opixels.ui.games.events.FlipCoinEvent;
import opixels.ui.games.listeners.FlipCoinListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Random;

public class FlipCoinPanel extends JPanel implements Screen, FlipCoinListener {
    private final MainFrame mainFrame;
    private final FlipCoinLeftPanel leftPanel;
    private final FlipCoinRightPanel rightPanel;
    private final Random random = new Random();

    private int totalAttempts;
    private int wins;

    public FlipCoinPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        leftPanel = new FlipCoinLeftPanel();
        rightPanel = new FlipCoinRightPanel();

        leftPanel.setListener(this);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        totalAttempts = 0;
        wins = 0;
    }

    @Override
    public void onChoiceSubmitted(FlipCoinEvent event) {
        String choice = event.getChoice();
        String flip = random.nextBoolean() ? "Glava" : "Pismo";
        boolean win = choice.equals(flip);

        totalAttempts++;
        if (win) {
            wins++;
        }

        rightPanel.displayResult(flip, win, totalAttempts, wins);
        mainFrame.getDataManager().recordFlipCoin(win);
    }

    @Override
    public String getScreenName() {
        return ScreenNames.FLIP_COIN;
    }

    @Override
    public void onShow() {
        rightPanel.reset(totalAttempts, wins);
    }
}
