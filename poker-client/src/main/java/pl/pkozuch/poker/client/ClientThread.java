package pl.pkozuch.poker.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ClientThread extends Thread {
    private final Client client;
    private final SocketChannel channel;
    private boolean running = true;

    ClientThread(Client client, SocketChannel channel) {
        this.client = client;
        this.channel = channel;
    }

    void stopRunning() {
        running = false;
    }

    boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (isRunning()) {

                String line = client.readFromChannel(channel);

                if (line != null)
                    System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null && channel.isOpen()) {
                    channel.close();
                    System.out.println("Połączenie z serwerem zostało przerwane.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
