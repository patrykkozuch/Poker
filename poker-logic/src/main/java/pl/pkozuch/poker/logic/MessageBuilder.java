package pl.pkozuch.poker.logic;

import java.util.List;

/**
 * Utility class meant to prepare all mesage for users
 *
 * @param gameController
 */
public record MessageBuilder(GameController gameController) {

    public static String buildChangeMessageForPlayer = "Wybierz, które karty chcesz wymienić.\nPodaj numery kart, oddzielając je spacjami, np: CHANGE 2 4";

    /**
     * Builds header message. Contains:
     *
     * <ul>
     *     <li>Nicknames of players</li>
     *     <li>Balance of players</li>
     *     <li>Current bet made by each player</li>
     * </ul>
     *
     * @return header message
     */
    public String buildHeaderMessage() {
        List<Player> players = gameController.getGame().getAllPlayers();

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

    /**
     * Builds card message for a player. For {@code currentPlayer} cards are shown,
     * for others cards are shown as '######'
     *
     * @param currentPlayer Player for whom message is built
     * @return card message
     */
    public String buildCardMessage(Player currentPlayer) {

        List<Player> players = gameController.getGame().getAllPlayers();

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

    /**
     * Builds bet message for player. Contains possible actions which can be made by player.
     *
     * @param p Player who makes action
     * @return action string provided by Player
     */
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
}
