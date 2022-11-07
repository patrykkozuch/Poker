package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

public class StartGame extends ServerAction {

    StartGame(Server server, ServerThread playerThread, String[] args) {
        super(server, playerThread);

        if (args != null)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() {
        if (server.getGame(playerThread.getGameID()).getHostID() != playerThread.getPlayer().getId())
            throw new RuntimeException("Nie jesteś hostem gry. Grę może rozpocząć tylko jej host.");
    }

    @Override
    public void make() {
        super.make();

        try {
            server.getGame(playerThread.getGameID()).startGame();
        } catch (Exception e) {
            playerThread.sendMessageToPlayer("Nie udało się rozpocząć gry. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "START";
    }
}
