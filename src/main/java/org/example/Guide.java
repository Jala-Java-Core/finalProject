package org.example;

import java.time.LocalDate;
import java.util.HashMap;

public class Guide {
    private HashMap<String, Schedule> schedules;
    private LocalDate start;
    private LocalDate end;

    public Guide (LocalDate start, LocalDate end) {
        if (start.compareTo(end) >= 0) {
            throw new IllegalArgumentException("End date must be greater than start date");
        }
        this.schedules = new HashMap<String, Schedule>();
        this.start = start;
        this.end = end;
    }

    public HashMap<String, Schedule> getScheduleList() {
        return this.schedules;
    }

    public void addSchedule(Schedule schedule, LocalDate date) {
        if (schedule == null || date == null) {
            throw new IllegalArgumentException("Schedule and Date must not be null");
        }
        if (!(date.compareTo(this.start) >= 0 && date.compareTo(this.end) <= 0)) {
            throw new IllegalArgumentException("Date must be between start date and end date");
        }
        if (this.schedules.containsKey(this.getDateKey(date))) {
            throw new IllegalArgumentException("Date already has schedule");
        }
        this.schedules.put(this.getDateKey(date), schedule);

    }

    public void deleteSchedule(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        if (!this.schedules.containsKey(this.getDateKey(date))){
            throw new IllegalArgumentException("Date does not have schedule");
        }
        this.schedules.remove(this.getDateKey(date));
    }

    public void editSchedule(Schedule schedule, LocalDate date) {
        if (schedule == null || date == null) {
            throw new IllegalArgumentException("Schedule and Date must not be null");
        }
        if (!(date.compareTo(this.start) >= 0 && date.compareTo(this.end) <= 0)) {
            throw new IllegalArgumentException("Date must be between start date and end date");
        }
        if (!this.schedules.containsKey(this.getDateKey(date))) {
            throw new IllegalArgumentException("Date does not have schedule");
        }
        this.schedules.replace(this.getDateKey(date), schedule);
    }

    private String getDateKey (LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        String year = Integer.toString(date.getYear());
        String month = Integer.toString(date.getMonthValue());
        if (month.length() == 1){
            month = "0" + month;
        }
        String day = Integer.toString(date.getDayOfMonth());
        if (month.length() == 1){
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    public Schedule getScheduleByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        return this.schedules.get(this.getDateKey(date));
    }
}
