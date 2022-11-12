package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public class CheckAction extends Action {
    CheckAction(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    public void validate() throws IllegalActionException {
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
