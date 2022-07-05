package entertainment.controllers;

import entertainment.helpers.JsonHelper;
import entertainment.models.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class GuideController {
    private JsonHelper<Channel> jsonHelper;

    public GuideController() {
        jsonHelper = new JsonHelper<Channel>(Channel.class);
    }

    public void addGuide(String channelNumber, Guide guide) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        channel.addGuide(guide);
        jsonHelper.saveCollection(channels);
    }

    public Guide getGuide(String channelNumber, String guideId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Guide guide = channel.getGuide(guideId);
        if (guide == null) {
            throw new IllegalArgumentException("Guide with id " + guideId + " does not exist");
        }
        return guide;
    }

    public void deleteGuide(String channelNumber, String guideId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        if (!channel.hasGuide(guideId)) {
            throw new IllegalArgumentException("Guide with id " + guideId + " does not exist");
        }

        channel.deleteGuide(guideId);
        jsonHelper.saveCollection(channels);
    }

    public void addSchedule(String channelNumber, String guideId, String scheduleId, int repetitions) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Guide guide = channel.getGuide(guideId);
        if (guide == null) {
            throw new IllegalArgumentException("Guide with id " + guideId + " does not exist");
        }
        guide.addSchedule(scheduleId, repetitions);
        jsonHelper.saveCollection(channels);
    }

    public void addRecurringSchedule(String channelNumber, String guideId, String scheduleId, DayOfWeek dayOfWeek) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Guide guide = channel.getGuide(guideId);
        if (guide == null) {
            throw new IllegalArgumentException("Guide with id " + guideId + " does not exist");
        }
        guide.addRecurringSchedule(scheduleId, dayOfWeek);
        jsonHelper.saveCollection(channels);
    }

    public void deleteSchedule(String channelNumber, String guideId, String date) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Guide guide = channel.getGuide(guideId);
        if (guide == null) {
            throw new IllegalArgumentException("Guide with id " + guideId + " does not exist");
        }
        guide.deleteSchedule(date);
        jsonHelper.saveCollection(channels);
    }

    public String showGuides(String channelNumber) {
        ScheduleController scheduleController = new ScheduleController();
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }

        StringBuilder sb = new StringBuilder();
        Map<String, Guide> guides = channel.getGuides();
        for (String guideId: guides.keySet()) {
            sb.append("\nGuide: " + guideId + "\n");
            Map<String, String> guideScheduleIds = guides.get(guideId).getSchedules();
            for (Map.Entry<String, String> entry : guideScheduleIds.entrySet()) {
                sb.append(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }
        return sb.toString();
    }
}
