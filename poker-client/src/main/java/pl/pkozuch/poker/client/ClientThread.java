package pl.pkozuch.poker.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Responsible for asynchronous read from server
 */
public class ClientThread extends Thread {
    private final Client client;
    private final SocketChannel channel;
    private boolean running = true;

    ClientThread(Client client, SocketChannel channel) {
        this.client = client;
        this.channel = channel;
    }

    /**
     * Sets running flag to false, results in stopping the thread
     */
    void stopRunning() {
        running = false;
    }

    /**
     * Gets current 'running' flag value
     *
     * @return boolean
     */
    boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (isRunning()) {

                String line = client.readLineFromChannel(channel);

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
