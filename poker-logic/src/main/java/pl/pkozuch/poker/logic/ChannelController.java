package pl.pkozuch.poker.logic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Handles read and write operations on SocketChannel
 */
public class ChannelController {

    /**
     * Selector used to select channel.
     * <p>
     * Needed to check if {@link ChannelController#channel} is readable/writeable.
     */
    private final Selector selector;

    /**
     * Channel on which operations will be performed
     */
    private final SocketChannel channel;

    /**
     * @param selector {@link ChannelController#selector}
     * @param channel  {@link ChannelController#channel}
     */
    public ChannelController(Selector selector, SocketChannel channel) {
        this.selector = selector;
        this.channel = channel;
    }

    /**
     * Performs read operation on a {@link ChannelController#channel}.
     * Reads whole buffer.
     *
     * @return content stored in channel buffer. Otherwise, if channel is not readable or error occured returns {@code none}.
     */
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

            try {
                channel.close();
            } catch (IOException ioe) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Performs write operation on a {@link ChannelController#channel}.
     *
     * @param message Message to be sent.
     * @return {@code true} if write was successful. {@code false} otherwise.
     */
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

            try {
                channel.close();
            } catch (IOException ioe) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
