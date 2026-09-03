package opixels.ui.games.listeners;

import opixels.ui.games.events.GuessNumberEvent;

public interface GuessNumberListener {
    void onGuessSubmitted(GuessNumberEvent event);
    void onStartGame();
}
