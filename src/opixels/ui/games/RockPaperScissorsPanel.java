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
    private final JTextArea resultArea = new JTextArea(8, 22);
    private final Random random = new Random();

    public RockPaperScissorsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        JPanel left = new JPanel(new GridLayout(5, 1, 0, 10));
        left.add(new JLabel("Rock Paper Scissors"));
        left.add(createChoiceButton("Kamen", "Kamen"));
        left.add(createChoiceButton("Skare", "Skare"));
        left.add(createChoiceButton("Papir", "Papir"));
        left.add(new JLabel(" "));

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Racunalo:\n-\n\nRezultat:\n-");

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Rezultat"), BorderLayout.NORTH);
        right.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
    }

    private JButton createChoiceButton(String label, String choice) {
        JButton button = new JButton(label);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(e -> play(choice));
        return button;
    }

    private void play(String playerChoice) {
        String[] choices = {"Kamen", "Skare", "Papir"};
        String computerChoice = choices[random.nextInt(choices.length)];
        String result = decideResult(playerChoice, computerChoice);

        resultArea.setText("Racunalo:\n" + computerChoice + "\n\nRezultat:\n" + result);

        mainFrame.getDataManager().recordRockPaperScissors(result);
    }

    private String decideResult(String player, String computer) {
        if (player.equals(computer)) {
            return "Nerijeseno";
        }
        if (("Kamen".equals(player) && "Skare".equals(computer))
                || ("Skare".equals(player) && "Papir".equals(computer))
                || ("Papir".equals(player) && "Kamen".equals(computer))) {
            return "Pobjeda";
        }
        return "Poraz";
    }

    @Override
    public String getScreenName() {
        return ScreenNames.ROCK_PAPER_SCISSORS;
    }

    @Override
    public void onShow() {
        resultArea.setText("Racunalo:\n-\n\nRezultat:\n-");
    }
}
