package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public abstract class ServerAction {

    protected final Server server;
    protected final PlayerWrapper playerWrapper;

    ServerAction(Server server, PlayerWrapper playerWrapper) {
        this.server = server;
        this.playerWrapper = playerWrapper;
    }

    public abstract void validate() throws IllegalActionException;

    public void make() throws IllegalActionException {
        validate();
    }

    public static String getHelpString() {
        return null;
    }
}
