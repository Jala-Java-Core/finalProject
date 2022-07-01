package org.example;

import java.time.LocalTime;

public class Content {
    private String title;
    private String description;
    private int duration;
    private LocalTime start;

    public Content (String title, String description, int duration, LocalTime start) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public LocalTime getStart() {
        return start;
    }
}
