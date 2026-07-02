package opixels.ui.games;

import opixels.ui.MainFrame;
import opixels.ui.Screen;
import opixels.ui.ScreenNames;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Random;

public class RockPaperScissorsPanel extends JPanel implements Screen {

    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);

    private final MainFrame mainFrame;
    private final JTextArea resultArea = new JTextArea(10, 22);
    private final Random random = new Random();
    private int totalAttempts;
    private int wins;
    private int draws;

    public RockPaperScissorsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        JPanel left = new JPanel(new GridLayout(5, 1, 0, 10));
        left.add(new JLabel("Rock Paper Scissors"));
        left.add(createChoiceButton("Kamen", "Kamen"));
        left.add(createChoiceButton("Škare", "Škare"));
        left.add(createChoiceButton("Papir", "Papir"));
        left.add(new JLabel(" "));

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Odaberite:\n-\n\nRačunalo:\n-\n\nRezultat:\n-\n\nUkupno igara: 0\nPobjede: 0\nNeriješeno: 0");

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Rezultat"), BorderLayout.NORTH);
        right.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);

        totalAttempts = 0;
        wins = 0;
        draws = 0;
    }

    private JButton createChoiceButton(String label, String choice) {
        JButton button = new JButton(label);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(e -> play(choice));
        return button;
    }

    private void play(String choice) {
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

        resultArea.setText(
                "Odabrali ste:\n" + choice + "\n\nRačunalo:\n" + computer + "\n\nRezultat:\n" + result
                        + "\n\nUkupno igara: " + totalAttempts
                        + "\nPobjede: " + wins
                        + "\nNeriješeno: " + draws
        );

        // Šaljemo String rezultat
        mainFrame.getDataManager().recordRockPaperScissors(result);
    }

    @Override
    public String getScreenName() {
        return ScreenNames.ROCK_PAPER_SCISSORS;
    }

    @Override
    public void onShow() {
        resultArea.setText("Odaberite:\n-\n\nRačunalo:\n-\n\nRezultat:\n-\n\nUkupno igara: " + totalAttempts + "\nPobjede: " + wins + "\nNeriješeno: " + draws);
    }
}