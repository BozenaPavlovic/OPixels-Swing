package opixels.ui.games.listeners;

import opixels.ui.games.events.FlipCoinEvent;

public interface FlipCoinListener {
    void onChoiceSubmitted(FlipCoinEvent event);
}
