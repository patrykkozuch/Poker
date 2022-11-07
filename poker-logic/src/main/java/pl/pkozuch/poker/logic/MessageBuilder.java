package pl.pkozuch.poker.logic;

import java.util.ArrayList;
import java.util.List;

public class MessageBuilder {
    private final GameController gameController;

    MessageBuilder(GameController gameController) {
        this.gameController = gameController;
    }

    public String buildHeaderMessage() {
        ArrayList<Player> players = gameController.getGame().getAllPlayers();

        List<String> playersNicknames = players.stream().map(p -> {
            String s = "Player " + p.getId();
            if (p.doesFold())
                s += "(F)";
            return s;
        }).toList();

        List<String> playersBalance = players.stream().map(p -> p.getBalance().toString()).toList();
        List<String> playersCurrentBet = players.stream().map(p -> p.getBetInCurrentRound().toString()).toList();

        String playersNicknamesLine = String.join("\t", playersNicknames) + "\n";
        String playersBalanceLine = String.join("\t\t\t", playersBalance) + "\n";
        String playersCurrentBetLine = String.join("\t\t\t", playersCurrentBet) + "\n";

        return playersNicknamesLine + playersBalanceLine + playersCurrentBetLine;
    }

    public String buildCardMessage(Player currentPlayer) {

        ArrayList<Player> players = gameController.getGame().getAllPlayers();

        StringBuilder cardsSummary = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            for (Player p : players) {
                cardsSummary.append(i + 1).append(". ");

                if (p != currentPlayer && gameController.getRoundState() != GameController.possibleRoundStates.END) {
                    cardsSummary.append("########\t");
                } else {
                    cardsSummary.append(p.cards[i]).append("\t");
                }
            }
            cardsSummary.append("\n");
        }

        return cardsSummary.toString();
    }

    public String buildBetMessageForPlayer(Player p) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Wybierz, co chcesz zrobić: \n");

        if (!gameController.doesSomeoneBetThisRound()) {
            stringBuilder.append("CHECK\n");
        }

        if (p.getBalance() < gameController.getCurrentRoundBetPerPlayer()) {
            stringBuilder.append("Ponieważ ktoś obstawił za więcej niż masz na koncie, możesz tylko obstawić ALL IN albo spasować.\n");
            stringBuilder.append("ALLIN\n");
        } else {
            stringBuilder.append("RAISE <kwota>\n");
            stringBuilder.append("CALL\n");
        }

        stringBuilder.append("FOLD\n");

        return stringBuilder.toString();
    }

    public String buildChangeMessageForPlayer(Player p) {
        return "Wybierz, które karty chcesz wymienić.\nPodaj numery kart, oddzielając je spacjami, np: CHANGE 2 4";
    }
}
