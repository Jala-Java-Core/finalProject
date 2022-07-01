package source.entities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

public class Schedule {
    private final TreeMap<String, Content> timeTable;

    public Schedule(TreeMap<String, Content> timeTable) {
        this.timeTable = timeTable;
    }

    public TreeMap<String, Content> getTimeTable() {
        return timeTable;
    }

    public ContentInformation getContentInformationBasedOnTime(LocalTime time) {

        ContentInSchedule previous = null, previousToContent = null, content = null, nextToContent = null;

        for (Map.Entry<String,Content> contentBasedOnTime: getTimeTable().entrySet()) {
            LocalTime startTime = LocalTime.parse(contentBasedOnTime.getKey(), DateTimeFormatter.ISO_TIME);
            LocalTime endTime = getEndTime(startTime, contentBasedOnTime.getValue());
            ContentInSchedule current = new ContentInSchedule(startTime, endTime, contentBasedOnTime.getValue());

            if (isInContentTime(time, current)) {
                content = current;
            }

            if (endTime.isBefore(time) && content == null) {
                previousToContent = current;
            }

            if (previous != null && startTime.isAfter(time) && nextToContent == null) {
                 nextToContent = current;
            }
            previous = current;
        }

        return getContentInformation(previousToContent, content, nextToContent, time);
    }

    private ContentInformation getContentInformation(ContentInSchedule previousToContent, ContentInSchedule content,
                                                     ContentInSchedule nextToContent, LocalTime time) {
        ContentInformation contentInformation = new ContentInformation();
        if (content != null && content.getContent() != null) {
            contentInformation.setContent(content);
            contentInformation.setTimeRemaining(time, content.getEndTime());
        }

        if (content != null && content.getContent() != null && previousToContent != null && !isInContentTime(content.getStartTime().minusMinutes(1), previousToContent)) {
            contentInformation.setPreviousContent(new ContentInSchedule());
        } else if (previousToContent != null) {
            contentInformation.setPreviousContent(previousToContent);
        }

        if (content != null && content.getContent() != null && nextToContent != null && !isInContentTime(content.getEndTime().plusMinutes(1), nextToContent)) {
            contentInformation.setNextContent(new ContentInSchedule());
        } else if (nextToContent != null) {
            contentInformation.setNextContent(nextToContent);
        }

        return contentInformation;
    }

    private boolean isInContentTime(LocalTime time, ContentInSchedule content) {
        return (time.isAfter(content.getStartTime()) && time.isBefore(content.getEndTime())) ||
                time.equals(content.getStartTime()) || time.equals(content.getEndTime());
    }

    public LocalTime getEndTime(LocalTime startTime, Content content) {
        LocalTime endTime;

        if (content == null) {
            throw new IllegalArgumentException("Content is null.");
        } else {
            endTime = startTime.plusMinutes(content.getDurationInMinutes());
        }
        return endTime.minusMinutes(1);
    }

    // Edit and Delete
    public void deleteTime(String timeString) { timeTable.remove(timeString); }

    public void saveContentInTime(String timeString, Content content) { timeTable.put(timeString, content); }
}
