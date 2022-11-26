package pl.pkozuch.poker.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsible for asynchronous read from server
 */
public class ClientThread extends Thread {
    private final Client client;
    private final SocketChannel channel;
    private final Logger console = Logger.getLogger("console");
    private final Logger logger = Logger.getLogger("error");
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
                    console.log(Level.FINEST, line);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        } finally {
            try {
                if (channel != null && channel.isOpen()) {
                    channel.close();
                    console.log(Level.SEVERE, "Połączenie z serwerem zostało przerwane.");
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
