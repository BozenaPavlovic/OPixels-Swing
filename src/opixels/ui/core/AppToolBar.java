package opixels.ui.core;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class AppToolBar extends JToolBar {
    private final JLabel userLabel = new JLabel("User: -");
    private final JButton backButton = new JButton("Back to Games");
    private final JButton statsButton = new JButton("Stats");
    private final JButton logoutButton = new JButton("Log out");
    private final JLabel statusLabel = new JLabel("Status: Slobodan");
    private ToolBarListener listener;

    public AppToolBar() {
        setFloatable(false);
        setLayout(new BorderLayout());

        // Lijevi dio: Gumbi za navigaciju
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        navPanel.setOpaque(false);
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

        // Srednji dio: Status (centrirano)
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Desni dio: Korisničko ime i Logout gumb
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

        // Dodavanje u BorderLayout (svaka pozicija ima svoju komponentu)
        add(navPanel, BorderLayout.WEST);
        add(statusLabel, BorderLayout.CENTER);
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

    public void setStatusText(String status) {
        if (status != null && !status.trim().isEmpty()) {
            this.statusLabel.setText(status);
        } else {
            this.statusLabel.setText("Status: Slobodan");
        }
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }
}