package opixels.ui.games.rock_paper_scissors;

import opixels.ui.core.MainFrame;
import opixels.ui.core.Screen;
import opixels.ui.core.ScreenNames;
import opixels.ui.games.events.RockPaperScissorsEvent;
import opixels.ui.games.listeners.RockPaperScissorsListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.Random;

public class RockPaperScissorsPanel extends JPanel implements Screen, RockPaperScissorsListener {
    private final MainFrame mainFrame;
    private final RockPaperScissorsLeftPanel leftPanel;
    private final RockPaperScissorsRightPanel rightPanel;
    private final Random random = new Random();

    private int totalAttempts;
    private int wins;
    private int draws;

    public RockPaperScissorsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        leftPanel = new RockPaperScissorsLeftPanel();
        rightPanel = new RockPaperScissorsRightPanel();

        leftPanel.setListener(this);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        totalAttempts = 0;
        wins = 0;
        draws = 0;
    }

    @Override
    public void onChoiceSubmitted(RockPaperScissorsEvent event) {
        String choice = event.getChoice();
        String[] options = {"Kamen", "Škare", "Papir"};
        String computer = options[random.nextInt(3)];

        String result;
        boolean win = false;
        boolean draw = false;

        if (choice.equals(computer)) {
            result = "Neriješeno";
            draw = true;
        } else if ((choice.equals("Kamen") && computer.equals("Škare")) ||
                (choice.equals("Škare") && computer.equals("Papir")) ||
                (choice.equals("Papir") && computer.equals("Kamen"))) {
            result = "POBJEDA";
            win = true;
        } else {
            result = "PORAZ";
        }

        totalAttempts++;
        if (win) {
            wins++;
        } else if (draw) {
            draws++;
        }

        rightPanel.displayResult(choice, computer, result, totalAttempts, wins, draws);
        mainFrame.getDataManager().recordRockPaperScissors(result);
    }

    @Override
    public String getScreenName() {
        return ScreenNames.ROCK_PAPER_SCISSORS;
    }

    @Override
    public void onShow() {
        rightPanel.reset(totalAttempts, wins, draws);
    }
}
