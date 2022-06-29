package source.entities;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class ContentInformation {
    private Content content;
    private long timeRemaining;
    private Content previousContent;
    private Content nextContent;

    public ContentInformation() {
    }

    public void setContent(Content content) { this.content = content; }

    public void setPreviousContent(Content previousContent) { this.previousContent = previousContent; }

    public void setNextContent(Content nextContent) { this.nextContent = nextContent; }

    public Content getContent() { return content; }

    public Content getNextContent() { return nextContent; }

    public Content getPreviousContent() { return previousContent; }

    public void setTimeRemaining(LocalTime time, LocalTime endTime) { timeRemaining = time.until(endTime, ChronoUnit.MINUTES); }

    public long getTimeRemaining() { return timeRemaining; }
}
