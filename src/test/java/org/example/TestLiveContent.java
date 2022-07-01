package org.example;

import org.junit.jupiter.api.*;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLiveContent {
    LiveContent lc;

    @BeforeEach
    public void setup() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        lc = new LiveContent(title, description,duration,start);
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getTitle() {
        assertEquals(lc.getTitle(), "title");
    }

    @Test
    public void getDescription() {
        assertEquals(lc.getDescription(), "description");
    }

    @Test
    public void getDuration() {
        assertEquals(lc.getDuration(), 30);
    }

    @Test
    public void getStart() {
        assertEquals(lc.getStart().getHour(), 12);
        assertEquals(lc.getStart().getMinute(), 0);
        assertEquals(lc.getStart().getSecond(), 0);
    }
}
