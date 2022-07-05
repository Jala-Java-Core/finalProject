import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static java.time.temporal.ChronoUnit.DAYS;

public class Guide {

    private LocalDate startDate;
    private LocalDate endDate;
    private int datesQuantity;
    private HashMap<String, Schedule> schedules = new HashMap<String, Schedule>();
    private HashMap<String, ArrayList<String>> recurrences = new HashMap<String, ArrayList<String>>();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    private StringBuilder formattedDate = new StringBuilder();

    public Guide(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        datesQuantity = (int) DAYS.between(this.startDate, this.endDate);
        createDatesMap();
    }

    private void createDatesMap() {
        LocalDate temp = startDate.minusDays(1);
        for (int day = 0; day <= datesQuantity; day++) {
            temp = temp.plusDays(1);
            formattedDate.setLength(0);
            formattedDate.append(temp.format(dateFormat));
            schedules.put(formattedDate.toString(), null);
        }
    }

    private boolean addSchedule(Schedule schedule, LocalDate date) {
        if(schedules.containsKey(date.format(dateFormat))) {
            schedules.replace(date.format(dateFormat), schedule);
            return true;
        }
        return false;
    }

    public void addScheduleInMultipleDates(String nameSchedule, Schedule schedule, ArrayList<LocalDate> dates) {
        ArrayList<String> dateKeys = new ArrayList<String>();
        for (LocalDate date : dates) {
            boolean success = addSchedule(schedule, date);
            if (success) {
                dateKeys.add(date.format(dateFormat));
            }
        }
        if(dateKeys.size() != 0) {
            recurrences.put(nameSchedule, dateKeys);
        }
    }

    public void deleteSchedule(String nameSchedule) {
        if(recurrences.containsKey(nameSchedule)) {
            for (String date : recurrences.get(nameSchedule)) {
                schedules.replace(date, null);
            }
            recurrences.remove(nameSchedule);
        }
    }

    public void editTitleContentInSchedule(String nameSchedule, String startHour, String newTitle) {
        Schedule schedule;
        if(recurrences.containsKey((nameSchedule))) {
            for (String date : recurrences.get(nameSchedule)) {
                schedule = schedules.get(date);
                schedule.editTitleContent(startHour, newTitle);
            }
        }
    }

    public Schedule getSchedule(String name, LocalDate date) {
        return schedules.get(date.format(dateFormat));
    }
}
