package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.GameController;
import pl.pkozuch.poker.logic.Player;

public abstract class Action {

    /**
     * Player, who performs an Action
     */
    protected final Player player;

    /**
     * GameController, on which action should be performed
     */
    protected final GameController gameController;

    Action(GameController gameController, Player player) {
        this.player = player;
        this.gameController = gameController;
    }

    /**
     * Checks if action is valid in terms of business logic correctness
     *
     * @throws IllegalActionException if action should not be performed
     */
    protected abstract void validate() throws IllegalActionException;

    /**
     * Makes an action. During make action is being validated.
     *
     * @throws IllegalActionException if action should not be performed
     */
    public void make() throws IllegalActionException {
        validate();
        sendMessageToOtherPlayers();
    }

    /**
     * Sends message to other players in same lobby as {@link Action#player}
     */
    public abstract void sendMessageToOtherPlayers();
}
