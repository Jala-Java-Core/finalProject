package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Channel {
    private String name;
    private HashMap<String, Guide> guides;

    public Channel(String name) {
        this.name = name;
        this.guides = new HashMap<String, Guide>();
    }

    public String getName() {
        return this.name;
    }

    public HashMap getGuides() {
        return this.guides;
    }

    public void addGuide(Guide newGuide, String guideName) {
        if (newGuide == null) {
            throw new IllegalArgumentException("Guide must not be null");
        }
        if (guideName == null) {
            throw new IllegalArgumentException("Guide must not be null");
        }
        if (guideName == "") {
            throw new IllegalArgumentException("Guide must not be empty");
        }

        if (this.guides.size() == 0) {
            this.guides.put(guideName, newGuide);
        } else {
            AtomicBoolean addGuideFlag = new AtomicBoolean(true);
            guides.forEach((name, oldGuide) -> {
                LocalDate newGuideStart = newGuide.getStart();
                LocalDate newGuideEnd = newGuide.getEnd();
                LocalDate oldGuideStart = oldGuide.getStart();
                LocalDate oldGuideEnd = oldGuide.getEnd();

                boolean newGuideStartsBeforeOldGuide = newGuideStart.compareTo(oldGuideStart) < 0 && newGuideEnd.compareTo(oldGuideStart) < 0;
                boolean newGuideStartsAfterOldGuide = newGuideStart.compareTo(oldGuideEnd) > 0 && newGuideEnd.compareTo(oldGuideEnd) > 0;

                if (newGuideStartsAfterOldGuide || newGuideStartsBeforeOldGuide) {
                    addGuideFlag.set(addGuideFlag.get() && true);
                } else {
                    addGuideFlag.set(addGuideFlag.get() && false);
                }
            });


            if (addGuideFlag.get()) {
                this.guides.put(guideName, newGuide);
            } else {
                throw new IllegalArgumentException("New Guide is overwriting an existing content");
            }
        }
    }

    public void removeGuide (String guideName){
        if (guideName == null) {
            throw new IllegalArgumentException("Guide must not be null");
        }
        if (guideName == "") {
            throw new IllegalArgumentException("Guide must not be empty");
        }
        if (this.guides.containsKey(guideName)) {
            this.guides.remove(guideName);
        } else {
            throw new IllegalArgumentException("Guide does not exist");
        }
    }

    public Guide getGuideByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        AtomicReference<Guide> atomicGuide = new AtomicReference<>();
        guides.forEach((name, existingGuide) -> {
            if (date.compareTo(existingGuide.getStart()) >= 0 && date.compareTo(existingGuide.getEnd()) <= 0){
                atomicGuide.set(existingGuide);
            }
        });
        if (atomicGuide.get() == null){
            throw new IllegalArgumentException("Guide does not exist to this date");
        }
        return atomicGuide.get();
    }

    public Content[] channelPreview(LocalDateTime inputDateTime){
        if (inputDateTime == null){
            throw new IllegalArgumentException("Date must not be null");
        }
        Content response[] =  new Content[3];
        LocalDate date = LocalDate.of(inputDateTime.getYear(), inputDateTime.getMonthValue(), inputDateTime.getDayOfMonth()) ;
        LocalTime time = LocalTime.of(inputDateTime.getHour(), inputDateTime.getMinute(), inputDateTime.getSecond()) ;
        Guide guide = this.getGuideByDate(date);
        Schedule schedule = guide.getScheduleByDate(date);
        Content actualContent = schedule.getContentByHour(time);
        Content previousContent = null;
        Content laterContent = null;
        if (actualContent != null){
            previousContent = schedule.getPreviousContentOfContent(actualContent);
            laterContent = schedule.getLaterContentOfContent(actualContent);
        } else {
            previousContent = schedule.getPreviousContentOfTime(time);
            laterContent = schedule.getLaterContentOfTime(time);
        }
        response[0] = actualContent;
        response[1] = previousContent;
        response[2] = laterContent;
        return response;
    }
}
