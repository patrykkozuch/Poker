package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public class CallAction extends Action {
    private final Integer amount;

    CallAction(GameController gameController, Player player) {
        super(gameController, player);

        amount = gameController.getCurrentRoundBetPerPlayer() - player.getBetInCurrentRound();
    }

    @Override
    public void validate() throws IllegalActionException{
        if (gameController.getRoundState() != GameController.possibleRoundStates.BETTING && gameController.getRoundState() != GameController.possibleRoundStates.SECOND_BETTING)
            throw new IllegalActionException("Możesz wyrównać tylko w trakcie obstawiania.");

        if (gameController.getCurrentRoundBetPerPlayer() == 0)
            throw new IllegalActionException("Nie możesz wyrównać, jeżeli nikt jeszcze nie obstawił!");

        if (player.getBalance() < amount)
            throw new IllegalActionException("Nie możesz wyrównać, stan twojego konta jest zbyt mały.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        player.reduceBalance(amount);
        player.raiseBetInCurrentRound(amount);
        gameController.addToReward(amount);
    }

    @Override
    public void sendMessageToOtherPlayers() {
        gameController.sendMessageToAllPlayersWithout(
                player,
                String.format("Gracz Player %1d wyrównał.", player.getId())
        );
    }
}
