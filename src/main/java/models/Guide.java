package models;

import enums.Season;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Guide {
    private Season season;
    private List<Schedule> schedules;
    private List<Date> dates;

    public Guide(Season season) {
        this.season = season;
        this.schedules = new ArrayList<Schedule>();
        this.dates = new ArrayList<Date>();
    }

    public Guide(Season season, List<Schedule> schedules, List<Date> dates) {
        this.season = season;
        this.schedules = schedules;
        this.dates = dates;
    }

    // Setters and getters
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedule(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    // Custom methods
    public Schedule getSchedule(String scheduleName) {
        Schedule schedule = schedules
                .stream()
                .filter(currSchedule -> scheduleName.equals(currSchedule.getSchedule()))
                .findAny()
                .orElse(null);

        return schedule;
    }

    public Schedule addSchedule(String schedule, Content content, ContentTime contentTime) {
        Schedule createdSchedule = new Schedule(schedule, content, contentTime);
        schedules.add(createdSchedule);
        return createdSchedule;
    }
}