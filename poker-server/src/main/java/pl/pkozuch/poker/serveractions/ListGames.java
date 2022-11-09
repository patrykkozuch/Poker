package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.PlayerWrapper;
import pl.pkozuch.poker.server.Server;

import java.util.Collection;

public class ListGames extends ServerAction {

    ListGames(Server server, PlayerWrapper playerWrapper, String[] args) {
        super(server, playerWrapper);

        if (args != null)
            throw new RuntimeException("Nieprawidłowa liczba argumentów");
    }

    @Override
    public void validate() {
    }

    @Override
    public void make() {
        super.make();

        try {
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
        } catch (Exception e) {
            playerWrapper.sendMessageToPlayer("Nie udało się rozpocząć gry. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "LIST";
    }
}
