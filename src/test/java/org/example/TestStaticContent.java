package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStaticContent {
    StaticContent sc;

    @BeforeEach
    public void setup() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        sc = new StaticContent(title, description,duration,start);
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getTitle() {
        assertEquals(sc.getTitle(), "title");
    }

    @Test
    public void getDescription() {
        assertEquals(sc.getDescription(), "description");
    }

    @Test
    public void getDuration() {
        assertEquals(sc.getDuration(), 30);
    }

    @Test
    public void getStart() {
        assertEquals(sc.getStart().getHour(), 12);
        assertEquals(sc.getStart().getMinute(), 0);
        assertEquals(sc.getStart().getSecond(), 0);
    }
}
