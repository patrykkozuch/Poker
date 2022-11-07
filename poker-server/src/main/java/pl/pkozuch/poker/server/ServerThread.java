package pl.pkozuch.poker.server;

import pl.pkozuch.poker.logic.Player;
import pl.pkozuch.poker.logic.StreamController;
import pl.pkozuch.poker.serveractions.ServerAction;
import pl.pkozuch.poker.serveractions.ServerActionFactory;

import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
    private final Server server;
    private final Socket socket;
    private final StreamController streamController;
    private final Player player;
    private Integer gameID = null;


    ServerThread(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;

        streamController = new StreamController(socket.getInputStream(), socket.getOutputStream());
        player = new Player(streamController);
    }

    void sendGreetingMessage() {
        String message = "Witaj na serwerze Pokera.\n" +
                "Wybierz co chcesz zrobić:\n" +
                "1. Stwórz nową grę\n" +
                "2. Dołącz do istniejącej gry";

        streamController.sendWithNewLine(message);
    }

    public Player getPlayer() {
        return player;
    }

    int getPlayerID() {
        return player.getId();
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public Integer getGameID() {
        return gameID;
    }

    @Override
    public void run() {
        super.run();
        ServerActionFactory actionFactory = new ServerActionFactory(server);
        streamController.sendWithNewLine(player.getId() + "");
        sendGreetingMessage();

        String line = "";
        try {
            while (line.compareTo("EXIT") != 0) {
                if (player.isLocked() || !player.isInReady())
                    continue;
                line = player.getResponse();

                if (!line.equals("")) {
                    System.out.println("Request from Client  :  " + line);
                }
                try {
                    ServerAction a = actionFactory.create(this, line);
                    a.make();
                } catch (Exception e) {
                    streamController.sendWithNewLine(e.getMessage());
                }
            }
        } catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Connection Closing..");
                if (socket != null) {
                    socket.close();
                    System.out.println("Socket Closed");
                }

            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }//end finally
    }

    public void sendMessageToPlayer(String message) {
        streamController.sendWithNewLine(message);
    }

    public String getResponse() {
        return player.getResponse();
    }
}
