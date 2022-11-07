package pl.pkozuch.poker.client;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.StreamController;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Hello world!
 */
public class Client {
    Socket socket;
    StreamController streamController;
    Integer playerID = 0;

    Client(Socket socket) {
        this.socket = socket;

        try {
            streamController = new StreamController(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
        
        run();
    }

    public void run() {
        String line;
        line = streamController.readLine();

        while (!IntValidator.isInt(line))
            line = streamController.readLines();

        playerID = Integer.parseInt(line);

        ClientThread thread = new ClientThread(streamController);
        thread.start();

        Scanner scanner = new Scanner(System.in);
        line = scanner.nextLine();

        while (!line.equals("EXIT")) {

            streamController.sendWithNewLine(playerID + " " + line);

            line = scanner.nextLine();
        }
    }

    public static void main(String[] args) throws IOException {
        new Client(new Socket("localhost", 4444));
    }
}
