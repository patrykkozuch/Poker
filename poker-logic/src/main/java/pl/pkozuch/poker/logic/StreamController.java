package pl.pkozuch.poker.logic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class StreamController {

    private final Selector selector;
    private final SocketChannel channel;


    public StreamController(Selector selector, SocketChannel channel) {
        this.selector = selector;
        this.channel = channel;
    }

    public String readFromChannel() {
        try {
            SelectionKey key = channel.keyFor(selector);

            if (key.isReadable()) {
                ByteBuffer buffer = ByteBuffer.allocate(256);

                SocketChannel socketChannel = (SocketChannel) key.channel();

                socketChannel.read(buffer);

                return new String(buffer.array()).trim();
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean writeToChannel(String message) {
        try {
            SelectionKey key = channel.keyFor(selector);

            if (key.isWritable()) {
                message += "\n";
                ByteBuffer buffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
                channel.write(buffer);
                return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
