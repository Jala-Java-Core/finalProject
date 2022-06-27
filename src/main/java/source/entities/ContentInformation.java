package source.entities;

import java.time.LocalTime;

public class ContentInformation {
    private Content content;
    private LocalTime timeRemaining;
    private Content previousContent;
    private Content nextContent;

    public ContentInformation() {
    }

    public ContentInformation(Content content, LocalTime timeRemaining, Content previousContent, Content nextContent) {
        this.content = content;
        this.timeRemaining = timeRemaining;
        this.previousContent = previousContent;
        this.nextContent = nextContent;
    }
}
