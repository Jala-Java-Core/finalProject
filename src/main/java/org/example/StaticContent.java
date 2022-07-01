package org.example;

import java.time.LocalTime;

public class StaticContent extends Content{

    public StaticContent (String title, String description, int duration, LocalTime start) {
        super(title, description, duration, start);
    }
}
