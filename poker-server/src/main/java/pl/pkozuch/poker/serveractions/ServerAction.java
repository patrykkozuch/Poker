package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

public abstract class ServerAction {

    protected final ServerThread playerThread;
    protected final Server server;

    ServerAction(Server server, ServerThread playerThread) {
        this.server = server;
        this.playerThread = playerThread;
    }

    public abstract void validate();

    public void make() {
        validate();
    }

    public static String getHelpString() {
        return null;
    }
}
