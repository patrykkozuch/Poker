package pl.pkozuch.poker.client;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ClientThread extends Thread {
    private final Client client;
    private final SocketChannel channel;

    ClientThread(Client client, SocketChannel channel) {
        this.client = client;
        this.channel = channel;
    }

    @Override
    public void run() {
        super.run();
        while (true) {

            try {
                String line = client.readFromChannel(channel);


                if (line != null)
                    System.out.println(line);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
