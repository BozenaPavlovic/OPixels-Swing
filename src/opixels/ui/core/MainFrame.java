package opixels.ui.core;

import opixels.storage.DataManager;
import opixels.ui.components.LoginPanel;
import opixels.ui.components.RegisterPanel;
import opixels.ui.components.MainMenuPanel;
import opixels.ui.components.StatsPanel;
import opixels.ui.games.flip_coin.FlipCoinPanel;
import opixels.ui.games.guess_number.GuessNumberPanel;
import opixels.ui.games.rock_paper_scissors.RockPaperScissorsPanel;
import opixels.ui.games.speed_clicker.SpeedClickerPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class MainFrame extends JFrame implements ToolBarListener {

    private final DataManager dataManager = new DataManager();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout);
    private final AppToolBar appToolBar = new AppToolBar();

    public MainFrame() {
        setTitle("OPixels");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        appToolBar.setListener(this);

        contentPanel.add(new LoginPanel(this), ScreenNames.LOGIN);
        contentPanel.add(new RegisterPanel(this), ScreenNames.REGISTER);
        contentPanel.add(new MainMenuPanel(this), ScreenNames.MAIN_MENU);
        contentPanel.add(new FlipCoinPanel(this), ScreenNames.FLIP_COIN);
        contentPanel.add(new SpeedClickerPanel(this), ScreenNames.SPEED_CLICKER);
        contentPanel.add(new RockPaperScissorsPanel(this), ScreenNames.ROCK_PAPER_SCISSORS);
        contentPanel.add(new GuessNumberPanel(this), ScreenNames.GUESS_NUMBER);
        contentPanel.add(new StatsPanel(this), ScreenNames.STATS);

        JPanel root = new JPanel(new BorderLayout());
        root.add(appToolBar, BorderLayout.NORTH);
        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        showScreen(ScreenNames.LOGIN);
    }

    @Override
    public void onBackClicked() {
        showScreen(ScreenNames.MAIN_MENU);
    }

    @Override
    public void onStatsClicked() {
        showScreen(ScreenNames.STATS);
    }

    @Override
    public void onLogoutClicked() {
        logout();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public void showScreen(String screenName) {
        boolean authScreen = ScreenNames.LOGIN.equals(screenName) || ScreenNames.REGISTER.equals(screenName);
        appToolBar.setVisible(!authScreen);

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
            appToolBar.updateUserLabel(dataManager.getCurrentUser().getUsername());
        } else {
            appToolBar.updateUserLabel(null);
        }
    }

    public void logout() {
        dataManager.logout();
        showScreen(ScreenNames.LOGIN);
    }
}
