package models;

public class ChannelPreview {
    private long timeRemaining;
    private Schedule schedule;
    private Schedule previousSchedule;
    private Schedule nextSchedule;

    public ChannelPreview() {
        this.timeRemaining = 0;
        this.schedule = null;
        this.previousSchedule = null;
        this.nextSchedule = null;
    }

    public ChannelPreview(int timeRemaining, Schedule schedule, Schedule previousSchedule, Schedule nextSchedule) {
        this.timeRemaining = timeRemaining;
        this.schedule = schedule;
        this.previousSchedule = previousSchedule;
        this.nextSchedule = nextSchedule;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getPreviousSchedule() {
        return previousSchedule;
    }

    public void setPreviousSchedule(Schedule previousSchedule) {
        this.previousSchedule = previousSchedule;
    }

    public Schedule getNextSchedule() {
        return nextSchedule;
    }

    public void setNextSchedule(Schedule nextSchedule) {
        this.nextSchedule = nextSchedule;
    }
}