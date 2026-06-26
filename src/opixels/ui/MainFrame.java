package opixels.ui;

import opixels.storage.DataManager;
import opixels.ui.games.FlipCoinPanel;
import opixels.ui.games.GuessNumberPanel;
import opixels.ui.games.RockPaperScissorsPanel;
import opixels.ui.games.SpeedClickerPanel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

public class MainFrame extends JFrame {

    private final DataManager dataManager = new DataManager();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final JToolBar toolBar = new JToolBar();
    private final JLabel userLabel = new JLabel("User: -");
    private final JButton logoutButton = new JButton("Log out");
    private final JButton backButton = new JButton("Back to Games");
    private final JButton statsButton = new JButton("Stats");

    public MainFrame() {
        setTitle("OPixels");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        setJMenuBar(createMenuBar());
        createToolBar();

        contentPanel.add(new LoginPanel(this), ScreenNames.LOGIN);
        contentPanel.add(new RegisterPanel(this), ScreenNames.REGISTER);
        contentPanel.add(new MainMenuPanel(this), ScreenNames.MAIN_MENU);
        contentPanel.add(new FlipCoinPanel(this), ScreenNames.FLIP_COIN);
        contentPanel.add(new SpeedClickerPanel(this), ScreenNames.SPEED_CLICKER);
        contentPanel.add(new RockPaperScissorsPanel(this), ScreenNames.ROCK_PAPER_SCISSORS);
        contentPanel.add(new GuessNumberPanel(this), ScreenNames.GUESS_NUMBER);
        contentPanel.add(new StatsPanel(this), ScreenNames.STATS);

        JPanel root = new JPanel(new BorderLayout());
        root.add(toolBar, BorderLayout.NORTH);
        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        showScreen(ScreenNames.LOGIN);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        return menuBar;
    }

    private void createToolBar() {
        toolBar.setFloatable(false);
        toolBar.setLayout(new BorderLayout());

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        backButton.addActionListener(e -> showScreen(ScreenNames.MAIN_MENU));
        statsButton.addActionListener(e -> showScreen(ScreenNames.STATS));
        navPanel.add(backButton);
        navPanel.add(statsButton);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new javax.swing.BoxLayout(userPanel, javax.swing.BoxLayout.Y_AXIS));
        userLabel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
        logoutButton.setAlignmentX(JButton.RIGHT_ALIGNMENT);
        logoutButton.addActionListener(e -> logout());
        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        toolBar.add(navPanel, BorderLayout.WEST);
        toolBar.add(userPanel, BorderLayout.EAST);
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void showScreen(String screenName) {
        boolean authScreen = ScreenNames.LOGIN.equals(screenName) || ScreenNames.REGISTER.equals(screenName);
        toolBar.setVisible(!authScreen);

        updateUserInfo();

        cardLayout.show(contentPanel, screenName);

        for (java.awt.Component component : contentPanel.getComponents()) {
            if (component instanceof Screen screen && screenName.equals(screen.getScreenName())) {
                screen.onShow();
                break;
            }
        }
    }

    public void updateUserInfo() {
        if (dataManager.getCurrentUser() != null) {
            userLabel.setText("User: " + dataManager.getCurrentUser().getUsername());
        } else {
            userLabel.setText("User: -");
        }
    }

    public void logout() {
        dataManager.logout();
        showScreen(ScreenNames.LOGIN);
    }
}
