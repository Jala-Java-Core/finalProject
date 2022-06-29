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
        ContentInformation contentInformation = new ContentInformation();
        LocalTime previousStartTime = null;
        LocalTime previousEndTime = null;

        for(Map.Entry<String,Content> contentBasedOnTime: getTimeTable().entrySet()) {
            LocalTime startTime = LocalTime.parse(contentBasedOnTime.getKey(), DateTimeFormatter.ISO_TIME);
            LocalTime endTime = getEndTime(startTime, contentBasedOnTime.getValue());

            if (isInContentTime(time, startTime, endTime)) {
                contentInformation.setContent(contentBasedOnTime.getValue());
                contentInformation.setTimeRemaining(time, endTime);
            }

            if (previousStartTime != null && previousEndTime.isBefore(time) && contentInformation.getContent() == null) {
                if(isInContentTime(startTime.minusMinutes(1), previousStartTime, previousEndTime))
                    contentInformation.setPreviousContent(contentBasedOnTime.getValue());
            }

            if (previousStartTime != null && contentInformation.getNextContent() == null && startTime.isAfter(time)) {
                if (isInContentTime(previousEndTime.plusMinutes(1), startTime, endTime))
                    contentInformation.setNextContent(contentBasedOnTime.getValue());
            }
            previousStartTime = startTime;
            previousEndTime = endTime;
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

    private Content GetEmptyContent() {
        return new Content();
    }

    private boolean isInContentTime(LocalTime time, LocalTime startTime, LocalTime endTime) {
        return (time.isAfter(startTime) && time.isBefore(endTime)) || time.equals(startTime) || time.equals(endTime);
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
}
