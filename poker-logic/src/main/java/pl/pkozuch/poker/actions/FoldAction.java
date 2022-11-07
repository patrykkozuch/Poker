package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public class FoldAction extends Action {
    FoldAction(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    public void validate() {
        if (gameController.getRoundState() != GameController.possibleRoundStates.BETTING && gameController.getRoundState() != GameController.possibleRoundStates.SECOND_BETTING)
            throw new RuntimeException("Możesz spasować tylko w trakcie obstawiania.");
    }

    @Override
    public void make() {
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
