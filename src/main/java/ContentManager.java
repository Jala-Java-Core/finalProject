import enums.Season;
import models.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ContentManager {
    private List<Channel> channels;

    public ContentManager() {
        this.channels = new ArrayList<>();
    }

    // Setters and getters
    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    // Custom methods
    public Channel getChannel(String channelName) {
        Channel channel = channels
                .stream()
                .filter(currChannel -> channelName.equals(currChannel.getChannel()))
                .findAny()
                .orElse(null);

        return channel;
    }

    public Schedule getSchedule(String channelName, String scheduleName) {
        Channel channel = channels
                .stream()
                .filter(currChannel -> channelName.equals(currChannel.getChannel()))
                .findAny()
                .orElse(null);

        if (channel != null) {
            Guide guide = channel.getGuide();

            if (guide != null) {
                Schedule schedule = guide
                        .getSchedules()
                        .stream()
                        .filter(currSchedule -> scheduleName.equals(currSchedule.getSchedule()))
                        .findAny()
                        .orElse(null);

                return schedule;
            }
        }

        return null;
    }

    public Channel createChannel(String channel) {
        Channel newChannel = new Channel(channel);
        channels.add(newChannel);
        return newChannel;
    }

    public Guide createGuide(String channelName, Season season, List<Schedule> schedules, List<Date> dates) {
        if (
            channelName == null ||
            channelName.isEmpty() ||
            getChannel(channelName) == null
        ) {
            return null;
        }

        Channel channel = getChannel(channelName);

        Guide newGuide = channel.addGuide(season, schedules, dates);

        return newGuide;
    }

    public boolean updateGuide(String channelName, Season season, List<Schedule> schedules, List<Date> dates) {
        if (
                channelName == null ||
                        channelName.isEmpty() ||
                        getChannel(channelName) == null
        ) {
            return false;
        }

        Channel channel = getChannel(channelName);

        Guide guide = channel.getGuide();

        if (guide != null) {
            guide.setSeason(season);
            guide.setSchedule(schedules);
            guide.setDates(dates);
            return true;
        }

        return false;
    }

    public boolean deleteGuide(String channelName) {
        if (
                channelName == null ||
                channelName.isEmpty() ||
                getChannel(channelName) == null
        ) {
            return false;
        }

        Channel channel = getChannel(channelName);

        Guide guide = channel.getGuide();

        if (guide != null) {
            channel.deleteGuide();
            return true;
        }

        return false;
    }

    public Schedule createSchedule(String scheduleName, String channelName, Content content, ContentTime contentTime) {
        if (
                scheduleName == null ||
                channelName == null ||
                channelName.isEmpty() ||
                scheduleName.isEmpty() ||
                getChannel(channelName) == null ||
                MINUTES.between(contentTime.getStartTime(), contentTime.getEndTime()) < 30
        ) {
            return null;
        }

        Channel channel = getChannel(channelName);

        Guide guide = channel.getGuide();

        if (guide != null) {
            Schedule newSchedule = guide.addSchedule(scheduleName, content, contentTime);
            return newSchedule;
        }

        return null;
    }

    public boolean updateSchedule(String scheduleName, String channelName, Content content, ContentTime contentTime) {
        if (
                scheduleName == null ||
                channelName == null ||
                channelName.isEmpty() ||
                scheduleName.isEmpty() ||
                getChannel(channelName) == null ||
                MINUTES.between(contentTime.getStartTime(), contentTime.getEndTime()) < 30
        ) {
            return false;
        }

        Channel channel = getChannel(channelName);

        Guide guide = channel.getGuide();

        Schedule schedule = guide.getSchedule(scheduleName);

        if (schedule != null) {
            schedule.setContent(content);
            schedule.setContentTime(contentTime);
            return true;
        }

        return false;
    }

    public boolean deleteSchedule(String scheduleName, String channelName) {
        if (
                scheduleName == null ||
                channelName == null ||
                channelName.isEmpty() ||
                scheduleName.isEmpty() ||
                getChannel(channelName) == null
        ) {
            return false;
        }

        Channel channel = getChannel(channelName);

        Guide guide = channel.getGuide();

        List<Schedule> schedules = guide.getSchedules();
        Schedule schedule = guide.getSchedule(scheduleName);

        if (schedule != null) {
            schedules.remove(schedule);
            return true;
        }

        return false;
    }

    public Content createContent(String scheduleName, String channelName, String title, String summary, int duration) {
        if (
                scheduleName == null ||
                channelName == null ||
                channelName.isEmpty() ||
                scheduleName.isEmpty() ||
                getChannel(channelName) == null
        ) {
            return null;
        }

        Schedule schedule = getSchedule(channelName, scheduleName);

        Content newContent = schedule.addContent(title, summary, duration);

        return newContent;
    }

    private void setSchedulesOnChannelWithCurrent(ChannelPreview channelPreview, List<Schedule> schedules, LocalTime hour) {
        // Check if the hour belongs to a schedule.
        for (int i = 0; i < schedules.size(); i++) {
            Schedule schedule = schedules.get(i);

            LocalTime startTime = schedule.getContentTime().getStartTime();
            LocalTime endTime = schedule.getContentTime().getEndTime();

            int comparingWithStartTime = startTime.compareTo(hour);
            int comparingWithEndTime = hour.compareTo(endTime);

            if (comparingWithStartTime <= 0 && comparingWithEndTime < 0) {
                channelPreview.setSchedule(schedule);
                channelPreview.setTimeRemaining(MINUTES.between(hour, endTime));
                if (i > 0) {
                    Schedule previousSchedule = schedules.get(i - 1);
                    channelPreview.setPreviousSchedule(previousSchedule);
                }

                if (i < schedules.size() - 1) {
                    Schedule nextSchedule = schedules.get(i + 1);
                    channelPreview.setNextSchedule(nextSchedule);
                }
                break;
            }
        }
    }

    private void setSchedulesOnChannelWithoutCurrent(ChannelPreview channelPreview, List<Schedule> schedules, LocalTime hour) {
        // Check if the hour is between two schedules.
        channelPreview.setTimeRemaining(0);
        for (int i = 0; i < schedules.size(); i++) {
            Schedule currentSchedule = schedules.get(i);
            ContentTime currentScheduleContentTime = currentSchedule.getContentTime();

            if (i == 0) {
                LocalTime startTime = currentScheduleContentTime.getStartTime();
                int comparingWithStartTime = hour.compareTo(startTime);

                if (comparingWithStartTime < 0) {
                    channelPreview.setNextSchedule(currentSchedule);
                    break;
                }
            }
            if (i == schedules.size() - 1) {
                LocalTime endTime = currentScheduleContentTime.getEndTime();
                int comparingWithEndTime = hour.compareTo(endTime);

                if (comparingWithEndTime > 0) {
                    channelPreview.setPreviousSchedule(currentSchedule);
                    break;
                }
            }
            if (i < schedules.size() - 1) {
                Schedule nextSchedule = schedules.get(i + 1);
                ContentTime nextScheduleContentTime = nextSchedule.getContentTime();

                LocalTime startTime = currentScheduleContentTime.getEndTime();
                LocalTime endTime = nextScheduleContentTime.getStartTime();

                int comparingWithStartTime = startTime.compareTo(hour);
                int comparingWithEndTime = hour.compareTo(endTime);

                if (comparingWithStartTime <= 0 && comparingWithEndTime < 0) {
                    channelPreview.setPreviousSchedule(currentSchedule);
                    channelPreview.setNextSchedule(nextSchedule);
                    break;
                }
            }
        }
    }

    private ChannelPreview _previewChannel(Channel channel, Date date, LocalTime hour) {
        ChannelPreview channelPreview = new ChannelPreview();

        Guide guide = channel.getGuide();
        List<Schedule> schedules = guide.getSchedules();
        List<Date> dates = guide.getDates();

        if (dates.indexOf(date) >= 0) {
            setSchedulesOnChannelWithCurrent(channelPreview, schedules, hour);

            if (channelPreview.getSchedule() == null) {
                setSchedulesOnChannelWithoutCurrent(channelPreview, schedules, hour);
            }
            return channelPreview;
        }

        return null;
    }

    public ChannelPreview previewChannel(Channel channel) {
        if (channel == null || channel.getGuide() == null) {
            return null;
        }

        Date date = new Date();
        LocalTime hour = LocalTime.now();

        ChannelPreview channelPreview = _previewChannel(channel, date, hour);

        return channelPreview;
    }

    public ChannelPreview previewChannel(Channel channel, Date date, LocalTime hour) {
        if (channel == null || channel.getGuide() == null) {
            return null;
        }

        ChannelPreview channelPreview = _previewChannel(channel, date, hour);

        return channelPreview;
    }
}
