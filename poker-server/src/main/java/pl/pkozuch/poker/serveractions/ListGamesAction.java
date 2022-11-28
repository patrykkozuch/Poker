package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.actions.IllegalActionException;
import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

import java.util.Collection;

/**
 * List Games Action shows list of available games with number of people in lobby, ante and current game state
 */
public class ListGamesAction extends ServerAction {

    @SuppressWarnings("unused")
    @UsedViaReflection
    public static final String HELP_STRING = "LIST";

    ListGamesAction(Server server, PlayerWrapper playerWrapper) {
        super(server, playerWrapper);
    }

    @Override
    protected void validate() {
        // Validation not needed
    }

    @Override
    public void make() throws IllegalActionException {
        super.make();

        Collection<Game> games = server.getAllGames();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Aby dołączyć do gry wpisz: JOIN <id_gry>.\n");
        stringBuilder.append("Aktywne lobby: \n");

        for (Game g : games) {
            stringBuilder.append("\t* Gra nr ")
                    .append(g.getGameID())
                    .append(" (Ante ")
                    .append(g.getAnte())
                    .append(", ")
                    .append(g.getPlayersCount())
                    .append("/4) ")
                    .append(g.getStatus());
        }

        playerWrapper.sendMessageToPlayer(stringBuilder.toString());
    }
}
