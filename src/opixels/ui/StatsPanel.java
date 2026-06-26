package opixels.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class StatsPanel extends JPanel implements Screen {

    private final MainFrame mainFrame;
    private final JTextArea myStatsArea = new JTextArea(20, 28);
    private final JTextArea globalStatsArea = new JTextArea(20, 28);

    public StatsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(16, 10));

        myStatsArea.setEditable(false);
        globalStatsArea.setEditable(false);

        JPanel left = new JPanel(new BorderLayout(0, 6));
        left.add(new JLabel("My Stats"), BorderLayout.NORTH);
        left.add(new JScrollPane(myStatsArea), BorderLayout.CENTER);

        JPanel right = new JPanel(new BorderLayout(0, 6));
        right.add(new JLabel("Global"), BorderLayout.NORTH);
        right.add(new JScrollPane(globalStatsArea), BorderLayout.CENTER);

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

    @Override
    public void onShow() {
        if (mainFrame.getDataManager().getCurrentUserStats() != null) {
            myStatsArea.setText(mainFrame.getDataManager().getCurrentUserStats().formatStats());
        } else {
            myStatsArea.setText("");
        }
        globalStatsArea.setText(mainFrame.getDataManager().getGlobalStats().formatStats());
    }
}
