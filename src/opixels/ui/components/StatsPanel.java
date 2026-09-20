package opixels.ui.components;

import opixels.ui.core.MainFrame;
import opixels.ui.core.Screen;
import opixels.ui.core.ScreenNames;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class StatsPanel extends JPanel implements Screen {

    // Pohrana reference na MainFrame radi pristupa DataManager-u
    private final MainFrame mainFrame;

    // Tekstualna područja za prikaz formirane statistike (onemogućeno uređivanje)
    private final JTextArea myStatsArea = new JTextArea(20, 28);
    private final JTextArea globalStatsArea = new JTextArea(20, 28);

    public StatsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        // ZAŠTO setEditable(false): JTextArea služi samo za čitanje/prikaz generiranog teksta, ne za unos
        myStatsArea.setEditable(false);
        globalStatsArea.setEditable(false);

        // Lijevi panel: Osobna statistika igrača unutar JScrollPane-a (za povijest igara)
        JPanel left = new JPanel(new BorderLayout(0, 6));
        left.add(new JLabel("My Stats"), BorderLayout.NORTH);
        left.add(new JScrollPane(myStatsArea), BorderLayout.CENTER);

        // Desni panel: Globalna statistika svih igrača zajedno
        JPanel right = new JPanel(new BorderLayout(0, 6));
        right.add(new JLabel("Global"), BorderLayout.NORTH);
        right.add(new JScrollPane(globalStatsArea), BorderLayout.CENTER);

        // Layout u 2 jednaka stupca (1 redak, 2 stupca)
        JPanel content = new JPanel(new GridLayout(1, 2, 16, 0));
        content.add(left);
        content.add(right);

        add(new JLabel("Statistika", JLabel.CENTER), BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    @Override
    public String getScreenName() {
        return ScreenNames.STATS;
    }

    // KLJUČNA LOGIKA Ekran se osvježava SVAKI PUT kada postane vidljiv (onShow)
    @Override
    public void onShow() {
        // 1. OSOBNA STATISTIKA: Dohvaća statistiku prijavljenog korisnika i poziva formatStats()
        if (mainFrame.getDataManager().getCurrentUserStats() != null) {
            myStatsArea.setText(mainFrame.getDataManager().getCurrentUserStats().formatStats());
        } else {
            myStatsArea.setText(""); // Ako nitko nije prijavljen, polje ostaje prazno
        }

        // 2. GLOBALNA STATISTIKA: Dohvaća zbrojenu statistiku svih korisnika i formatira je
        globalStatsArea.setText(mainFrame.getDataManager().getGlobalStats().formatStats());
    }
}