package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGuide {
    Guide guide;

    @BeforeEach
    public void setup() {
        LocalDate start = LocalDate.of(2022,6,26);
        LocalDate end = LocalDate.of(2022,7,2);
        guide = new Guide(start, end);
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getScheduleList() {
        assertEquals(guide.getScheduleList().size(), 0);
    }

    @Test
    public void addSchedule() {
        Schedule sc = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);
        HashMap schedules = guide.getScheduleList();

        assertEquals(schedules.size(), 1);
        assertEquals(schedules.containsKey("2022-06-26"), true);
    }

    @Test
    public void addScheduleWithScheduleNull() {
        LocalDate date = LocalDate.of(2022,6,26);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(null, date);
        });
    }

    @Test
    public void addScheduleWithDateNull() {
        Schedule sc = new Schedule();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(sc, null);
        });
    }

    @Test
    public void addScheduleWithInvalidDate() {
        Schedule sc = new Schedule();
        LocalDate date = LocalDate.of(2022,6,25);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(sc, date);
        });
    }

    @Test
    public void addScheduleInDateWithSchedule() {
        Schedule sc = new Schedule();
        Schedule sc2 = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);
        HashMap schedules = guide.getScheduleList();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(sc2, date);
        });
    }

    @Test
    public void deleteSchedule() {
        Schedule sc = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);

        guide.deleteSchedule(date);
        HashMap schedules = guide.getScheduleList();

        assertEquals(schedules.size(), 0);
    }

    @Test
    public void deleteScheduleWithDateNull() {
        Schedule sc = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.deleteSchedule(null);
        });
    }

    @Test
    public void deleteScheduleWithDateDoesNotExist() {
        Schedule sc = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);
        LocalDate date2 = LocalDate.of(2022,6,27);

        guide.addSchedule(sc,date);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.deleteSchedule(date2);
        });
    }

    @Test
    public void editSchedule() {
        Schedule sc = new Schedule();
        Content c = new Content("title", "description", 30, LocalTime.of(12, 0, 0));
        Schedule sc2 = new Schedule();
        sc2.addContent(c);
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);

        guide.editSchedule(sc2, date);
        HashMap<String, Schedule> schedules = guide.getScheduleList();
        Schedule schedule = schedules.get("2022-06-26");
        LinkedList<Content> contents = schedule.getContentList();

        assertEquals(schedules.size(), 1);
        assertEquals(schedules.containsKey("2022-06-26"), true);
        assertEquals(contents.size(), 1);
    }

    @Test
    public void editScheduleWithScheduleNull() {
        Schedule sc = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(null, date);
        });
    }

    @Test
    public void editScheduleWithDateNull() {
        Schedule sc = new Schedule();
        Content c = new Content("title", "description", 30, LocalTime.of(12, 0, 0));
        Schedule sc2 = new Schedule();
        sc2.addContent(c);
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(sc,date);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(sc2, null);
        });
    }

    @Test
    public void editScheduleWithInvalidDate() {
        Schedule sc = new Schedule();
        Content c = new Content("title", "description", 30, LocalTime.of(12, 0, 0));
        Schedule sc2 = new Schedule();
        sc2.addContent(c);
        LocalDate date = LocalDate.of(2022,6,26);
        LocalDate date2 = LocalDate.of(2022,6,25);

        guide.addSchedule(sc,date);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(sc2, date2);
        });
    }

    @Test
    public void editScheduleInDateWithoutSchedule() {
        Schedule sc2 = new Schedule();
        LocalDate date2 = LocalDate.of(2022,6,25);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            guide.addSchedule(sc2, date2);
        });
    }

    @Test
    public void getStart() {
        assertEquals(guide.getStart().getYear(), 2022);
        assertEquals(guide.getStart().getMonthValue(), 6);
        assertEquals(guide.getStart().getDayOfMonth(), 26);
    }

    @Test
    public void getEnd() {
        assertEquals(guide.getEnd().getYear(), 2022);
        assertEquals(guide.getEnd().getMonthValue(), 7);
        assertEquals(guide.getEnd().getDayOfMonth(), 2);
    }

    @Test
    public void getScheduleByDay() {
        Schedule schedule = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(schedule,date);
        Schedule foundSchedule = guide.getScheduleByDate(date);

        assertEquals(foundSchedule.getContentList().size(), 0);
    }

    @Test
    public void getScheduleByDayWithNullDate() {
        Schedule schedule = new Schedule();
        LocalDate date = LocalDate.of(2022,6,26);

        guide.addSchedule(schedule,date);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Schedule foundSchedule = guide.getScheduleByDate(null);
        });
    }
}
