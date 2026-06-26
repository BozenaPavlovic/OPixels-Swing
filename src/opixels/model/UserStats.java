package opixels.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserStats implements Serializable {

    private static final long serialVersionUID = 1L;

    private int totalGames;
    private int totalWins;
    private int totalLosses;
    private int totalDraws;

    private int flipCoinGames;
    private int flipCoinWins;
    private int flipCoinLosses;

    private int speedClickerGames;
    private int speedClickerBestScore;

    private int rpsGames;
    private int rpsWins;
    private int rpsLosses;
    private int rpsDraws;

    private int guessGames;
    private int guessWins;
    private int guessLosses;

    private final List<String> playHistory = new ArrayList<>();

    public void recordFlipCoin(boolean win) {
        flipCoinGames++;
        totalGames++;
        if (win) {
            flipCoinWins++;
            totalWins++;
            playHistory.add("Flip Coin - Pobjeda");
        } else {
            flipCoinLosses++;
            totalLosses++;
            playHistory.add("Flip Coin - Poraz");
        }
    }

    public void recordSpeedClicker(int clicks) {
        speedClickerGames++;
        totalGames++;
        if (clicks > speedClickerBestScore) {
            speedClickerBestScore = clicks;
        }
        if (clicks >= 10) {
            totalWins++;
            playHistory.add("Speed Clicker - Pobjeda (" + clicks + " klikova)");
        } else {
            totalLosses++;
            playHistory.add("Speed Clicker - Poraz (" + clicks + " klikova)");
        }
    }

    public void recordRockPaperScissors(String result) {
        rpsGames++;
        totalGames++;
        if ("Pobjeda".equals(result)) {
            rpsWins++;
            totalWins++;
        } else if ("Poraz".equals(result)) {
            rpsLosses++;
            totalLosses++;
        } else {
            rpsDraws++;
            totalDraws++;
        }
        playHistory.add("Rock Paper Scissors - " + result);
    }

    public void recordGuessNumber(boolean win, int attempts) {
        guessGames++;
        totalGames++;
        if (win) {
            guessWins++;
            totalWins++;
            playHistory.add("Guess the Number - Pobjeda (" + attempts + " pokusaja)");
        } else {
            guessLosses++;
            totalLosses++;
            playHistory.add("Guess the Number - Poraz");
        }
    }

    public String formatStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("UKUPNO:\n");
        sb.append("Odigrano: ").append(totalGames).append("\n");
        sb.append("Pobjede: ").append(totalWins).append("\n");
        sb.append("Porazi: ").append(totalLosses).append("\n");
        sb.append("Nerijeseno: ").append(totalDraws).append("\n\n");
        sb.append("PO IGRAMA:\n");
        sb.append("Flip Coin - Games: ").append(flipCoinGames)
                .append(", Win: ").append(flipCoinWins)
                .append(", Loss: ").append(flipCoinLosses).append("\n");
        sb.append("Speed Clicker - Games: ").append(speedClickerGames)
                .append(", Best Score: ").append(speedClickerBestScore).append("\n");
        sb.append("Rock Paper Scissors - Games: ").append(rpsGames)
                .append(", Win: ").append(rpsWins)
                .append(", Loss: ").append(rpsLosses)
                .append(", Draw: ").append(rpsDraws).append("\n");
        sb.append("Guess the Number - Games: ").append(guessGames)
                .append(", Win: ").append(guessWins)
                .append(", Loss: ").append(guessLosses).append("\n\n");
        sb.append("PRETHODNO ODIGRANE IGRE:\n");
        if (playHistory.isEmpty()) {
            sb.append("(nema)\n");
        } else {
            for (String entry : playHistory) {
                sb.append("- ").append(entry).append("\n");
            }
        }
        return sb.toString();
    }
}
