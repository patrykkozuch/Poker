package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

public class StartGameAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "START";

    /**
     * @param server        {@link ServerAction#server}
     * @param playerWrapper {@link ServerAction#playerWrapper}
     * @throws IllegalArgumentException if {@code args} is not null
     */
    StartGameAction(Server server, PlayerWrapper playerWrapper, String[] args) throws IllegalArgumentException {
        super(server, playerWrapper);

        if (args != null)
            throw new IllegalArgumentException("Nieprawidłowa liczba argumentów");
    }

    /**
     * {@inheritDoc}
     *
     * @throws IllegalActionException if player is not a member of any game or is not a host of game
     */
    @Override
    protected void validate() throws IllegalActionException {
        if (playerWrapper.getGameID() == null)
            throw new IllegalActionException("Nie jesteś aktualnie członkiem żadnej gry.");

        if (server.getGame(playerWrapper.getGameID()).getHostID() != playerWrapper.getPlayer().getId())
            throw new IllegalActionException("Nie jesteś hostem gry. Grę może rozpocząć tylko jej host.");
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        server.getGame(playerWrapper.getGameID()).startGame();
    }
}
