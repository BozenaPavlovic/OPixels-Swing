package opixels.ui.games.guess_number;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import opixels.ui.games.events.GuessNumberEvent;
import opixels.ui.games.listeners.GuessNumberListener;

public class GuessNumberLeftPanel extends JPanel {
    private final JButton[] numberButtons = new JButton[5];
    private final JButton startButton;
    private GuessNumberListener listener;

    public GuessNumberLeftPanel() {
        setLayout(new GridLayout(6, 1, 0, 10));

        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(140, 36));
        startButton.addActionListener(e -> {
            if (listener != null) {
                listener.onStartGame();
            }
        });

        add(new JLabel("Pogodi broj"));
        add(new JLabel("Odaberi broj (1-5):"));

        JPanel numberPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        for (int i = 0; i < 5; i++) {
            int number = i + 1;
            numberButtons[i] = new JButton(String.valueOf(number));
            numberButtons[i].setPreferredSize(new Dimension(50, 40));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 14));
            numberButtons[i].setEnabled(false);
            numberButtons[i].addActionListener(e -> {
                if (listener != null) {
                    listener.onGuessSubmitted(new GuessNumberEvent(number));
                }
            });
            numberPanel.add(numberButtons[i]);
        }
        add(numberPanel);
        add(startButton);
        add(new JLabel(" "));
    }

    public void setListener(GuessNumberListener listener) {
        this.listener = listener;
    }

    public void enableButtons(boolean enable) {
        for (JButton button : numberButtons) {
            button.setEnabled(enable);
        }
    }
}
