package models;

import enums.Season;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Schedule {
    private String schedule;
    private Content content;
    private ContentTime contentTime;

    public Schedule(String schedule, Content content, ContentTime contentTime) {
        this.schedule = schedule;
        this.content = content;
        this.contentTime = contentTime;
    }

    // Setters and getters
    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ContentTime getContentTime() {
        return contentTime;
    }

    public void setContentTime(ContentTime contentTime) {
        this.contentTime = contentTime;
    }

    // Custom methods
    public Content addContent(String title, String summary, int duration) {
        Content createdContent = new Content(duration, title, summary);
        setContent(createdContent);
        return createdContent;
    }
}