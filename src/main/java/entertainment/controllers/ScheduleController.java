package entertainment.controllers;

import entertainment.helpers.JsonHelper;
import entertainment.models.Channel;
import entertainment.models.Content;
import entertainment.models.Schedule;
import entertainment.models.ScheduleContent;

import java.util.Map;

public class ScheduleController {
    private JsonHelper<Channel> jsonHelper;

    public ScheduleController() {
        jsonHelper = new JsonHelper<Channel>(Channel.class);
    }

    public void addSchedule(String channelNumber, Schedule schedule) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        channel.addSchedule(schedule);
        jsonHelper.saveCollection(channels);
    }

    public Map<String, Schedule> getSchedules(String channelNumber) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        return channel.getSchedules();
    }

    public Schedule getSchedule(String channelNumber, String scheduleId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Schedule schedule = channel.getSchedule(scheduleId);
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule with id " + scheduleId + " does not exist");
        }
        return schedule;
    }

    public void deleteSchedule(String channelNumber, String scheduleId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        if (!channel.hasSchedule(scheduleId)) {
            throw new IllegalArgumentException("Schedule with id " + scheduleId + " does not exist");
        }

        channel.deleteSchedule(scheduleId);
        jsonHelper.saveCollection(channels);
    }

    public void addContent(String channelNumber, String scheduleId, ScheduleContent scheduleContent) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Schedule schedule = channel.getSchedule(scheduleId);
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule with id " + scheduleId + " does not exist");
        }
        schedule.addContent(scheduleContent);
        jsonHelper.saveCollection(channels);
    }

    public void deleteContent(String channelNumber, String scheduleId, String scheduleContentId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Schedule schedule = channel.getSchedule(scheduleId);
        if (schedule == null) {
            throw new IllegalArgumentException("Schedule with id " + scheduleId + " does not exist");
        }
        schedule.deleteContent(scheduleContentId);
        jsonHelper.saveCollection(channels);
    }

    public String showSchedules(String channelNumber) {
        ContentController contentController = new ContentController();
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }

        StringBuilder sb = new StringBuilder();
        Map<String, Schedule> schedules = channel.getSchedules();
        for (String scheduleId: schedules.keySet()) {
            sb.append("\nSchedule: " + scheduleId + "\n");
            Map<String, ScheduleContent> scheduleFullContent = schedules.get(scheduleId).getContent();
            for (String scheduleContentId : scheduleFullContent.keySet()) {
                ScheduleContent scheduleContent = scheduleFullContent.get(scheduleContentId);
                String contentId = scheduleContent.getContentId();
                Content content = contentController.getContent(channelNumber, contentId);
                sb.append("id: " + scheduleContentId + " Content: " + content.getTitle() + " from " + scheduleContent.getStartTime()
                        + " to " + scheduleContent.getStartTime().plusSeconds(content.getDuration().getSeconds()) + "\n");
            }
        }
        return sb.toString();
    }
}
