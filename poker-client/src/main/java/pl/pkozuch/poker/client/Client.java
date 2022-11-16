package pl.pkozuch.poker.client;

import pl.pkozuch.poker.common.IntValidator;
import pl.pkozuch.poker.logic.ChannelController;

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
 * Hello world!
 */
public class Client {
    ChannelController channelController;
    Integer playerID = 0;

    ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    Client() {
        SocketChannel channel = null;
        try (SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", 4444))) {
            Selector selector = Selector.open();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            String line = readFromChannel(client);
            while (!IntValidator.isInt(line)) {
                line = readFromChannel(client);
            }

            playerID = Integer.valueOf(line);

            new ClientThread(this, client).start();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isWritable()) {
                        channel = (SocketChannel) key.channel();
                        line = scanner.nextLine();
                        line = playerID + " " + line;

                        writeToChannel(channel, line);
                    }
                    iter.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                    System.out.println("Połączenie z serwerem zostało przerwane.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }

    public String readFromChannel(SocketChannel channel) throws IOException {
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

    public void writeToChannel(SocketChannel channel, String message) throws IOException {
        channel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
    }
}
