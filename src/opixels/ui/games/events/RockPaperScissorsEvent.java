package opixels.ui.games.events;

public class RockPaperScissorsEvent {
    private final String choice;

    public RockPaperScissorsEvent(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return choice;
    }
}
