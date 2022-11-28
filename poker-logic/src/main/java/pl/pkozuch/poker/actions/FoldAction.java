package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

/**
 * Fold Action means that Player do not want to play during this round anymore.
 * <p>
 * After fold, player remains inactive and cannot win any prize.
 * <p>
 * Can be performed only during betting phase.
 */
public class FoldAction extends Action {
    FoldAction(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    protected void validate() throws IllegalActionException {
        if (gameController.getRoundState() != GameController.possibleRoundStates.BETTING && gameController.getRoundState() != GameController.possibleRoundStates.SECOND_BETTING)
            throw new IllegalActionException("Możesz spasować tylko w trakcie obstawiania.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        player.fold();
    }

    @Override
    public void sendMessageToOtherPlayers() {
        gameController.sendMessageToAllPlayersWithout(
                player,
                String.format("Gracz Player %1d spasował", player.getId())
        );
    }
}
