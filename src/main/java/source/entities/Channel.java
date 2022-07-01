package source.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class Channel {
    private final HashSet<Guide> guides;
    private final HashSet<Schedule> schedules;
    private final HashSet<Content> contents;
    private final String channelName;

    public Channel(String channelName) {
        this.channelName = channelName;
        this.guides = new HashSet<>();
        this.schedules = new HashSet<>();
        this.contents = new HashSet<>();
    }

    public String getChannelName() {
        return channelName;
    }

    public HashSet<Guide> getGuides() {
        return guides;
    }

    public HashSet<Content> getContents() {
        return contents;
    }

    public HashSet<Schedule> getSchedules() {
        return schedules;
    }

    public void saveGuide(Guide guide) {
        guides.add(guide);
    }

    public void saveSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void saveContent(Content content) {
        contents.add(content);
    }

    public ContentInformation preview(LocalDate date, LocalTime time) {
        LocalDate dateToPreview = date;
        LocalTime timeToPreview = time;

        if (date == null) { dateToPreview = LocalDate.now(); }
        if (time == null) { timeToPreview = LocalTime.now(); }
        ContentInformation contentInformation = new ContentInformation();
        Guide guideBasedOnDate = GetGuideBasedOnDate(dateToPreview);
        if (guideBasedOnDate != null) {
            Schedule scheduleBasedOnDate = guideBasedOnDate.getScheduleBasedOnDate(dateToPreview);
            if (scheduleBasedOnDate != null) {
                contentInformation = scheduleBasedOnDate.getContentInformationBasedOnTime(timeToPreview);
            }
        }
        return contentInformation;
    }

    private Guide GetGuideBasedOnDate(LocalDate date){
        Guide guideBasedOnDate = null;
        for (Guide guide: getGuides()) {
            if(guide.isDateInSeason(date)) {
                guideBasedOnDate = guide;
                break;
            }
        }
        return guideBasedOnDate;
    }
}