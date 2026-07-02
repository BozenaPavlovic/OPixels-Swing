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
import java.awt.*;

public class RegisterPanel extends JPanel implements Screen {

    private final MainFrame mainFrame;
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JRadioButton maleButton = new JRadioButton("Muško", true);
    private final JRadioButton femaleButton = new JRadioButton("Žensko");
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

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Registracija", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Korisničko ime:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Lozinka:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Spol:"), gbc);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Omiljena igra:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(favoriteGameBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Interesi:"), gbc);

        JPanel interestsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        interestsPanel.add(casualBox);
        interestsPanel.add(competitiveBox);
        interestsPanel.add(bothBox);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(interestsPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("O meni:"), gbc);

        JScrollPane scrollPane = new JScrollPane(aboutArea);
        scrollPane.setPreferredSize(new Dimension(200, 80));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(scrollPane, gbc);

        JButton registerButton = new JButton("REGISTRIRAJ");
        registerButton.addActionListener(e -> register());

        JButton backButton = new JButton("NATRAG");
        backButton.addActionListener(e -> mainFrame.showScreen(ScreenNames.LOGIN));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageLabel, gbc);
    }

    private void register() {
        String gender = maleButton.isSelected() ? "Muško" : "Žensko";
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