package opixels.ui.components; // Definira paket u kojem se nalazi grafička komponenta za prijavu.

// Uvoz klasa iz projekta koje upravljaju navigacijom i zaslonima
import opixels.ui.core.MainFrame;   // Glavni prozor aplikacije koji upravlja prebacivanjem ekrana i sadrži DataManager.
import opixels.ui.core.Screen;      // Sučelje (interface) koje definira metode getScreenName() i onShow().
import opixels.ui.core.ScreenNames; // Klasa ili Enum sa konstantom nazivâ svih ekrana u aplikaciji (npr. LOGIN, REGISTER).

// Uvoz Swing i AWT komponenata za izradu grafičkog sučelja (GUI)
import javax.swing.JButton;        // Gumb za pritisak (Prijava, Registracija).
import javax.swing.JLabel;         // Tekstualna oznaka na ekranu.
import javax.swing.JPanel;         // Kontejner koji drži ostale GUI komponente.
import javax.swing.JPasswordField; // Unosno polje za lozinku (sakriva znakove).
import javax.swing.JTextField;     // Unosno polje za običan tekst (korisničko ime).
import java.awt.*;                 // Klase za raspored elemenata (Layouts, Insets, Font...).

// Klasa LoginPanel nasljeđuje JPanel (što znači da je grafička ploča)
// i implementira sučelje Screen (što omogućuje polimorfno upravljanje ekranima preko MainFrame-a)
public class LoginPanel extends JPanel implements Screen {

    // Instanca glavnog prozora preko koje se pristupa DataManager-u i navigaciji ekrana
    private final MainFrame mainFrame;

    // GUI komponente za unos podataka i prikaz poruka
    private final JTextField usernameField = new JTextField(15);    // Polje za unos korisničkog imena (širina 15 stupaca)
    private final JPasswordField passwordField = new JPasswordField(15); // Polje za unos lozinke
    private final JLabel messageLabel = new JLabel(" ");            // Oznaka za prikaz poruka o pogrešci pri prijavi

    // KONSTRUKTOR - Poziva se pri stvaranju panela za prijavu
    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Spremanje referencije na glavni prozor

        // Postavljanje GridBagLayout-a (fleksibilna mreža za pozicioniranje komponenata)
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); // Mrežna ograničenja za pozicije
        gbc.insets = new Insets(8, 8, 8, 8); // Razmak od 8 piksela oko svake komponente
        gbc.fill = GridBagConstraints.HORIZONTAL; // Komponente se rastežu horizontalno

        // --- NASLOV (OPixels) ---
        JLabel title = new JLabel("OPixels", JLabel.CENTER); // Naslovni tekst u sredini
        title.setFont(new Font("Arial", Font.BOLD, 28));     // Postavljanje fonta (Arial, Podebljano, 28pt)
        gbc.gridx = 0;          // Prvi stupac (0)
        gbc.gridy = 0;          // Prvi redak (0)
        gbc.gridwidth = 2;      // Naslov zauzima 2 stupca širine
        gbc.anchor = GridBagConstraints.CENTER; // Pozicioniranje u sredinu
        add(title, gbc);        // Dodavanje naslova na panel s definiranim ograničenjima

        // --- OBLAST ZA KORISNIČKO IME ---
        gbc.gridwidth = 1;      // Vraćanje širine na 1 stupac
        gbc.gridx = 0;          // Stupac 0 (lijevo)
        gbc.gridy = 1;          // Redak 1
        gbc.anchor = GridBagConstraints.WEST; // Poravnanje uz lijevi rub
        add(new JLabel("Korisničko ime:"), gbc); // Dodavanje oznake "Korisničko ime:"

        gbc.gridx = 1;          // Stupac 1 (desno od oznake)
        gbc.anchor = GridBagConstraints.CENTER;
        add(usernameField, gbc); // Dodavanje polja za unos korisničkog imena

        // --- OBLAST ZA LOZINKU ---
        gbc.gridx = 0;          // Stupac 0 (lijevo)
        gbc.gridy = 2;          // Redak 2
        gbc.anchor = GridBagConstraints.WEST;
        add(new JLabel("Lozinka:"), gbc); // Dodavanje oznake "Lozinka:"

        gbc.gridx = 1;          // Stupac 1 (desno od oznake)
        gbc.anchor = GridBagConstraints.CENTER;
        add(passwordField, gbc); // Dodavanje polja za unos lozinke

        // --- GUMB ZA PRIJAVU I REGISTRACIJU ---
        JButton loginButton = new JButton("PRIJAVA"); // Stvaranje gumba "PRIJAVA"
        loginButton.addActionListener(e -> login());  // Dodavanje slušača koji pritiskom na gumb poziva metodu login()

        JButton registerButton = new JButton("REGISTRIRAJ"); // Stvaranje gumba "REGISTRIRAJ"
        // Klikom na registraciju obavještava se MainFrame da prebaci prikaz na ekran za registraciju
        registerButton.addActionListener(e -> mainFrame.showScreen(ScreenNames.REGISTER));

        // Mali pomoćni panel s FlowLayout-om koji drži dva gumba jedan pokraj drugog
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.add(loginButton);    // Dodavanje gumba za prijavu
        buttonPanel.add(registerButton); // Dodavanje gumba za registraciju

        gbc.gridx = 0;          // Stupac 0
        gbc.gridy = 3;          // Redak 3
        gbc.gridwidth = 2;      // Panel s gumbima se rasteže preko oba stupca
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);   // Dodavanje panela s gumbima na glavni panel

        // --- PORUKA O STATUSU / GREŠCI ---
        messageLabel.setHorizontalAlignment(JLabel.CENTER); // Centriranje teksta u poruci
        gbc.gridx = 0;          // Stupac 0
        gbc.gridy = 4;          // Redak 4
        gbc.gridwidth = 2;      // Rastezanje preko 2 stupca
        gbc.anchor = GridBagConstraints.CENTER;
        add(messageLabel, gbc); // Dodavanje oznake za poruke na dno
    }

    // PRIVATNA LOGIKA ZA OBRADU PRIJAVE
    private void login() {
        // Preuzimanje teksta iz unosnih polja (trim uklanja praznine na početku i kraju)
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Poziva se login metoda DataManager-a preko MainFrame-a.
        // Ako vjerodajnice odgovaraju korisniku u mapi 'users', vraća se objekt User (nije null).
        if (mainFrame.getDataManager().login(username, password) != null) {
            messageLabel.setText(" ");       // Prazni se poruka o pogrešci
            passwordField.setText("");      // Briše se unesena lozinka radi sigurnosti
            mainFrame.showScreen(ScreenNames.MAIN_MENU); // Prebacuje se prikaz na glavni izbornik
        } else {
            // Ako su korisničko ime ili lozinka pogrešni (metoda login() je vratila null)
            messageLabel.setText("Pogrešno korisničko ime ili lozinka.");
        }
    }

    // PROPISE IZ SUČELJA Screen: Vraća jedinstveni identifikator ovog ekrana
    @Override
    public String getScreenName() {
        return ScreenNames.LOGIN;
    }

    // PROPISE IZ SUČELJA Screen: Poziva se automatski kad god MainFrame prikaže ovaj ekran
    @Override
    public void onShow() {
        usernameField.setText(""); // Resetira/prazni unos korisničkog imena
        passwordField.setText(""); // Resetira/prazni unos lozinke
        messageLabel.setText(" ");  // Briše sve prethodne poruke o greškama
    }
}