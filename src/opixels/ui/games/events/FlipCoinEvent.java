package opixels.ui.games.events;

public class FlipCoinEvent {
    private final String choice;

    public FlipCoinEvent(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }
}
