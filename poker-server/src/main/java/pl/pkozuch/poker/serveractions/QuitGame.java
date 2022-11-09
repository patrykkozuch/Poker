package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class QuitGame extends ServerAction {
    QuitGame(Server server, PlayerWrapper playerWrapper, String[] args) {
        super(server, playerWrapper);

        if (args != null)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() {
        if (playerWrapper.getGameID() == null)
            throw new RuntimeException("Nie jesteś członkiem żadnej gry. Użyj CREATE <ante> lub JOIN <id_gry> aby dołączyć do lobby.");
    }

    @Override
    public void make() {
        super.make();

        try {
            Game g = server.getGame(playerWrapper.getGameID());
            g.removePlayer(playerWrapper.getPlayer().getId());

            if (g.getStatus().equals("Nierozpoczęta"))
                playerWrapper.getPlayer().raiseBalance(g.getAnte());

            playerWrapper.setGameID(null);
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się opuścić gry. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "QUIT";
    }
}
