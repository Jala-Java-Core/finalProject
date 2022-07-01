package source.entities;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ContentInformation {
    private ContentInSchedule current;
    private long timeRemaining;
    private ContentInSchedule previous;
    private ContentInSchedule next;

    public ContentInformation() {
        current = new ContentInSchedule();
        previous = new ContentInSchedule();
        next = new ContentInSchedule();
    }

    public void setContent(ContentInSchedule current) { this.current = current; }

    public void setPreviousContent(ContentInSchedule previous) { this.previous = previous; }

    public void setNextContent(ContentInSchedule next) { this.next = next; }

    public ContentInSchedule getCurrent() { return current; }

    public ContentInSchedule getNext() { return next; }

    public ContentInSchedule getPrevious() { return previous; }

    public String getNextToString() {
        return "Next: " + getNext().getContent().getTitle() +
            (getNext().getStartTime() != null ? " (" + getNext().getStartTime() + "-" + getNext().getEndTime() + ")": "");
    }

    public String getPreviousToString() {
        return "Previous: " + getPrevious().getContent().getTitle() +
            (getPrevious().getStartTime() != null ? " (" + getPrevious().getStartTime() + "-" + getPrevious().getEndTime() + ")" : "");
    }

    public String getTitleToString() {
        return "Content Title: " + getCurrent().getContent().getTitle() +
        (getCurrent().getStartTime() != null ? " (" + getCurrent().getStartTime() + "-" + getCurrent().getEndTime() + ")": "");
    }

    public String getSummaryToString() { return "Content Summary: " + getCurrent().getContent().getSummary(); }

    public String getDurationToString() { return "Duration in Minutes: " + getCurrent().getContent().getDurationInMinutes(); }

    public String getRemainingToString() { return "Remaining Time: " + getTimeRemaining(); }

    public void setTimeRemaining(LocalTime time, LocalTime endTime) { timeRemaining = time.until(endTime, ChronoUnit.MINUTES); }

    public long getTimeRemaining() { return timeRemaining; }

}
