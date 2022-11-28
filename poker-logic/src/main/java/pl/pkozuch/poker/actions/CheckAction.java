package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

/**
 * Check Action means that Player can check as long as anybody bets.
 * <p>
 * Cannot be made if other Player has already bet.
 */
public class CheckAction extends Action {
    CheckAction(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    protected void validate() throws IllegalActionException {
        if (gameController.getRoundState() != GameController.possibleRoundStates.BETTING && gameController.getRoundState() != GameController.possibleRoundStates.SECOND_BETTING)
            throw new IllegalActionException("Czekać możesz tylko w fazie obstawiania.");

        if (gameController.doesSomeoneBetThisRound())
            throw new IllegalActionException("Nie możesz czekać, jeżeli ktoś już podbił stawkę.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        player.check();
    }

    @Override
    public void sendMessageToOtherPlayers() {
        gameController.sendMessageToAllPlayersWithout(
                player,
                String.format("Gracz Player %1d czeka", player.getId())
        );
    }
}
