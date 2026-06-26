package opixels.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class MainMenuPanel extends JPanel implements Screen {

    private static final Dimension BUTTON_SIZE = new Dimension(220, 40);

    private final MainFrame mainFrame;

    public MainMenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("ODABERI IGRU", JLabel.CENTER);
        JButton statsButton = new JButton("Stats");
        statsButton.setPreferredSize(new Dimension(100, 30));
        statsButton.addActionListener(e -> mainFrame.showScreen(ScreenNames.STATS));
        header.add(titleLabel, BorderLayout.CENTER);
        header.add(statsButton, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(4, 1, 0, 12));
        grid.add(createNavButton("Flip Coin", ScreenNames.FLIP_COIN));
        grid.add(createNavButton("Speed Clicker", ScreenNames.SPEED_CLICKER));
        grid.add(createNavButton("Rock Paper Scissors", ScreenNames.ROCK_PAPER_SCISSORS));
        grid.add(createNavButton("Guess the Number", ScreenNames.GUESS_NUMBER));

        JPanel gridWrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gridWrapper.add(grid, gbc);

        add(header, BorderLayout.NORTH);
        add(gridWrapper, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text, String screen) {
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);
        button.addActionListener(e -> mainFrame.showScreen(screen));
        return button;
    }

    @Override
    public String getScreenName() {
        return ScreenNames.MAIN_MENU;
    }

    @Override
    public void onShow() {
    }
}
