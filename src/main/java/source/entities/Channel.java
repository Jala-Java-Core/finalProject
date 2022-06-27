package source.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Channel {
    private final ArrayList<Guide> guides;
    private final ArrayList<Content> contents;
    private final ArrayList<Schedule> schedules;

    public Channel() {
        this.guides = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.schedules = new ArrayList<>();
    }

    public Channel(ArrayList<Guide> guides, ArrayList<Content> contents, ArrayList<Schedule> schedules) {
        this.guides = guides;
        this.contents = contents;
        this.schedules = schedules;
    }

    public void createGuide(Guide guide) {
        guides.add(guide);
    }

    public void createSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void createContent(Content content) {
        contents.add(content);
    }

    public ContentInformation preview(LocalDate date, LocalTime time) {
        ContentInformation contentInformation = new ContentInformation();
        LocalDate dateToPreview = date;
        LocalTime timeToPreview = time;

        if (date == null) { dateToPreview = LocalDate.now(); }
        if (time == null) { timeToPreview = LocalTime.now(); }

        return contentInformation;
    }
}