package opixels.ui.games.events;

public class SpeedClickerEvent {
    private final String action;

    public SpeedClickerEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
