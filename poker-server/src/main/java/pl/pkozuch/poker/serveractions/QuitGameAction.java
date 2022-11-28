package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.logic.NoSuchPlayerException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class QuitGameAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "QUIT";

    QuitGameAction(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args != null)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalActionException if players is not a game member or game is already in progress
     */
    @Override
    protected void validate() throws IllegalActionException {
        if (playerWrapper.getGameID() == null)
            throw new IllegalActionException("Nie jesteś członkiem żadnej gry. Użyj CREATE <ante> lub JOIN <id_gry> aby dołączyć do lobby.");

        Game g = server.getGame(playerWrapper.getGameID());

        if (g.getStatus().equals("W trakcie"))
            throw new IllegalActionException("Nie możesz opuścić gry w trakcie");

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

            if (g.getPlayersCount() > 0) {
                g.setHostID(g.getAllPlayers().get(0).getId());
            } else {
                g.setHostID(null);
            }

        } catch (NoSuchPlayerException e) {
            // Will never happen
        }
    }
}
