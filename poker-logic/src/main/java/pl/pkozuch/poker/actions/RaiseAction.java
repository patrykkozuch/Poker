package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public class RaiseAction extends Action {

    private final Integer amount;
    private final Integer providedAmount;

    RaiseAction(GameController gameController, Player player, String[] args) {
        super(gameController, player);

        if (args.length != 1 || !IntValidator.isInt(args[0]))
            throw new RuntimeException("Nieprawidłowa akcja. Spróbuj ponownie");

        providedAmount = Integer.parseInt(args[0]);

        amount = providedAmount - player.getBetInCurrentRound();
    }

    @Override
    public void validate() {
        if (gameController.getRoundState() != GameController.possibleRoundStates.BETTING && gameController.getRoundState() != GameController.possibleRoundStates.SECOND_BETTING)
            throw new RuntimeException("Podnosić stawkę możesz tylko w fazie obstawiania.");

        if (providedAmount < gameController.getCurrentRoundBetPerPlayer())
            throw new RuntimeException("Nie możesz obstawiać za mniej niż obstawili inni gracze. " +
                    "Aktualnie minimalna kwota to: " + gameController.getCurrentRoundBetPerPlayer());

        if (player.getBalance() < amount)
            throw new RuntimeException("Nie możesz obstawiać za więcej niż masz. Spróbuj jeszcze raz zmniejszając kwotę");
    }

    @Override
    public void make() {
        super.make();

        player.reduceBalance(amount);
        player.raiseBetInCurrentRound(amount);

        gameController.addToReward(amount);
        gameController.setCurrentRoundBetPerPlayer(player.getBetInCurrentRound());
    }

    @Override
    public void sendMessageToOtherPlayers() {
        gameController.sendMessageToAllPlayersWithout(
                player,
                String.format("Gracz Player %1d podbił do %2d", player.getId(), providedAmount)
        );
    }
}
