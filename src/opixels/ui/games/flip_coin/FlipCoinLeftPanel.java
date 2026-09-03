package opixels.ui.games.flip_coin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import opixels.ui.games.events.FlipCoinEvent;
import opixels.ui.games.listeners.FlipCoinListener;

public class FlipCoinLeftPanel extends JPanel {
    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);
    private FlipCoinListener listener;

    public FlipCoinLeftPanel() {
        setLayout(new GridLayout(5, 1, 0, 10));
        
        add(new JLabel("Flip Coin"));
        add(createChoiceButton("Glava", "Glava"));
        add(createChoiceButton("Pismo", "Pismo"));
        add(new JLabel(" "));
    }

    private JButton createChoiceButton(String label, String choice) {
        JButton button = new JButton(label);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(e -> {
            if (listener != null) {
                listener.onChoiceSubmitted(new FlipCoinEvent(choice));
            }
        });
        return button;
    }

    public void setListener(FlipCoinListener listener) {
        this.listener = listener;
    }
}
