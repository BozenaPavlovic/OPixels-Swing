package opixels.ui.games.speed_clicker;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class SpeedClickerRightPanel extends JPanel {
    private final JTextArea resultArea;

    public SpeedClickerRightPanel() {
        setLayout(new BorderLayout());

        resultArea = new JTextArea(10, 22);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Status:\nKliknite START\n\nUkupno igara: 0\nPobjede: 0");

        add(new JLabel("Rezultat"), BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    public void displayGameStart(int totalAttempts, int wins) {
        resultArea.setText(
            "Status:\nKlikajte crveni kvadrat!\n\nUkupno igara: " + totalAttempts + "\nPobjede: " + wins
        );
    }

    public void displayResult(int clicks, int totalAttempts, int wins, boolean win) {
        String status = win ? "Pobjeda! 10 klikova." : "Kraj. Klikovi: " + clicks;
        resultArea.setText(
            "Status:\n" + status + "\n\nUkupno igara: " + totalAttempts + "\nPobjede: " + wins
        );
    }

    public void reset(int totalAttempts, int wins) {
        resultArea.setText("Status:\nKliknite START\n\nUkupno igara: " + totalAttempts + "\nPobjede: " + wins);
    }
}
