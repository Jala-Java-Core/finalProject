package source.entities;

import source.utility.Helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class Guide {
    private final Season season;
    private final HashMap<Schedule, LinkedHashSet<LocalDate>> scheduleWithDateAssociated;

    public Guide(Season season) {
        this.season = season;
        this.scheduleWithDateAssociated = new HashMap<>();
    }

    public HashMap<Schedule, LinkedHashSet<LocalDate>> getScheduleWithDateAssociated() {
        return scheduleWithDateAssociated;
    }

    // Repetition based on random dates or one date
    public void saveRepeatedSchedule(Schedule schedule, LinkedHashSet<String> datesString) {
        LinkedHashSet<LocalDate> dates = new LinkedHashSet<>();

        for (String dateString: datesString) {
            LocalDate date = LocalDate.parse(dateString, Helper.formatterDate);
            if (IsDateInSeason(date)) {
                dates.add(date);
            }
        }
        scheduleWithDateAssociated.put(schedule, dates);
    }

    // Recurrence based on days selected
    public void saveRecurringSchedule(Schedule schedule, DayOfWeek dayOfWeek) {
        LinkedHashSet<LocalDate> dates = new LinkedHashSet<>();
        LocalDate date = season.getStartDate().with( TemporalAdjusters.nextOrSame(dayOfWeek));
        while(date.isBefore(season.getEndDate()) ) {
            dates.add(date);
            date = date.plusWeeks(1);
        }
        scheduleWithDateAssociated.put(schedule, dates);
    }

    public void deleteScheduleInGuide(Schedule schedule) { scheduleWithDateAssociated.remove(schedule); }

    // Helper Methods
    public Schedule GetScheduleBasedOnDate(LocalDate date) {
        for(Map.Entry<Schedule, LinkedHashSet<LocalDate>> scheduleWithDate: getScheduleWithDateAssociated().entrySet()) {
            if (IsDateInSchedule(date, scheduleWithDate.getValue())) {
                return scheduleWithDate.getKey();
            }
        }
        return null;
    }

    public boolean IsDateInSeason(LocalDate date) {
        return (date.isAfter(season.getStartDate()) && date.isBefore(season.getEndDate())) ||
                date.equals(season.getStartDate()) || date.equals(season.getEndDate());
    }

    private boolean IsDateInSchedule(LocalDate date, LinkedHashSet<LocalDate> dates) {
        boolean isDateInSchedule = false;
        for (LocalDate dateInSchedule: dates) {
            if (dateInSchedule.isEqual(date)) {
                isDateInSchedule = true;
            }
        }
        return  isDateInSchedule;
    }
}
