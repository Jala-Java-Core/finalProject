package entertainment.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Schedule extends BaseModel{
    TreeMap<String, ScheduleContent> contentList = new TreeMap<>(Comparator.reverseOrder());

    public void addContent(ScheduleContent scheduleContent) {
        this.contentList.put(scheduleContent.getId(), scheduleContent);
    }

    public void deleteContent(String scheduleContentId) {
        this.contentList.remove(scheduleContentId);
    }

    public ScheduleContent getCurrentScheduledContent() {
        LocalTime now = LocalTime.now();
        for (Map.Entry<String, ScheduleContent> entry: this.contentList.entrySet()) {
            if (entry.getValue().getStartTime().compareTo(now) < 0 && entry.getValue().getEndTime().compareTo(now) > 0) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, ScheduleContent> getContent() {
        return contentList;
    }
}
