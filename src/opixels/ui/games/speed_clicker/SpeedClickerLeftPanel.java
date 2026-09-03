package opixels.ui.games.speed_clicker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import opixels.ui.games.listeners.SpeedClickerListener;

public class SpeedClickerLeftPanel extends JPanel {
    private static final Dimension BUTTON_SIZE = new Dimension(140, 36);
    private static final int GAME_SECONDS = 10;
    private static final int TARGET_CLICKS = 10;

    private final JPanel playArea = new JPanel(null);
    private final JPanel redSquare = new JPanel();
    private final JLabel timerLabel = new JLabel("Vrijeme: " + GAME_SECONDS);
    private final JLabel clicksLabel = new JLabel("Klikovi: 0");
    private final JButton startButton = new JButton("START");
    private final Random random = new Random();
    private Timer gameTimer;
    private SpeedClickerListener listener;

    public SpeedClickerLeftPanel() {
        setLayout(new GridLayout(6, 1, 0, 10));

        startButton.setPreferredSize(BUTTON_SIZE);
        startButton.addActionListener(e -> {
            if (listener != null) {
                listener.onStartGame();
            }
        });

        redSquare.setBackground(Color.RED);
        redSquare.setPreferredSize(new Dimension(50, 50));
        redSquare.setSize(50, 50);
        redSquare.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.onGameClicked();
                }
            }
        });

        playArea.setPreferredSize(new Dimension(400, 260));
        playArea.add(redSquare);

        add(new JLabel("Speed Clicker"));
        add(startButton);
        add(timerLabel);
        add(clicksLabel);
        add(new JLabel(" "));
    }

    public void setListener(SpeedClickerListener listener) {
        this.listener = listener;
    }

    public void setTimerLabel(int timeLeft) {
        timerLabel.setText("Vrijeme: " + timeLeft);
    }

    public void setClicksLabel(int clicks) {
        clicksLabel.setText("Klikovi: " + clicks);
    }

    public void moveSquare() {
        int maxX = Math.max(1, playArea.getWidth() - redSquare.getWidth());
        int maxY = Math.max(1, playArea.getHeight() - redSquare.getHeight());
        int x = random.nextInt(maxX);
        int y = random.nextInt(maxY);
        redSquare.setLocation(x, y);
    }

    public void resetSquarePosition() {
        redSquare.setLocation(10, 10);
    }

    public JPanel getPlayArea() {
        return playArea;
    }

    public void startTimer(Runnable onTick) {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(1000, e -> onTick.run());
        gameTimer.start();
    }

    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }
}
