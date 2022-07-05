package entertainment.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Channel extends BaseModel {
    private String name;
    private String number;
    private Map<String, Content> shows = new HashMap<>();
    private Map<String, Schedule> schedules = new HashMap<>();
    private TreeMap<String, Guide> guides = new TreeMap<>(Comparator.reverseOrder());

    public Channel(String channelNumber, String channelName) {
        this.setId(channelNumber);
        this.number = channelNumber;
        this.name = channelName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<String, Content> getAllContent() { return this.shows; }

    public Content getContent(String contentId) {
        return this.shows.get(contentId);
    }

    public ScheduleContent getCurrentScheduledContent() {
        LocalDate today = LocalDate.now();
        for (Map.Entry<String, Guide> entry: this.guides.entrySet()) {
            if (entry.getValue().getStartDate().compareTo(today) <= 0 && entry.getValue().getEndDate().compareTo(today) >= 0) {
                String scheduleId = entry.getValue().getTodayScheduleId();
                Schedule schedule = this.schedules.get(scheduleId);
                return schedule.getCurrentScheduledContent();
            }
        }
        return null;
    }

    public void addContent(Content content) {
        this.shows.put(content.getId(), content);
    }

    public boolean hasContent(String contentId) { return this.shows.containsKey(contentId); }

    public void deleteContent(String contentId) { this.shows.remove(contentId); }

    public Map<String, Schedule> getSchedules() { return this.schedules; }

    public Schedule getSchedule(String scheduleId) {
        return this.schedules.get(scheduleId);
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.put(schedule.getId(), schedule);
    }

    public boolean hasSchedule(String scheduleId) { return this.schedules.containsKey(scheduleId); }

    public void deleteSchedule(String scheduleId) { this.schedules.remove(scheduleId); }

    public TreeMap<String, Guide> getGuides() { return this.guides; }

    public Guide getGuide(String guideId) {
        return this.guides.get(guideId);
    }

    public void addGuide(Guide guide) {
        while (this.guides.containsKey(guide.getId())) {
            Guide existingGuide = this.guides.get(guide.getId());
            guide.setStartDate(existingGuide.getEndDate().plusDays(1));
        }
        this.guides.put(guide.getId(), guide);
    }

    public boolean hasGuide(String guideId) { return this.guides.containsKey(guideId); }

    public void deleteGuide(String guideId) { this.guides.remove(guideId); }

    @Override
    public String toString() {
        return "Channel{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
