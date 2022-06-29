package source.entities;

import source.utility.Helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Guide {
    private final Season season;
    private final HashMap<Schedule, List<LocalDate>> scheduleWithDateAssociated;

    public Guide(Season season) {
        this.season = season;
        this.scheduleWithDateAssociated = new HashMap<>();
    }

    public HashMap<Schedule, List<LocalDate>> getScheduleWithDateAssociated() {
        return scheduleWithDateAssociated;
    }

    // Repetition based on random dates
    public void createSchedule(Schedule schedule, ArrayList<String> stringDates) {
        List<LocalDate> dates = stringDates.stream()
                .map(date -> LocalDate.parse(date, Helper.formatterDate))
                .collect(Collectors.toList());
        scheduleWithDateAssociated.put(schedule, dates);
    }

    // Recurrence based on days selected
    public void createSchedule(Schedule schedule, DayOfWeek dayOfWeek) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = season.getStartDate().with( TemporalAdjusters.nextOrSame(dayOfWeek));
        while(date.isBefore(season.getEndDate()) ) {
            dates.add(date);
            date = date.plusWeeks(1);
        }
        scheduleWithDateAssociated.put(schedule, dates);
    }

    public Schedule GetScheduleBasedOnDate(LocalDate date) {
        for(Map.Entry<Schedule, List<LocalDate>> scheduleWithDate: getScheduleWithDateAssociated().entrySet()) {
            if (IsDateInSchedule(date, scheduleWithDate.getValue())) {
                return scheduleWithDate.getKey();
            }
        }
        return null;
    }

    public boolean IsDateInSeason(LocalDate date) {
        return (date.isAfter(season.getStartDate()) && date.isBefore(season.getEndDate())) || date.equals(season.getStartDate()) || date.equals(season.getEndDate());
    }

    private boolean IsDateInSchedule(LocalDate date, List<LocalDate> dates) {
        boolean isDateInSchedule = false;
        for (LocalDate dateInSchedule: dates) {
            if (dateInSchedule.isEqual(date)) {
                isDateInSchedule = true;
            }
        }
        return  isDateInSchedule;
    }
    // edit, delete, recurrence and repetition
}
