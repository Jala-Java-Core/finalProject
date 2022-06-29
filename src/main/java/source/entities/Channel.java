package source.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Channel {
    private final ArrayList<Guide> guides;

    public Channel() {
        this.guides = new ArrayList<>();
    }

    public ArrayList<Guide> getGuides() {
        return guides;
    }

    public void createGuide(Guide guide) {
        guides.add(guide);
    }

    public ContentInformation preview(LocalDate date, LocalTime time) {
        LocalDate dateToPreview = date;
        LocalTime timeToPreview = time;

        if (date == null) { dateToPreview = LocalDate.now(); }
        if (time == null) { timeToPreview = LocalTime.now(); }

        Guide guideBasedOnDate = GetGuideBasedOnDate(dateToPreview);
        Schedule scheduleBasedOnDate = guideBasedOnDate.GetScheduleBasedOnDate(dateToPreview);

        return scheduleBasedOnDate.getContentInformationBasedOnTime(timeToPreview);
    }

    private Guide GetGuideBasedOnDate(LocalDate date){
        Guide guideBasedOnDate = null;
        for (Guide guide: getGuides()) {
            if(guide.IsDateInSeason(date)) {
                guideBasedOnDate = guide;
                break;
            }
        }
        return guideBasedOnDate;
    }
}