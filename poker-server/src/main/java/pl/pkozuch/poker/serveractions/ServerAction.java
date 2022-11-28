package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public abstract class ServerAction {

    /**
     * Server, on which action should be performed
     */
    protected final Server server;

    /**
     * Player wrapper containing channel and player who performs action
     */
    protected final PlayerWrapper playerWrapper;

    /**
     * @param server        {@link ServerAction#server}
     * @param playerWrapper {@link ServerAction#playerWrapper}
     */
    ServerAction(Server server, PlayerWrapper playerWrapper) {
        this.server = server;
        this.playerWrapper = playerWrapper;
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
    }
}
