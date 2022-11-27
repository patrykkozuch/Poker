package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class ServerStub extends Server {

    public void addPlayer(PlayerWrapper p) {
        players.put(p.getPlayer().getId(), p);
    }
}
