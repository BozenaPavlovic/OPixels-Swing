package opixels.model;

public enum GameType {
    FLIP_COIN("Flip Coin"),
    SPEED_CLICKER("Speed Clicker"),
    ROCK_PAPER_SCISSORS("Rock Paper Scissors"),
    GUESS_NUMBER("Guess the Number");

    private final String displayName;

    GameType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
// enum (enumeration) je posebna vrsta klase koja sadrži fiksni popis konstanti (stvari koje se ne mijenjaju)
// gotov popis fiksnih opcija koji sprječava greške u pisanju i osigurava da program koristi samo točno definirane igre

// enum se korisit kao fiksni popis (npr. GameType.FLIP_COIN) kako bi se izbjehli
// tipfeleri i omogućilo Javi da odmah upozori ako upišemo nepostojeću opciju

//Upišeš je bilo gdje u kodu dok programiraš (npr. unutar if provjere ili dok postavljaš aktivnu igru)
//Ako umjesto GameType.FLIP_COIN slučajno utipkaš GameType.FLIP_COJN,
// Java će odmah podvući kod crvenom bojom i javiti grešku prije nego uopće pokreneš program