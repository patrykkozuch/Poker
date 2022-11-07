package pl.pkozuch.poker.client;

import pl.pkozuch.poker.logic.StreamController;

public class ClientThread extends Thread {
    private final StreamController streamController;

    ClientThread(StreamController streamController) {
        this.streamController = streamController;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            String lines = streamController.readLines();

            if (!lines.equals(""))
                System.out.print(lines);
        }
    }
}
