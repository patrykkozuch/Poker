package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

public class QuitGame extends ServerAction {
    QuitGame(Server server, ServerThread playerThread, String[] args) {
        super(server, playerThread);

        if (args != null)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() {
        if (playerThread.getGameID() == null)
            throw new RuntimeException("Nie jesteś członkiem żadnej gry. Użyj CREATE <ante> lub JOIN <id_gry> aby dołączyć do lobby.");
    }

    @Override
    public void make() {
        super.make();

        try {
            Game g = server.getGame(playerThread.getGameID());
            g.removePlayer(playerThread.getPlayer().getId());

            if (g.getStatus().equals("Nierozpoczęta"))
                playerThread.getPlayer().raiseBalance(g.getAnte());

            playerThread.setGameID(null);
        } catch (Exception e) {
            playerThread.sendMessageToPlayer("Nie udało się opuścić gry. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "QUIT";
    }
}
