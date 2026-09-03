package opixels.ui.games.guess_number;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;

public class GuessNumberRightPanel extends JPanel {
    private final JTextArea resultArea;

    public GuessNumberRightPanel() {
        setLayout(new BorderLayout());

        resultArea = new JTextArea(10, 24);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Pritisnite 'Start' za novu igru.\n\nRacunalo:\n-\n\nRezultat:\n-");

        add(new JLabel("Rezultat"), BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    public void displayResult(String computerNumber, String result, int totalAttempts, int wins) {
        resultArea.setText(
            result + "\n\n"
            + "Racunalo:\n" + computerNumber + "\n\n"
            + "Rezultat:\n" + result
            + "\n\nUkupno igara: " + totalAttempts
            + "\nPobjede: " + wins
        );
    }

    public void displayGameStarted(int totalAttempts, int wins) {
        resultArea.setText(
            "Pogodite broj od 1 do 5.\n\n"
            + "Racunalo:\n-\n\n"
            + "Rezultat:\n-"
            + "\n\nUkupno igara: " + totalAttempts
            + "\nPobjede: " + wins
        );
    }

    public void reset() {
        resultArea.setText("Pritisnite 'Start' za novu igru.\n\nRacunalo:\n-\n\nRezultat:\n-");
    }
}
