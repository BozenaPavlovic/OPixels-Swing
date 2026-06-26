package opixels.ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridLayout;

public class LoginPanel extends JPanel implements Screen {

    private final MainFrame mainFrame;
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel messageLabel = new JLabel(" ");

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(6, 1));

        add(new JLabel("Prijava"));
        add(new JLabel("Korisnicko ime:"));
        add(usernameField);
        add(new JLabel("Lozinka:"));
        add(passwordField);

        JPanel buttons = new JPanel();
        JButton loginButton = new JButton("PRIJAVA");
        loginButton.addActionListener(e -> login());
        JButton registerButton = new JButton("REGISTRIRAJ");
        registerButton.addActionListener(e -> mainFrame.showScreen(ScreenNames.REGISTER));
        buttons.add(loginButton);
        buttons.add(registerButton);

        add(buttons);
        add(messageLabel);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (mainFrame.getDataManager().login(username, password) != null) {
            messageLabel.setText(" ");
            passwordField.setText("");
            mainFrame.showScreen(ScreenNames.MAIN_MENU);
        } else {
            messageLabel.setText("Pogresno korisnicko ime ili lozinka.");
        }
    }

    @Override
    public String getScreenName() {
        return ScreenNames.LOGIN;
    }

    @Override
    public void onShow() {
        usernameField.setText("");
        passwordField.setText("");
        messageLabel.setText(" ");
    }
}
