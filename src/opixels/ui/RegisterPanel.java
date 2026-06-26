package opixels.ui;

import opixels.model.GameType;
import opixels.model.User;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class RegisterPanel extends JPanel implements Screen {

    private final MainFrame mainFrame;
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JRadioButton maleButton = new JRadioButton("Musko", true);
    private final JRadioButton femaleButton = new JRadioButton("Zensko");
    private final JComboBox<String> favoriteGameBox = new JComboBox<>(new String[]{
            GameType.FLIP_COIN.getDisplayName(),
            GameType.SPEED_CLICKER.getDisplayName(),
            GameType.ROCK_PAPER_SCISSORS.getDisplayName(),
            GameType.GUESS_NUMBER.getDisplayName()
    });
    private final JCheckBox casualBox = new JCheckBox("Casual Gaming");
    private final JCheckBox competitiveBox = new JCheckBox("Competitive Gaming");
    private final JCheckBox bothBox = new JCheckBox("Both");
    private final JTextArea aboutArea = new JTextArea(4, 20);
    private final JLabel messageLabel = new JLabel(" ");

    public RegisterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        JPanel form = new JPanel(new GridLayout(0, 1));
        form.add(new JLabel("Registracija"));
        form.add(new JLabel("Korisnicko ime:"));
        form.add(usernameField);
        form.add(new JLabel("Lozinka:"));
        form.add(passwordField);
        form.add(new JLabel("Spol:"));
        JPanel genderPanel = new JPanel();
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        form.add(genderPanel);
        form.add(new JLabel("Omiljena igra:"));
        form.add(favoriteGameBox);
        form.add(new JLabel("Interesi:"));
        JPanel interestsPanel = new JPanel();
        interestsPanel.add(casualBox);
        interestsPanel.add(competitiveBox);
        interestsPanel.add(bothBox);
        form.add(interestsPanel);
        form.add(new JLabel("O meni:"));
        form.add(new JScrollPane(aboutArea));

        JPanel buttons = new JPanel();
        JButton registerButton = new JButton("REGISTRIRAJ");
        registerButton.addActionListener(e -> register());
        JButton backButton = new JButton("NATRAG");
        backButton.addActionListener(e -> mainFrame.showScreen(ScreenNames.LOGIN));
        buttons.add(registerButton);
        buttons.add(backButton);

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);
    }

    private void register() {
        String gender = maleButton.isSelected() ? "Musko" : "Zensko";
        String interests = buildInterests();

        User user = new User(
                usernameField.getText().trim(),
                new String(passwordField.getPassword()),
                gender,
                (String) favoriteGameBox.getSelectedItem(),
                interests,
                aboutArea.getText().trim()
        );

        String error = mainFrame.getDataManager().register(user);
        if (error == null) {
            messageLabel.setText("Registracija uspjesna. Mozete se prijaviti.");
            mainFrame.showScreen(ScreenNames.LOGIN);
        } else {
            messageLabel.setText(error);
        }
    }

    private String buildInterests() {
        StringBuilder sb = new StringBuilder();
        if (casualBox.isSelected()) {
            sb.append("Casual Gaming");
        }
        if (competitiveBox.isSelected()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("Competitive Gaming");
        }
        if (bothBox.isSelected()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("Both");
        }
        if (sb.length() == 0) {
            return "N/A";
        }
        return sb.toString();
    }

    @Override
    public String getScreenName() {
        return ScreenNames.REGISTER;
    }

    @Override
    public void onShow() {
        usernameField.setText("");
        passwordField.setText("");
        aboutArea.setText("");
        casualBox.setSelected(false);
        competitiveBox.setSelected(false);
        bothBox.setSelected(false);
        maleButton.setSelected(true);
        messageLabel.setText(" ");
    }
}
