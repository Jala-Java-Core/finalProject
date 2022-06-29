package source.entities;

import source.utility.Helper;
import source.utility.ScheduleType;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Season {
    private LocalDate startDate;
    private LocalDate endDate;

    public Season(String startDate, String endDate) {
        this.startDate = LocalDate.parse(startDate, Helper.formatterDate);
        this.endDate = LocalDate.parse(endDate, Helper.formatterDate);
    }

    public Season(ScheduleType scheduleType, String dateString) {
        LocalDate date = LocalDate.parse(dateString, Helper.formatterDate);
        switch(scheduleType) {
            case MONTHLY:
                this.startDate = date.withDayOfMonth(1);
                this.endDate = date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
                break;
            case WEEKLY:
                this.startDate = date.with(DayOfWeek.MONDAY);
                this.endDate = date.with(DayOfWeek.SUNDAY);
                break;
            case ANNUALLY:
                this.startDate = LocalDate.of(date.getYear(), 1, 1);
                this.endDate = LocalDate.of(date.getYear(), 12, 31);
                break;
        }
    }

    public LocalDate getEndDate() { return this.endDate; }
    public LocalDate getStartDate() { return this.startDate; }
}
