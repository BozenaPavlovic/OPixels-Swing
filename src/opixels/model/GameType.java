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
