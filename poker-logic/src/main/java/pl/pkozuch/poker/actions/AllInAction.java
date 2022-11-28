package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

/**
 * All-In bet Action - means that player bet all the money they have.
 * <p>
 * After going All-In player remains in game, but cannot bet higher or fold.
 * <p>
 * Can be performed only during betting phases.
 */
public class AllInAction extends Action {
    AllInAction(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    public void validate() throws IllegalActionException {
        if (gameController.getRoundState() != GameController.possibleRoundStates.BETTING && gameController.getRoundState() != GameController.possibleRoundStates.SECOND_BETTING)
            throw new IllegalActionException("Możesz wejsć ALL IN tylko w trakcie obstawiania.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();
        if (player.getBalance() >= gameController.getCurrentRoundBetPerPlayer()) {
            gameController.setCurrentRoundBetPerPlayer(player.getBalance());
        }

        player.raiseBetInCurrentRound(player.getBalance());

        player.reduceBalance(player.getBalance());
        player.betAllIn();
    }

    @Override
    public void sendMessageToOtherPlayers() {
        gameController.sendMessageToAllPlayersWithout(
                player,
                String.format("Gracz Player %1d wszedł ALL IN", player.getId())
        );
    }
}
