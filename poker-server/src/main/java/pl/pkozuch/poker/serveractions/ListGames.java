package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.logic.Game;
import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

import java.util.Collection;

public class ListGames extends ServerAction {

    ListGames(Server server, ServerThread playerThread, String[] args) {
        super(server, playerThread);

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

            playerThread.sendMessageToPlayer(stringBuilder.toString());
        } catch (Exception e) {
            playerThread.sendMessageToPlayer("Nie udało się rozpocząć gry. " + e.getMessage());
        }
    }
    
    public static String getHelpString() {
        return "LIST";
    }
}
