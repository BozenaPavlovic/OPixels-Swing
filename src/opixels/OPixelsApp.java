package opixels;

import opixels.ui.core.MainFrame;

import javax.swing.SwingUtilities;

public class OPixelsApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}

// "Sve unutar ovih zagrada izvrši na posebnoj niti (Threadu) zaduženoj za grafiku"