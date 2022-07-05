package entertainment.models;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;

public class ScheduleContent extends BaseModel {
    private String contentId;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduleContent(String contentId, LocalTime startTime, LocalTime endTime) {
        this.contentId = contentId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.setId(startTime.toString());
    }

    public String getContentId() {
        return contentId;
    }

    public LocalTime getStartTime() { return startTime; }

    public LocalTime getEndTime() { return endTime; }

    public Duration getRemainingTime() {
        LocalTime now = LocalTime.now();
        int hours = this.endTime.getHour() - now.getHour();
        int minutes = this.endTime.getMinute() - now.getMinute();
        return Duration.ofHours(hours).plusMinutes(minutes);
    }

    @Override
    public String toString() {
        return "ScheduleContent{" +
                "contentId='" + contentId + '\'' +
                ", startTime=" + startTime +
                '}';
    }
}
