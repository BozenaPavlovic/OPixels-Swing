package opixels.ui.games;

import opixels.ui.MainFrame;
import opixels.ui.Screen;
import opixels.ui.ScreenNames;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class SpeedClickerPanel extends JPanel implements Screen {

    private static final int GAME_SECONDS = 10;
    private static final int TARGET_CLICKS = 10;
    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);

    private final MainFrame mainFrame;
    private final JPanel playArea = new JPanel(null);
    private final JPanel redSquare = new JPanel();
    private final JLabel timerLabel = new JLabel("Vrijeme: " + GAME_SECONDS);
    private final JLabel clicksLabel = new JLabel("Klikovi: 0");
    private final JTextArea resultArea = new JTextArea(8, 22);
    private final Random random = new Random();

    private Timer gameTimer;
    private int timeLeft = GAME_SECONDS;
    private int clicks;
    private boolean running;
    private boolean recorded;

    public SpeedClickerPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        redSquare.setBackground(Color.RED);
        redSquare.setPreferredSize(new Dimension(50, 50));
        redSquare.setSize(50, 50);
        redSquare.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick();
            }
        });

        playArea.setPreferredSize(new Dimension(400, 260));
        playArea.add(redSquare);

        JButton startButton = new JButton("START");
        startButton.setPreferredSize(BUTTON_SIZE);
        startButton.addActionListener(e -> startGame());

        JPanel left = new JPanel(new GridLayout(5, 1, 0, 10));
        left.add(new JLabel("Speed Clicker"));
        left.add(startButton);
        left.add(timerLabel);
        left.add(clicksLabel);
        left.add(new JLabel(" "));

        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText("Status:\nKliknite START");

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Rezultat"), BorderLayout.NORTH);
        right.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout());
        center.add(playArea, BorderLayout.CENTER);

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);

        gameTimer = new Timer(1000, e -> tick());
    }

    private void startGame() {
        if (running) {
            return;
        }
        running = true;
        recorded = false;
        clicks = 0;
        timeLeft = GAME_SECONDS;
        timerLabel.setText("Vrijeme: " + timeLeft);
        clicksLabel.setText("Klikovi: 0");
        resultArea.setText("Status:\nKlikajte crveni kvadrat!");
        moveSquare();
        gameTimer.start();
    }

    private void tick() {
        timeLeft--;
        timerLabel.setText("Vrijeme: " + timeLeft);
        if (timeLeft <= 0) {
            endGame();
        }
    }

    private void handleClick() {
        if (!running) {
            return;
        }
        clicks++;
        clicksLabel.setText("Klikovi: " + clicks);
        if (clicks >= TARGET_CLICKS) {
            resultArea.setText("Status:\nPobjeda! 10 klikova.");
            endGame();
        } else {
            moveSquare();
        }
    }

    private void moveSquare() {
        int maxX = Math.max(1, playArea.getWidth() - redSquare.getWidth());
        int maxY = Math.max(1, playArea.getHeight() - redSquare.getHeight());
        int x = random.nextInt(maxX);
        int y = random.nextInt(maxY);
        redSquare.setLocation(x, y);
    }

    private void endGame() {
        gameTimer.stop();
        running = false;
        if (!recorded) {
            mainFrame.getDataManager().recordSpeedClicker(clicks);
            recorded = true;
            if (clicks < TARGET_CLICKS) {
                resultArea.setText("Status:\nKraj. Klikovi: " + clicks);
            }
        }
    }

    @Override
    public String getScreenName() {
        return ScreenNames.SPEED_CLICKER;
    }

    @Override
    public void onShow() {
        gameTimer.stop();
        running = false;
        recorded = false;
        clicks = 0;
        timeLeft = GAME_SECONDS;
        timerLabel.setText("Vrijeme: " + GAME_SECONDS);
        clicksLabel.setText("Klikovi: 0");
        resultArea.setText("Status:\nKliknite START");
        redSquare.setLocation(10, 10);
    }
}
