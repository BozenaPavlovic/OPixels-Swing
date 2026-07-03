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

// "Sve unutar ovih zagrada izvrši na posebnoj niti (Threadu) zaduženoj za grafiku"
//        obavezno u Javi kako ti se prozor ne bi zamrznuo ili se ponašao čudno
//        SwingUtilities.invokeLater(() -> {