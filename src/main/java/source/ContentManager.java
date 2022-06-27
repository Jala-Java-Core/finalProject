package source;

import source.entities.Channel;
import source.entities.ContentInformation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ContentManager {

    private ArrayList<Channel> channels;

    public ContentManager() {
        this.channels = new ArrayList<>();
    }

    public ContentManager(ArrayList channels) {
        this.channels = channels;
    }

    public void createChannel(Channel channel) {
        this.channels.add(channel);
    }

    public ContentInformation preview(Channel channel, LocalDate date, LocalTime time) {
        return channel.preview(date, time);
    }
}
