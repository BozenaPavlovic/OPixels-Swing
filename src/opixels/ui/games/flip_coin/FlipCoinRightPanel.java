package opixels.ui.games.flip_coin;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class FlipCoinRightPanel extends JPanel {
    private final JTextArea resultArea;

    public FlipCoinRightPanel() {
        setLayout(new BorderLayout());

        resultArea = new JTextArea(10, 22);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Rezultat bacanja:\n-\n\nStatus:\n-\n\nUkupno igara: 0\nPobjede: 0");

        add(new JLabel("Rezultat"), BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    public void displayResult(String flip, boolean win, int totalAttempts, int wins) {
        resultArea.setText(
            "Rezultat bacanja:\n" + flip + "\n\nStatus:\n" + (win ? "Pogodili ste!" : "Niste pogodili.")
            + "\n\nUkupno igara: " + totalAttempts
            + "\nPobjede: " + wins
        );
    }

    public void reset(int totalAttempts, int wins) {
        resultArea.setText(
            "Rezultat bacanja:\n-\n\nStatus:\n-\n\nUkupno igara: " + totalAttempts
            + "\nPobjede: " + wins
        );
    }
}
