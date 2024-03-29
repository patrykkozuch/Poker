package pl.pkozuch.poker.client;

import pl.pkozuch.poker.common.IntValidator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Client class
 * Responsible for connecting to server, retrieving playerID, handling write
 * and creating Thread which handles asynchronous read from a server
 */
public class Client {
    final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    ClientThread clientThread;
    Integer playerID = 0;

    Client() {
        SocketChannel channel = null;

        //Connecting to server
        try (SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 4444))) {
            Selector selector = Selector.open();

            connectToServer(selector, client);

            String line;

            Scanner scanner = new Scanner(System.in);

            //Handling write to server until EXIT is typed
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isWritable()) {
                        channel = (SocketChannel) key.channel();
                        line = scanner.nextLine();

                        if (line.equalsIgnoreCase("EXIT"))
                            return;

                        line = String.format("%d %s", playerID, line);

                        writeToChannel(channel, line);
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd. Proszę spróbować za chwilę.");
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                    System.out.println("Połączenie z serwerem zostało przerwane.");
                }

                if (clientThread != null) {
                    clientThread.stopRunning();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }

    /**
     * Connects to a server. As a part of connection playerID is received.
     *
     * @param selector used to select channel
     * @param client   client channel
     * @throws IOException if something badly happens (ex. read fails)
     */
    private void connectToServer(Selector selector, SocketChannel client) throws IOException {
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        //Retrieving playerID (first message sent after connection)
        String line = readLineFromChannel(client);
        while (!IntValidator.isInt(line)) {
            line = readLineFromChannel(client);
        }

        playerID = Integer.valueOf(line);

        //Starting new thread, which handles asynchronous read from server
        clientThread = new ClientThread(this, client);
        clientThread.start();
    }

    /**
     * Reads ONE LINE from server
     *
     * @param channel - channel which will be used to read from
     * @return String - message got from server (just single line)
     * @throws IOException if read from server fails
     */
    public String readLineFromChannel(SocketChannel channel) throws IOException {
        channel.read(readBuffer);

        String message = new String(readBuffer.array());

        int lineLength = message.indexOf('\n');

        if (lineLength == -1)
            return null;

        message = message.substring(0, lineLength);

        lineLength = message.getBytes(StandardCharsets.UTF_8).length;

        readBuffer.position(lineLength + 1);
        readBuffer.compact();
        readBuffer.position(0);
        if (!message.equals(""))
            return message;

        return null;
    }

    /**
     * Writes message to server
     *
     * @param channel - channel which will be used to send message to
     * @param message - message which will be sent
     * @throws IOException - if write to server fails
     */
    public void writeToChannel(SocketChannel channel, String message) throws IOException {
        channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
    }

}
