import java.util.HashMap;

public class Schedule {

    private HashMap<String, TimeSlot> timeSlotsMap = new HashMap<String, TimeSlot>();

    public Schedule() {

    }

    public void associateContent(Content content, String startHour) {
        if(timeSlotsMap.isEmpty()) {
            TimeSlot timeSlot = new TimeSlot(content, startHour);
            timeSlotsMap.put(startHour, timeSlot);
        } else {
            String endTime = HourHelp.getHourHelp().calculateEndHour(startHour, content.duration.toString());
            if (!HourHelp.getHourHelp().isThereCollisionInTimeSlots(startHour.toString(), endTime, timeSlotsMap)) {
                TimeSlot timeSlot = new TimeSlot(content, startHour);
                timeSlotsMap.put(startHour, timeSlot);
            }
        }
    }

    public void deleteContent(String startHour) {
        if (timeSlotsMap.containsKey(startHour)) {
            timeSlotsMap.remove(startHour);
        }
    }

    public void editTitleContent(String startHour, String newTitle) {
        if (timeSlotsMap.containsKey(startHour)) {
            TimeSlot timeSlot = timeSlotsMap.get(startHour);
            timeSlot.setContentTitle(newTitle);
        }
    }

    public void editDurationContent(String startHour, String newDuration) {
        if (timeSlotsMap.containsKey(startHour)) {
            TimeSlot timeSlot = timeSlotsMap.get(startHour);
            String endTime = HourHelp.getHourHelp().calculateEndHour(startHour.toString(), newDuration);
            if (!HourHelp.getHourHelp().isThereCollisionInTimeSlots(startHour.toString(), endTime, timeSlotsMap)) {
                timeSlot.setDuration(newDuration);
            }
        }
    }

    public void editSummaryContent(String startHour, String newSummary) {
        if (timeSlotsMap.containsKey(startHour)) {
            TimeSlot timeSlot = timeSlotsMap.get(startHour);
            timeSlot.setSummary(newSummary);
        }
    }

    public TimeSlot getPreviousTimeSlot(String time) {
        for (TimeSlot ts : timeSlotsMap.values()) {
            if (ts.getTimeEnd().equals(time)) {
                return ts;
            }
        }
        return null;
    }

    public TimeSlot getNextTimeSlot(String time) {
        for (TimeSlot ts : timeSlotsMap.values()) {
            if (ts.getTimeStart().equals(time)) {
                return ts;
            }
        }
        return null;
    }


    public String channelPreview(String startTime) {
        StringBuilder previousTitle = new StringBuilder();
        StringBuilder previousStartHour = new StringBuilder();
        StringBuilder previousEndHour = new StringBuilder();
        StringBuilder nextTitle = new StringBuilder();
        StringBuilder nextStartHour = new StringBuilder();
        StringBuilder nextEndHour = new StringBuilder();

        TimeSlot previousTimeSlot = getPreviousTimeSlot(startTime);
        if(previousTimeSlot != null) {
            previousTitle.append(previousTimeSlot.getContentTitle());
            previousStartHour.append(previousTimeSlot.getTimeStart());
            previousEndHour.append(startTime);
        }

        String endTime = timeSlotsMap.get(startTime).getTimeEnd();

        TimeSlot nextTimeSlot = getNextTimeSlot(endTime);
        if(nextTimeSlot != null) {
            nextTitle.append(nextTimeSlot.getContentTitle());
            nextStartHour.append(endTime);
            nextEndHour.append(nextTimeSlot.getTimeEnd());
        }

        TimeSlot currentTimeSlot = timeSlotsMap.get(startTime);

        String preview = "Title: " + currentTimeSlot.getContentTitle()
                + " Duration: " + currentTimeSlot.getDuration()
                + " Previous: " + previousTitle
                    + "(" + previousStartHour + " - "
                    + previousEndHour + ")"
                + " Next: " + nextTitle
                    + " (" + nextStartHour + " - "
                    + nextEndHour + ")"
                + " Summary: " + currentTimeSlot.getSummary();

        //System.out.println("Title: " + currentTimeSlot.getContentTitle());
        //System.out.println("Duration: " + currentTimeSlot.getDuration());
        //System.out.println("Previous: " + previousTitle
        //        + " (" + previousStartHour + " - "
        //        + previousEndHour + ")");
        //System.out.println("Next: " + nextTitle
        //        + " (" + nextStartHour + " - "
        //        + nextEndHour + ")");
        //System.out.println("Summary: " + currentTimeSlot.getSummary());

        System.out.println(preview);
        return preview;
    }
}
