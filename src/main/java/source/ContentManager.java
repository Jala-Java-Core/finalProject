package source;

import source.entities.*;
import source.utility.Helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

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
        if (channel == null) {
            throw new IllegalArgumentException("Channel is null.");
        }
        LocalDate date;
        LocalTime time;

        if (dateString == null || dateString.isBlank() || dateString.isEmpty()) {
            date = LocalDate.now();
        } else {
            date = LocalDate.parse(dateString, Helper.formatterDate);
        }
        if (timeString == null || timeString.isBlank() || timeString.isEmpty()) {
            time = LocalTime.now();
        } else {
            time = LocalTime.parse(timeString, DateTimeFormatter.ISO_TIME);
        }

        return channel.preview(date, time);
    }

    public String getChannelsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Channel channel:getChannels()) {
            stringBuilder.append(channel.getChannelName() + "\n");
        }
        return stringBuilder.toString();
    }

    public String getSchedulesBasedOnChannelToString(String channelString) {
        Channel channel = getChannelBasedOnName(channelString);
        StringBuilder stringBuilder = new StringBuilder();
        int index = 1;
        for (Schedule schedule : channel.getSchedules()) {
            stringBuilder.append("Schedule " + index + ": \n");
            for (Map.Entry<String, Content> entry : schedule.getTimeTable().entrySet()) {
                LocalTime startTime = LocalTime.parse(entry.getKey(), DateTimeFormatter.ISO_TIME);
                stringBuilder.append("Start Time: " + entry.getKey() + " - End Time: " + schedule.getEndTime(startTime, entry.getValue())
                        + " - Content Title: " + entry.getValue().getTitle() + "\n");
            }
            index++;
        }
        return stringBuilder.toString();
    }
    public String getGuidesBasedOnChannelToString(String channelString) {
        Channel channel = getChannelBasedOnName(channelString);
        StringBuilder stringBuilder = new StringBuilder();
        int indexGuide = 1, indexSchedule = 1;
        for (Guide guide : channel.getGuides()) {
            stringBuilder.append("Guide " + indexGuide + ": \n");
            for (Map.Entry<Schedule, LinkedHashSet<LocalDate>> entry : guide.getScheduleWithDateAssociated().entrySet()) {
                stringBuilder.append("Schedule " + indexSchedule + " - Dates: " + entry.getValue().toString() + "\n");
                indexSchedule++;
            }
            indexGuide++;
        }
        return stringBuilder.toString();
    }

    public String getContentBasedOnChannelToString(String channelString) {
        Channel channel = getChannelBasedOnName(channelString);
        StringBuilder stringBuilder = new StringBuilder();
        for (Content content : channel.getContents()) {
            stringBuilder.append("Title: " + content.getTitle() + " - Duration in Minutes: " + content.getDurationInMinutes() + "\n");
        }
        return stringBuilder.toString();
    }

    private Channel getChannelBasedOnName(String channelString) {
        Channel channel = null;
        for (Channel c: getChannels()) {
            if(channelString.equals(c.getChannelName())) {
                channel = c;
                break;
            }
        }
        if (channel == null) {
            throw  new IllegalArgumentException("The channel does ot exist.");
        }
        return channel;
    }

    public String getPreview(String channelString, String dateString, String timeString){
        Channel channel = getChannelBasedOnName(channelString);
        StringBuilder stringBuilder = new StringBuilder();
        ContentInformation contentInformation = preview(channel, dateString, timeString);
        stringBuilder.append(contentInformation.getTitleToString() + "\n");
        stringBuilder.append(contentInformation.getSummaryToString() + "\n");
        stringBuilder.append(contentInformation.getDurationToString() + "\n");
        stringBuilder.append(contentInformation.getRemainingToString() + "\n");
        stringBuilder.append(contentInformation.getPreviousToString() + "\n");
        stringBuilder.append(contentInformation.getNextToString() + "\n");

        return  stringBuilder.toString();
    }
}
