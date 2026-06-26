package opixels;

import opixels.ui.MainFrame;

import javax.swing.SwingUtilities;

public class OPixelsApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
