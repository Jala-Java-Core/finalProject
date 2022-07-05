package entertainment.models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import entertainment.enums.Season;
import entertainment.helpers.SeasonHelper;

public class Guide extends BaseModel {
    private Season season;
    private LocalDate startDate;
    private LocalDate endDate;
    private TreeMap<String, String> schedules;

    public Guide(Season season, LocalDate startDate) {
        this.season = season;
        this.startDate = startDate;
        generateSeasonDays();
        this.endDate = this.startDate.plusDays(this.schedules.size() - 1);
        this.setId(startDate.toString());
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        generateSeasonDays();
        this.endDate = this.startDate.plusDays(this.schedules.size() - 1);
        this.setId(startDate.toString());
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    private void generateSeasonDays() {
        switch (season) {
            case WEEK:
                this.schedules = generateDays(SeasonHelper.getDaysOfWeek(startDate));
                break;
            case MONTH:
                this.schedules = generateDays(SeasonHelper.getDaysOfMonth(startDate));
                break;
            default:
                break;
        }
    }

    private TreeMap<String, String> generateDays(int days) {
        TreeMap<String, String> schedules = new TreeMap<>(Comparator.reverseOrder());
        for (int i = 0; i < days; i++) {
            schedules.put(this.startDate.plusDays(i).toString(), "");
        }
        return schedules;
    }

    public Map<String, String> getSchedules() {
        return schedules;
    }

    public String getTodayScheduleId() {
        LocalDate today = LocalDate.now();
        return schedules.get(today.toString());
    }

    public void addSchedule(String scheduleId, int repetitions) {
        int i = 0;
        for (Map.Entry<String, String> entry : schedules.entrySet()) {
            if (entry.getValue().isEmpty()) {
                for (int j = 0; j < repetitions; j++) {
                    schedules.put(this.startDate.plusDays(i + j).toString(), scheduleId);
                }
                return;
            }
            i++;
        }
    }

    public void addRecurringSchedule(String scheduleId, DayOfWeek day) {
        if (season == Season.MONTH) {
            int i = day.getValue() - 1;
            while (i < schedules.size()) {
                schedules.put(this.startDate.plusDays(i).toString(), scheduleId);
                i += 7;
            }
        }
    }

    public void deleteSchedule(String day) {
        schedules.replace(day, "");
    }
}
