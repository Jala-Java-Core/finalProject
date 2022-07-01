package source.entities;

import java.time.LocalTime;

public class ContentInSchedule {
    private LocalTime startTime;
    private LocalTime endTime;
    private final Content content;

    public ContentInSchedule() {
        this.content = GetEmptyContent();
    }

    public ContentInSchedule(LocalTime startTime, LocalTime endTime, Content content) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
    }

    public Content getContent() { return content; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    private Content GetEmptyContent() { return new Content(); }
}
