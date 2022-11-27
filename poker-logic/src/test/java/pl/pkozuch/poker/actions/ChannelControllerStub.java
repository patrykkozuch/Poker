package pl.pkozuch.poker.actions;

import pl.pkozuch.poker.logic.ChannelController;

import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ChannelControllerStub extends ChannelController {
    public ChannelControllerStub(Selector selector, SocketChannel channel) {
        super(selector, channel);
    }

    @Override
    public String readFromChannel() {
        return "";
    }

    @Override
    public boolean writeToChannel(String message) {
        return true;
    }
}
