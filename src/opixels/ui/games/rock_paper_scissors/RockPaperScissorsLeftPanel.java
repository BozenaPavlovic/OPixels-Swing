package opixels.ui.games.rock_paper_scissors;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import opixels.ui.games.events.RockPaperScissorsEvent;
import opixels.ui.games.listeners.RockPaperScissorsListener;

public class RockPaperScissorsLeftPanel extends JPanel {
    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);
    private RockPaperScissorsListener listener;

    public RockPaperScissorsLeftPanel() {
        setLayout(new GridLayout(5, 1, 0, 10));
        
        add(new JLabel("Rock Paper Scissors"));
        add(createChoiceButton("Kamen", "Kamen"));
        add(createChoiceButton("Škare", "Škare"));
        add(createChoiceButton("Papir", "Papir"));
        add(new JLabel(" "));
    }

    private JButton createChoiceButton(String label, String choice) {
        JButton button = new JButton(label);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(e -> {
            if (listener != null) {
                listener.onChoiceSubmitted(new RockPaperScissorsEvent(choice));
            }
        });
        return button;
    }

    public void setListener(RockPaperScissorsListener listener) {
        this.listener = listener;
    }
}
