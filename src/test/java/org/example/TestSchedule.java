package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSchedule {
    Schedule sc;

    @BeforeEach
    public void setup() {
        sc = new Schedule();
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getContents() {
        assertEquals(sc.getContentList().size(), 0);
    }

    @Test
    public void addContent1() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);
        sc.addContent(content);
        LinkedList<Content> contents = sc.getContentList();

        assertEquals(contents.size(), 1);
        assertEquals(contents.getFirst().getTitle(), "title");
    }

    @Test
    public void addContent2() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(11,30,0);
        StaticContent content2 = new StaticContent(title2, description2,duration2,start2);

        sc.addContent(content);
        sc.addContent(content2);
        LinkedList<Content> contents = sc.getContentList();

        assertEquals(contents.size(), 2);
        assertEquals(contents.getFirst().getTitle(), "title");
        assertEquals(contents.get(1).getTitle(), "title2");
    }

    @Test
    public void addContentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.addContent(null);
        });
    }

    @Test
    public void addContentIsOverwritingExistingContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(12,15,0);
        StaticContent content2 = new StaticContent(title2, description2,duration2,start2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.addContent(content);
            sc.addContent(content2);
        });
    }

    @Test
    public void deleteContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(12,30,0);
        StaticContent content2 = new StaticContent(title2, description2,duration2,start2);

        sc.addContent(content);
        sc.addContent(content2);

        sc.deleteContent(title, start);
        LinkedList<Content> contents = sc.getContentList();
        assertEquals(contents.size(), 1);
        assertEquals(contents.getFirst().getTitle(), "title2");

    }

    @Test
    public void deleteContentWithTitleNull() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.deleteContent(null, start);
        });
    }

    @Test
    public void deleteContentWithTitleEmpty() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.deleteContent("", start);
        });
    }

    @Test
    public void deleteContentWithTimeNull() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.deleteContent("title", null);
        });
    }

    @Test
    public void deleteContentWithDataDoesNotExist() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.deleteContent("title1", start);
        });
    }

    @Test
    public void editContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(12, 30, 0);
        StaticContent content2 = new StaticContent(title2, description2, duration2, start2);

        String title3 = "title3";
        String description3 = "description3";
        int duration3 = 30;
        LocalTime start3 = LocalTime.of(17, 0, 0);
        LiveContent content3 = new LiveContent(title3, description3, duration3, start3);

        sc.addContent(content);
        sc.addContent(content2);

        sc.editContent(title, start, content3);
        LinkedList<Content> contents = sc.getContentList();
        assertEquals(contents.size(), 2);
        assertEquals(contents.getFirst().getTitle(), "title2");
        assertEquals(contents.get(1).getTitle(), "title3");
    }

    @Test
    public void editContentWithTitleNull() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(17, 0, 0);
        LiveContent content2 = new LiveContent(title2, description2, duration2, start2);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.editContent(null, start, content2);
        });
    }

    @Test
    public void editContentWithTitleEmpty() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(17, 0, 0);
        LiveContent content2 = new LiveContent(title2, description2, duration2, start2);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.editContent("", start, content2);
        });
    }

    @Test
    public void editContentWithTimeNull() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(17, 0, 0);
        LiveContent content2 = new LiveContent(title2, description2, duration2, start2);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.editContent("title", null, content2);
        });
    }

    @Test
    public void editContentWithNewNodeNull() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.editContent("title", null, null);
        });
    }

    @Test
    public void editContentWithDataDoesNotExist() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(17, 0, 0);
        LiveContent content2 = new LiveContent(title2, description2, duration2, start2);


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.editContent("title1", start, content2);
        });
    }

    @Test
    public void getContentByHour() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12,0,0);
        StaticContent content = new StaticContent(title, description,duration,start);

        sc.addContent(content);

        LocalTime time = LocalTime.of(12, 15, 0);
        Content foundContent = sc.getContentByHour(time);
        assertEquals(foundContent.getTitle(), "title");

    }

    @Test
    public void getContentByHourDoesNotFindContent() {
        LocalTime time = LocalTime.of(12, 15, 0);
        Content foundContent = sc.getContentByHour(time);
        assertEquals(foundContent, null);

    }

    @Test
    public void getContentByHourWithTimeNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.getContentByHour(null);
        });
    }

    @Test
    public void getPreviousContentOfContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(12, 30, 0);
        StaticContent content2 = new StaticContent(title2, description2, duration2, start2);

        sc.addContent(content);
        sc.addContent(content2);

        Content previousContent = sc.getPreviousContentOfContent(content2);
        assertEquals(previousContent.getTitle(), content.getTitle());
    }

    @Test
    public void getPreviousContentOfContentDoesNotFindContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(11, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        sc.addContent(content);

        Content foundContent = sc.getPreviousContentOfContent(content);
        assertEquals(foundContent, null);

    }

    @Test
    public void getPreviousContentOfContentWithActualContentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.getPreviousContentOfContent(null);
        });
    }

    @Test
    public void getLaterContentOfContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(12, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 30;
        LocalTime start2 = LocalTime.of(12, 30, 0);
        StaticContent content2 = new StaticContent(title2, description2, duration2, start2);

        sc.addContent(content);
        sc.addContent(content2);

        Content laterContent = sc.getLaterContentOfContent(content);
        assertEquals(laterContent.getTitle(), content2.getTitle());
    }

    @Test
    public void getLaterContentOfContentDoesNotFindContent() {
        String title = "title";
        String description = "description";
        int duration = 30;
        LocalTime start = LocalTime.of(11, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        sc.addContent(content);

        Content foundContent = sc.getLaterContentOfContent(content);
        assertEquals(foundContent, null);

    }

    @Test
    public void getLaterContentOfContentWithActualContentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sc.getLaterContentOfContent(null);
        });
    }

    @Test
    public void getPreviousContentOfTime() {
        String title = "title";
        String description = "description";
        int duration = 60;
        LocalTime start = LocalTime.of(12, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 60;
        LocalTime start2 = LocalTime.of(17, 0, 0);
        StaticContent content2 = new StaticContent(title2, description2, duration2, start2);

        sc.addContent(content);
        sc.addContent(content2);

        LocalTime timeWithoutContent = LocalTime.of(20, 0, 0);

        Content previousContent = sc.getPreviousContentOfTime(timeWithoutContent);
        assertEquals(previousContent.getTitle(), content2.getTitle());
    }

    @Test
    public void getPreviousContentOfTimeDoesNotFindContent() {
        LocalTime timeWithoutContent = LocalTime.of(20, 0, 0);
        Content foundContent = sc.getPreviousContentOfTime(timeWithoutContent);
        assertEquals(foundContent, null);

    }

    @Test
    public void getPreviousContentOfTimeWithActualContentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Content foundContent = sc.getPreviousContentOfTime(null);
        });
    }

    @Test
    public void getLaterContentOfTime() {
        String title = "title";
        String description = "description";
        int duration = 60;
        LocalTime start = LocalTime.of(12, 0, 0);
        StaticContent content = new StaticContent(title, description, duration, start);

        String title2 = "title2";
        String description2 = "description2";
        int duration2 = 60;
        LocalTime start2 = LocalTime.of(17, 0, 0);
        StaticContent content2 = new StaticContent(title2, description2, duration2, start2);

        sc.addContent(content);
        sc.addContent(content2);

        LocalTime timeWithoutContent = LocalTime.of(15, 0, 0);

        Content previousContent = sc.getLaterContentOfTime(timeWithoutContent);
        assertEquals(previousContent.getTitle(), content2.getTitle());
    }

    @Test
    public void getLaterContentOfTimeDoesNotFindContent() {
        LocalTime timeWithoutContent = LocalTime.of(20, 0, 0);
        Content foundContent = sc.getLaterContentOfTime(timeWithoutContent);
        assertEquals(foundContent, null);

    }

    @Test
    public void getLaterContentOfTimeWithActualContentNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Content foundContent = sc.getLaterContentOfTime(null);
        });
    }

}
