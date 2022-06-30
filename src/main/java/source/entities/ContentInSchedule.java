package source.entities;

import java.time.LocalTime;

public class ContentInSchedule {
    private LocalTime startTime;
    private LocalTime endTime;
    private Content content;

    public ContentInSchedule(LocalTime startTime, LocalTime endTime, Content content) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
    }

    public Content getContent() { return content; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
}
