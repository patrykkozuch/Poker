package pl.pkozuch.poker.serveractions;

import pl.pkozuch.poker.server.Server;
import pl.pkozuch.poker.server.ServerThread;

public class Balance extends ServerAction {

    Balance(Server server, ServerThread playerThread, String[] args) {
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
            playerThread.sendMessageToPlayer("Twój stan konta to: " + playerThread.getPlayer().getBalance());
        } catch (Exception e) {
            playerThread.sendMessageToPlayer("Nie udało się sprawdzić stanu twojego konta. " + e.getMessage());
        }
    }

    public static String getHelpString() {
        return "BALANCE";
    }
}
