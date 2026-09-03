package opixels.ui.games.rock_paper_scissors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class RockPaperScissorsRightPanel extends JPanel {
    private final JTextArea resultArea;

    public RockPaperScissorsRightPanel() {
        setLayout(new BorderLayout());

        resultArea = new JTextArea(10, 22);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Odaberite:\n-\n\nRačunalo:\n-\n\nRezultat:\n-\n\nUkupno igara: 0\nPobjede: 0\nNeriješeno: 0");

        add(new JLabel("Rezultat"), BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    public void displayResult(String playerChoice, String computerChoice, String result, int totalAttempts, int wins, int draws) {
        resultArea.setText(
            "Odabrali ste:\n" + playerChoice + "\n\nRačunalo:\n" + computerChoice + "\n\nRezultat:\n" + result
            + "\n\nUkupno igara: " + totalAttempts
            + "\nPobjede: " + wins
            + "\nNeriješeno: " + draws
        );
    }

    public void reset(int totalAttempts, int wins, int draws) {
        resultArea.setText(
            "Odaberite:\n-\n\nRačunalo:\n-\n\nRezultat:\n-\n\nUkupno igara: " + totalAttempts
            + "\nPobjede: " + wins
            + "\nNeriješeno: " + draws
        );
    }
}
