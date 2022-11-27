package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class QuitGame extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "QUIT";

    QuitGame(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args != null)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() throws IllegalActionException {
        if (playerWrapper.getGameID() == null)
            throw new IllegalActionException("Nie jesteś członkiem żadnej gry. Użyj CREATE <ante> lub JOIN <id_gry> aby dołączyć do lobby.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        Game g = server.getGame(playerWrapper.getGameID());

        try {
            g.removePlayer(playerWrapper.getPlayer().getId());

            if (g.getStatus().equals("Nierozpoczęta"))
                playerWrapper.getPlayer().raiseBalance(g.getAnte());

            playerWrapper.setGameID(null);

        } catch (NoSuchPlayerException e) {
            playerWrapper.sendMessageToPlayer("Nie jesteś członkiem wybranej gry.");
        }
    }
}
