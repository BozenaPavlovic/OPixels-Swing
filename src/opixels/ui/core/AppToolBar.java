package opixels.ui.core;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class AppToolBar extends JToolBar {
    private final JLabel userLabel = new JLabel("User: -");
    private final JButton backButton = new JButton("Back to Games");
    private final JButton statsButton = new JButton("Stats");
    private final JButton logoutButton = new JButton("Log out");
    private ToolBarListener listener;

    public AppToolBar() {
        setFloatable(false);
        setLayout(new BorderLayout());

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        backButton.addActionListener(e -> {
            if (listener != null) {
                listener.onBackClicked();
            }
        });
        statsButton.addActionListener(e -> {
            if (listener != null) {
                listener.onStatsClicked();
            }
        });
        navPanel.add(backButton);
        navPanel.add(statsButton);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        userPanel.setOpaque(false);
        userLabel.setHorizontalAlignment(JLabel.RIGHT);
        logoutButton.addActionListener(e -> {
            if (listener != null) {
                listener.onLogoutClicked();
            }
        });
        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        add(navPanel, BorderLayout.WEST);
        add(userPanel, BorderLayout.EAST);
    }

    public void setListener(ToolBarListener listener) {
        this.listener = listener;
    }

    public void updateUserLabel(String username) {
        if (username != null && !username.isEmpty()) {
            userLabel.setText("User: " + username);
        } else {
            userLabel.setText("User: -");
        }
    }

    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}
