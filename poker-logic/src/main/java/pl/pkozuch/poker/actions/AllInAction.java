package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public class AllInAction extends Action {
    AllInAction(GameController gameController, Player player) {
        super(gameController, player);
    }

    @Override
    public void validate() throws IllegalActionException {
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
