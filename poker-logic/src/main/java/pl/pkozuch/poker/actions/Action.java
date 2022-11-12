package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public abstract class Action {

    protected final Player player;
    protected final GameController gameController;

    Action(GameController gameController, Player player) {
        this.player = player;
        this.gameController = gameController;
    }

    public abstract void validate() throws IllegalActionException;

    public void make() throws IllegalActionException{
        validate();
        sendMessageToOtherPlayers();
    }

    public abstract void sendMessageToOtherPlayers();
}
