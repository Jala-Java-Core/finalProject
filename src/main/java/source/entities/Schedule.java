package source.entities;

import java.time.LocalTime;

public class Schedule {
    private final LocalTime startTime;
    private final LocalTime endTime;

    public static int MINIMUM_TIME = 30;
    public static int MAXIMUM_TIME = 1440;

    public Schedule(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Edit and Delete
}
