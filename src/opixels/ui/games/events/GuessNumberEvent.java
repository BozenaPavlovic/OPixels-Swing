package opixels.ui.games.events;

public class GuessNumberEvent {
    private final int guess;

    public GuessNumberEvent(int guess) {
        this.guess = guess;
    }

    public int getGuess() {
        return guess;
    }
}
