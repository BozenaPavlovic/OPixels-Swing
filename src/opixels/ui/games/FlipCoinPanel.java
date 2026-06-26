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

public class FlipCoinPanel extends JPanel implements Screen {

    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);

    private final MainFrame mainFrame;
    private final JTextArea resultArea = new JTextArea(8, 22);
    private final Random random = new Random();
    private boolean played;

    public FlipCoinPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        JPanel left = new JPanel(new GridLayout(4, 1, 0, 10));
        left.add(new JLabel("Flip Coin"));
        left.add(createChoiceButton("Glava", "Glava"));
        left.add(createChoiceButton("Pismo", "Pismo"));
        left.add(new JLabel(" "));

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Rezultat bacanja:\n-\n\nStatus:\n-");

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

    private void play(String choice) {
        String flip = random.nextBoolean() ? "Glava" : "Pismo";
        boolean win = choice.equals(flip);

        resultArea.setText(
                "Rezultat bacanja:\n" + flip + "\n\nStatus:\n" + (win ? "Pogodili ste!" : "Niste pogodili.")
        );

        if (!played) {
            mainFrame.getDataManager().recordFlipCoin(win);
            played = true;
        }
    }

    @Override
    public String getScreenName() {
        return ScreenNames.FLIP_COIN;
    }

    @Override
    public void onShow() {
        resultArea.setText("Rezultat bacanja:\n-\n\nStatus:\n-");
        played = false;
    }
}
