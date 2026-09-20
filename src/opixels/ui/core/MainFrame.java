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

// MainFrame je glavno proširenje JFrame-a i središnji upravljač (mozak) aplikacije
public class MainFrame extends JFrame implements ToolBarListener {

    // 1. Središnji upravitelj podacima (jedinstvena instanca u cijeloj aplikaciji)
    private final DataManager dataManager = new DataManager();

    // 2. CardLayout omogućuje slaganje ekrana (panela) "jedan preko drugog" i jednostavnu zamjenu
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(cardLayout); // Kontejner u koji idu svi ekrani
    private final AppToolBar appToolBar = new AppToolBar();

    public MainFrame() {
        setTitle("OPixels");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null); // Centriranje prozora na ekranu

        // Povezivanje listenera za AppToolBar (ovaj MainFrame reagira na klikove u traci)
        appToolBar.setListener(this);

        // REGISTRACIJA EKRANA u CardLayout: Svaki panel dobiva "this" (referencu na MainFrame) i jedinstveno ime
        contentPanel.add(new LoginPanel(this), ScreenNames.LOGIN);
        contentPanel.add(new RegisterPanel(this), ScreenNames.REGISTER);
        contentPanel.add(new MainMenuPanel(this), ScreenNames.MAIN_MENU);
        contentPanel.add(new FlipCoinPanel(this), ScreenNames.FLIP_COIN);
        contentPanel.add(new SpeedClickerPanel(this), ScreenNames.SPEED_CLICKER);
        contentPanel.add(new RockPaperScissorsPanel(this), ScreenNames.ROCK_PAPER_SCISSORS);
        contentPanel.add(new GuessNumberPanel(this), ScreenNames.GUESS_NUMBER);
        contentPanel.add(new StatsPanel(this), ScreenNames.STATS);

        // Glavni izgled: ToolBar ide na vrh (NORTH), a ekrani u sredinu (CENTER)
        JPanel root = new JPanel(new BorderLayout());
        root.add(appToolBar, BorderLayout.NORTH);
        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);

        // Početni ekran aplikacije je LOGIN
        showScreen(ScreenNames.LOGIN);
    }

    // IMPLEMENTACIJA TOOLBAR LISTENER-A: Reakcija na klikove gumba iz zaglavlja
    @Override
    public void onBackClicked() {
        showScreen(ScreenNames.MAIN_MENU); // Gumb "Natrag" vraća na glavni izbornik
    }

    @Override
    public void onStatsClicked() {
        showScreen(ScreenNames.STATS); // Gumb "Statistika" otvara statistiku
    }

    @Override
    public void onLogoutClicked() {
        logout(); // Gumb "Odjava" odjavljuje korisnika
    }

    // GETTER: Omogućuje svim panelima pristup jedinstvenom DataManager-u
    public DataManager getDataManager() {
        return dataManager;
    }

    // KLJUČNA METODA ZA NAVIGACIJU MEĐU EKRANIMA
    public void showScreen(String screenName) {
        // 1. Skriva ToolBar na ekranima za prijavu i registraciju (auth ekrani)
        boolean authScreen = ScreenNames.LOGIN.equals(screenName) || ScreenNames.REGISTER.equals(screenName);
        appToolBar.setVisible(!authScreen);

        // 2. Osvježava ime prijavljenog korisnika u gornjoj traci
        updateUserInfo();

        // 3. CardLayout fizički prebacuje prikaz na traženi panel
        cardLayout.show(contentPanel, screenName);

        // 4. POLIMORFIZAM NA DJELU: Traži panel koji se upravo prikazao i poziva njegovu onShow() metodu
        for (java.awt.Component component : contentPanel.getComponents()) {
            if (component instanceof Screen screen && screenName.equals(screen.getScreenName())) {
                screen.onShow(); // Mijenja/resetira stanje ekrana koji je stigao u prvi plan
                break;
            }
        }
    }

    // Osvježava prikaz imena trenutno prijavljenog korisnika na traci
    public void updateUserInfo() {
        if (dataManager.getCurrentUser() != null) {
            appToolBar.updateUserLabel(dataManager.getCurrentUser().getUsername());
        } else {
            appToolBar.updateUserLabel(null);
        }
    }

    // Odjava korisnika: Briše sesiju u DataManager-u i vraća na Login ekran
    public void logout() {
        dataManager.logout();
        showScreen(ScreenNames.LOGIN);
    }
}