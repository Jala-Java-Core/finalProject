package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class Schedule {
    private LinkedList<Content> contents;

    public Schedule() {
        contents = new LinkedList<Content>();
    }

    public LinkedList<Content> getContentList() {
        return this.contents;
    }

    public void addContent(Content newContent) {
        if (newContent == null) {
            throw new IllegalArgumentException("New Content is null");
        }
        if(this.contents.size() == 0) {
            this.contents.add(newContent);
        } else {
            boolean addContent = true;
            for (int i = 0; i < this.contents.size(); i++ ) {
                Content contentAlreadyExists = this.contents.get(i);
                LocalTime oldContentStart = contentAlreadyExists.getStart();
                LocalTime oldContentEnd = oldContentStart.plusMinutes(contentAlreadyExists.getDuration());
                LocalTime newContentStart = newContent.getStart();
                LocalTime newContentEnd = newContentStart.plusMinutes(contentAlreadyExists.getDuration());

                boolean newContentStartsBeforeOldContent = newContentStart.compareTo(oldContentStart) <= 0 && newContentEnd.compareTo(oldContentStart) <= 0;
                boolean newContentStartsAfterOldContent = newContentStart.compareTo(oldContentEnd) >= 0 && newContentEnd.compareTo(oldContentEnd) >= 0;

                if (newContentStartsAfterOldContent || newContentStartsBeforeOldContent) {
                    addContent = addContent && true;
                } else {
                    addContent = addContent && false;
                }
            }

            if (addContent) {
                this.contents.add(newContent);
            } else {
                throw new IllegalArgumentException("New content "+ newContent.getTitle() +" is overwriting an existing content");
            }
        }
    }

    public void deleteContent(String title, LocalTime time) {
        if (title == null ) {
            throw new IllegalArgumentException("Title is null");
        }
        if (title == "" ) {
            throw new IllegalArgumentException("Title is empty");
        }
        if (time == null ) {
            throw new IllegalArgumentException("Time is null");
        }

        boolean wasDeleted = false;
        for (int i = 0; i < this.contents.size(); i++) {
            Content content = this.contents.get(i);
            if (content.getTitle() == title && content.getStart().compareTo(time) == 0) {
                this.contents.remove(i);
                wasDeleted = true;
            }
        }

        if (!wasDeleted ) {
            throw new IllegalArgumentException("Content with title and time does not exist");
        }
    }

    public void editContent(String title, LocalTime time, Content newContent) {
        if (title == null ) {
            throw new IllegalArgumentException("Title is null");
        }
        if (title == "" ) {
            throw new IllegalArgumentException("Title is empty");
        }
        if (time == null ) {
            throw new IllegalArgumentException("Time is null");
        }
        if (newContent == null ) {
            throw new IllegalArgumentException("New Content is null");
        }

        boolean wasEdited = false;
        for (int i = 0; i < this.contents.size(); i++) {
            Content content = this.contents.get(i);
            if (content.getTitle() == title && content.getStart().compareTo(time) == 0) {
                this.contents.remove(i);
                this.addContent(newContent);
                wasEdited = true;
            }
        }

        if (!wasEdited ) {
            throw new IllegalArgumentException("Content with title and time does not exist");
        }
    }

    public Content getContentByHour(LocalTime time) {
        if (time == null ) {
            throw new IllegalArgumentException("Time must not be null");
        }
        Content response = null;
        for (int i = 0; i < this.contents.size(); i++) {
            Content content = this.contents.get(i);
            LocalTime startContent = content.getStart();
            LocalTime endContent = startContent.plusMinutes(content.getDuration());
            if (time.compareTo(startContent) > 0 && (time.compareTo(endContent) < 0 || endContent.equals(LocalTime.of(0,0,0)))) {
                response = content;
            } else if (time.compareTo(startContent) == 0 || time.compareTo(endContent) == 0){
                throw new IllegalArgumentException("One Program starts an other ends to the indicated hour");
            }
        }
        return response;
    }

    public Content getPreviousContentOfContent(Content actualContent) {
        if (actualContent == null ) {
            throw new IllegalArgumentException("Actual Content must not be null");
        }
        Content response = null;
        if (actualContent.getStart() != LocalTime.of(0,0,0)) {
            for (int i = 0; i < this.contents.size(); i++) {
                Content contentIterator = this.contents.get(i);
                LocalTime actualContentStart = actualContent.getStart();
                LocalTime endContentIterator = contentIterator.getStart().plusMinutes(contentIterator.getDuration());
                LocalTime differenceTime = actualContentStart.minusHours(endContentIterator.getHour());
                differenceTime = differenceTime.minusMinutes(endContentIterator.getMinute());
                if (differenceTime.getHour() == 0 && (differenceTime.getMinute() == 0 || differenceTime.getMinute() == 1)){
                    response = contentIterator;
                }
            }
        }
        return response;
    }

    public Content getLaterContentOfContent(Content actualContent) {
        if (actualContent == null ) {
            throw new IllegalArgumentException("Actual Content must not be null");
        }
        Content response = null;
        if (actualContent.getStart().plusMinutes(actualContent.getDuration()) != LocalTime.of(0,0,0)) {
            for (int i = 0; i < this.contents.size(); i++) {
                Content contentIterator = this.contents.get(i);
                LocalTime actualContentEnd = actualContent.getStart().plusMinutes(actualContent.getDuration());
                LocalTime startContentIterator = contentIterator.getStart();
                LocalTime differenceTime = startContentIterator.minusHours(actualContentEnd.getHour());
                differenceTime = differenceTime.minusMinutes(actualContentEnd.getMinute());
                if (differenceTime.getHour() == 0 && (differenceTime.getMinute() == 0 || differenceTime.getMinute() == 1)){
                    response = contentIterator;
                }
            }
        }
        return response;
    }

    public Content getPreviousContentOfTime(LocalTime actualTime) {
        if (actualTime == null ) {
            throw new IllegalArgumentException("Actual Time must not be null");
        }
        Content response = null;
        LocalTime previousDifferenceTime = LocalTime.of(23, 59, 59);
        for (int i = 0; i < this.contents.size(); i++) {
            Content contentIterator = this.contents.get(i);
            LocalTime endContentIterator = contentIterator.getStart().plusMinutes(contentIterator.getDuration());
            if (endContentIterator.compareTo(actualTime) < 0){
                LocalTime differenceTime = actualTime.minusHours(endContentIterator.getHour());
                differenceTime = differenceTime.minusMinutes(endContentIterator.getMinute());
                if (differenceTime.compareTo(previousDifferenceTime) == -1){
                    response = contentIterator;
                    previousDifferenceTime = differenceTime;
                }
            }
        }
        return response;
    }

    public Content getLaterContentOfTime(LocalTime actualTime) {
        if (actualTime == null ) {
            throw new IllegalArgumentException("Actual Time must not be null");
        }
        Content response = null;
        LocalTime previousDifferenceTime = LocalTime.of(23, 59, 59);
        for (int i = 0; i < this.contents.size(); i++) {
            Content contentIterator = this.contents.get(i);
            LocalTime startContentIterator = contentIterator.getStart();
            if (startContentIterator.compareTo(actualTime) > 0){
                LocalTime differenceTime = startContentIterator.minusHours(actualTime.getHour());
                differenceTime = differenceTime.minusMinutes(actualTime.getMinute());
                if (differenceTime.compareTo(previousDifferenceTime) == -1){
                    response = contentIterator;
                    previousDifferenceTime = differenceTime;
                }
            }
        }
        return response;
    }
}
