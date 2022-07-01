import org.example.Channel;
import org.example.ChannelSystem;
import org.example.ChannelSystemBuilder;
import org.example.ChannelSystemMenu;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        ChannelSystem channelSystem = new ChannelSystem();
        ChannelSystemBuilder channelSystemBuilder = new ChannelSystemBuilder();

        Channel comedyCentralChannel = channelSystemBuilder.getComedyCentralChannel();
        channelSystem.addChannel(comedyCentralChannel);

        ChannelSystemMenu menu =  new ChannelSystemMenu(channelSystem);
        menu.showMainMenu();
    }
}