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

            if (previous != null) {
                if (previous.getStartTime() != null && previous.getEndTime().isBefore(time) && content == null) {
                    if (isInContentTime(startTime.minusMinutes(1), previous))
                        previousToContent = current;
                }

                if (previous.getStartTime() != null && startTime.isAfter(time) && nextToContent == null) {
                     nextToContent = current;
                }
            }
            previous = current;
        }

        return GetContentInformation(previousToContent, content, nextToContent, time);
    }

    private ContentInformation GetContentInformation(ContentInSchedule previousToContent, ContentInSchedule content,
                                                     ContentInSchedule nextToContent, LocalTime time) {
        ContentInformation contentInformation = new ContentInformation();
        if (content != null && content.getContent() != null) {
            contentInformation.setContent(content.getContent());
            contentInformation.setTimeRemaining(time, content.getEndTime());
        }

        if (content != null && content.getContent() != null && previousToContent != null && !isInContentTime(content.getStartTime().minusMinutes(1), previousToContent)) {
            contentInformation.setPreviousContent(GetEmptyContent());
        } else if (previousToContent != null) {
            contentInformation.setPreviousContent(previousToContent.getContent());
        }

        if (content != null && content.getContent() != null && nextToContent != null && !isInContentTime(content.getEndTime().plusMinutes(1), nextToContent)) {
            contentInformation.setNextContent(GetEmptyContent());
        } else if (nextToContent != null) {
            contentInformation.setNextContent(nextToContent.getContent());
        }

        return GetContentInformation(contentInformation);
    }

    private ContentInformation GetContentInformation(ContentInformation contentInformation) {
        if (contentInformation.getContent() == null) {
            contentInformation.setContent(GetEmptyContent());
        }
        if (contentInformation.getPreviousContent() == null) {
            contentInformation.setPreviousContent(GetEmptyContent());
        }
        if (contentInformation.getNextContent() == null) {
            contentInformation.setNextContent(GetEmptyContent());
        }

        return contentInformation;
    }

    private Content GetEmptyContent() { return new Content(); }

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
        return endTime;
    }

    // Edit and Delete
    public void deleteTime(String timeString) { timeTable.remove(timeString); }

    public void saveContentInTime(String timeString, Content content) { timeTable.put(timeString, content); }
}
