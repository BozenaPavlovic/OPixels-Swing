package opixels.ui.core;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

// Navigacijska traka (zaglavlje) koja se prikazuje na vrhu aplikacije kada je korisnik prijavljen
public class AppToolBar extends JToolBar {

    private final JLabel userLabel = new JLabel("User: -");
    private final JButton backButton = new JButton("Back to Games");
    private final JButton statsButton = new JButton("Stats");
    private final JButton logoutButton = new JButton("Log out");

    // SUČELJE (OBSERVER PATTERN): Traka ne odlučuje što će se dogoditi na klik, nego obavještava listenera (MainFrame)
    private ToolBarListener listener;

    public AppToolBar() {
        setFloatable(false); // Onemogućuje korisniku da otkači i povlači traku po ekranu
        setLayout(new BorderLayout());

        // Lijevi dio trake: Navigacijski gumbi ("Back to Games" i "Stats")
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));

        // Klik šalje obavijest listener-u (MainFrame-u) da prebaci na izbornik igara
        backButton.addActionListener(e -> {
            if (listener != null) {
                listener.onBackClicked();
            }
        });

        // Klik šalje obavijest listener-u da prebaci na ekran statistike
        statsButton.addActionListener(e -> {
            if (listener != null) {
                listener.onStatsClicked();
            }
        });
        navPanel.add(backButton);
        navPanel.add(statsButton);

        // Desni dio trake: Profil i odjava ("User: [Ime]" i "Log out")
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        userPanel.setOpaque(false); // Prozirna pozadina radi boljeg uklapanja
        userLabel.setHorizontalAlignment(JLabel.RIGHT);

        // Klik šalje obavijest listener-u da odjavi korisnika
        logoutButton.addActionListener(e -> {
            if (listener != null) {
                listener.onLogoutClicked();
            }
        });
        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        // Dodavanje lijevog panela na zapad (WEST) i desnog na istok (EAST)
        add(navPanel, BorderLayout.WEST);
        add(userPanel, BorderLayout.EAST);
    }

    // Povezuje MainFrame s ovom trakom
    public void setListener(ToolBarListener listener) {
        this.listener = listener;
    }

    // Osvježava prikaz imena trenutno prijavljenog korisnika u desnom kutu
    public void updateUserLabel(String username) {
        if (username != null && !username.isEmpty()) {
            userLabel.setText("User: " + username);
        } else {
            userLabel.setText("User: -");
        }
    }

    // Omogućuje skrivanje trake (npr. na Login i Register ekranima)
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}