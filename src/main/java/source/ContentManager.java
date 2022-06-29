package source;

import source.entities.Channel;
import source.entities.ContentInformation;
import source.utility.Helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ContentManager {
    private final ArrayList<Channel> channels;

    public ContentManager() {
        this.channels = new ArrayList<>();
    }

    public void createChannel(Channel channel) {
        this.channels.add(channel);
    }

    public ArrayList<Channel> getChannels() { return channels; }

    public ContentInformation preview(Channel channel, String dateString, String timeString) {
        Helper.ValidateStringIsNullOrEmpty("Date", dateString);
        Helper.ValidateStringIsNullOrEmpty("Time", timeString);
        if (channel == null) {
            throw new IllegalArgumentException("Channel is null.");
        }
        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ISO_TIME);
        LocalDate date = LocalDate.parse(dateString, Helper.formatterDate);

        return channel.preview(date, time);
    }
}
