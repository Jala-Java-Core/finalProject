package org.example;

import java.time.LocalTime;

public class LiveContent extends Content{

    public LiveContent (String title, String description, int duration, LocalTime start) {
        super(title, description, duration, start);
    }
}
