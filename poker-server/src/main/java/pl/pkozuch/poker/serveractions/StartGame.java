package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class StartGame extends ServerAction {

    StartGame(Server server, PlayerWrapper playerWrapper, String[] args) {
        super(server, playerWrapper);

        if (args != null)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() {
        if (playerWrapper.getGameID() == null)
            throw new RuntimeException("Nie jesteś aktualnie członkiem żadnej gry.");
        
        if (server.getGame(playerWrapper.getGameID()).getHostID() != playerWrapper.getPlayer().getId())
            throw new RuntimeException("Nie jesteś hostem gry. Grę może rozpocząć tylko jej host.");
    }

    @Override
    public void make() {
        super.make();

        try {
            server.getGame(playerWrapper.getGameID()).startGame();
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się rozpocząć gry. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "START";
    }
}
