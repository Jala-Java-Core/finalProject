import enums.Season;
import models.*;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;

public class TestContentManager {
    ContentManager contentManager;
    List<ContentTime> contentTimes;
    List<Content> contents;
    List<Schedule> schedules;
    List<Schedule> otherSchedules;
    List<Date> dates;
    List<Date> otherDates;

    @BeforeEach
    public void setup() {
        contentManager = new ContentManager();

        contentTimes = new ArrayList<ContentTime>();

        contentTimes.add(new ContentTime(LocalTime.of(7,00), LocalTime.of(8,00)));
        contentTimes.add(new ContentTime(LocalTime.of(9,00), LocalTime.of(10,00)));
        contentTimes.add(new ContentTime(LocalTime.of(10,00), LocalTime.of(11,00)));
        contentTimes.add(new ContentTime(LocalTime.of(11,00), LocalTime.of(12,00)));
        contentTimes.add(new ContentTime(LocalTime.of(14,00), LocalTime.of(15,00)));

        contents = new ArrayList<Content>();

        contents.add(new StaticContent(60, "Deewangi", "Hersha meets cheff Dominnic, and start a new relationship while the Hotel event begins.", "path/to/content1"));
        contents.add(new StaticContent(60, "Dolly Darling", "Hersha meets cheff Dominnic, and start a new relationship while the Hotel event begins.", "path/to/content2"));
        contents.add(new StreamContent(60, "Live transmission 1", "Someone playing a videogame"));
        contents.add(new StreamContent(60, "Live transmission 2", "A program about Oscar awards"));
        contents.add(new StreamContent(60, "Live transmission 3", "Another streaming of someone playing videogames"));

        schedules = new ArrayList<Schedule>();

        schedules.add(new Schedule("Deewangi at 7:00", contents.get(0), contentTimes.get(0)));
        schedules.add(new Schedule("Dolly Darling at 9:00", contents.get(1), contentTimes.get(1)));
        schedules.add(new Schedule("Live transmission 1 at 10:00", contents.get(2), contentTimes.get(2)));
        schedules.add(new Schedule("Live transmission 2 at 11:00", contents.get(3), contentTimes.get(3)));
        schedules.add(new Schedule("Live transmission 3 at 14:00", contents.get(4), contentTimes.get(4)));

        otherSchedules = new ArrayList<Schedule>();

        otherSchedules.add(new Schedule("Deewangi at 7:00", contents.get(0), contentTimes.get(0)));
        otherSchedules.add(new Schedule("Dolly Darling at 9:00", contents.get(1), contentTimes.get(1)));
        otherSchedules.add(new Schedule("Live transmission 1 at 10:00", contents.get(2), contentTimes.get(2)));

        dates = new ArrayList<Date>();

        dates.add(new Date(2022, 5, 29));
        dates.add(new Date(2022, 6, 6));
        dates.add(new Date(2022, 6, 13));
        dates.add(new Date(2022, 6, 20));
        dates.add(new Date(2022, 6, 27));

        otherDates = new ArrayList<Date>();

        otherDates.add(new Date(2022, 5, 29));
        otherDates.add(new Date(2022, 6, 6));
        otherDates.add(new Date(2022, 6, 13));
    }

    @After
    public void cleanup() {

    }

    @Test
    public void testCreateContent() {
        contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        contentManager.createSchedule("Deewangi at 7:00", "GEO TV", contents.get(0), contentTimes.get(0));

        Content createdContent = contentManager.createContent("Deewangi at 7:00", "GEO TV", "Deewangi", "Hersha meets cheff Dominnic, and start a new relationship while the Hotel event begins.", 60);

        Assertions.assertNotNull(createdContent);
        Assertions.assertEquals("Deewangi", createdContent.getTitle());
        Assertions.assertEquals("Hersha meets cheff Dominnic, and start a new relationship while the Hotel event begins.", createdContent.getSummary());
        Assertions.assertEquals(60, createdContent.getDuration());
    }

    @Test
    public void testCreateSchedule() {
        contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        contentManager.createSchedule("Deewangi at 7:00", "GEO TV", contents.get(0), contentTimes.get(0));

        Schedule createdSchedule = contentManager.getSchedule("GEO TV", "Deewangi at 7:00");

        Assertions.assertNotNull(createdSchedule);
        Assertions.assertEquals("Deewangi at 7:00", createdSchedule.getSchedule());
        Assertions.assertEquals(contents.get(0), createdSchedule.getContent());
        Assertions.assertEquals(contentTimes.get(0), createdSchedule.getContentTime());
    }

    @Test
    public void testUpdateSchedule() {
        contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        Schedule createdSchedule = contentManager.getSchedule("GEO TV", "Deewangi at 7:00");

        Assertions.assertNotNull(createdSchedule);
        Assertions.assertEquals("Deewangi at 7:00", createdSchedule.getSchedule());
        Assertions.assertEquals(contents.get(0), createdSchedule.getContent());
        Assertions.assertEquals(contentTimes.get(0), createdSchedule.getContentTime());

        boolean isUpdated = contentManager.updateSchedule("Dolly Darling at 9:00", "GEO TV", contents.get(1), contentTimes.get(1));
        Schedule updatedSchedule = contentManager.getSchedule("GEO TV", "Dolly Darling at 9:00");

        Assertions.assertEquals(true, isUpdated);
        Assertions.assertNotNull(updatedSchedule);
        Assertions.assertEquals("Dolly Darling at 9:00", updatedSchedule.getSchedule());
        Assertions.assertEquals(contents.get(1), updatedSchedule.getContent());
        Assertions.assertEquals(contentTimes.get(1), updatedSchedule.getContentTime());
    }

    @Test
    public void testDeleteSchedule() {
        contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        Schedule createdSchedule = contentManager.getSchedule("GEO TV", "Deewangi at 7:00");

        Assertions.assertNotNull(createdSchedule);
        Assertions.assertEquals("Deewangi at 7:00", createdSchedule.getSchedule());
        Assertions.assertEquals(contents.get(0), createdSchedule.getContent());
        Assertions.assertEquals(contentTimes.get(0), createdSchedule.getContentTime());

        boolean isDeleted = contentManager.deleteSchedule("Deewangi at 7:00", "GEO TV");
        Schedule deletedSchedule = contentManager.getSchedule("GEO TV", "Deewangi at 7:00");

        Assertions.assertEquals(true, isDeleted);
        Assertions.assertNull(deletedSchedule);
    }

    @Test
    public void testCreateGuide() {
        contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        Channel channel = contentManager.getChannel("GEO TV");

        Guide createdGuide = channel.getGuide();

        Assertions.assertNotNull(createdGuide);
        Assertions.assertEquals(Season.MONTH, createdGuide.getSeason());
        Assertions.assertEquals(schedules, createdGuide.getSchedules());
        Assertions.assertEquals(dates, createdGuide.getDates());
    }

    @Test
    public void testUpdateGuide() {
        contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        Channel channel = contentManager.getChannel("GEO TV");

        Guide guide = channel.getGuide();

        Assertions.assertNotNull(guide);
        Assertions.assertEquals(Season.MONTH, guide.getSeason());
        Assertions.assertEquals(schedules, guide.getSchedules());
        Assertions.assertEquals(dates, guide.getDates());

        boolean isUpdated = contentManager.updateGuide("GEO TV", Season.YEAR, otherSchedules, otherDates);
        Guide updatedGuide = channel.getGuide();

        Assertions.assertEquals(true, isUpdated);
        Assertions.assertNotNull(updatedGuide);
        Assertions.assertEquals(Season.YEAR, updatedGuide.getSeason());
        Assertions.assertEquals(otherSchedules, updatedGuide.getSchedules());
        Assertions.assertEquals(otherDates, updatedGuide.getDates());
    }

    @Test
    public void testDeleteGuide() {
        contentManager.createChannel("GEO TV");

        Guide createdGuide = contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        Assertions.assertNotNull(createdGuide);
        Assertions.assertEquals(Season.MONTH, createdGuide.getSeason());
        Assertions.assertEquals(schedules, createdGuide.getSchedules());
        Assertions.assertEquals(dates, createdGuide.getDates());

        boolean isDeleted = contentManager.deleteGuide("GEO TV");
        Channel channel = contentManager.getChannel("GEO TV");
        Guide deletedGuide = channel.getGuide();

        Assertions.assertEquals(true, isDeleted);
        Assertions.assertNull(deletedGuide);
    }

    @Test
    public void testCreateChannel() {
        Assertions.assertNull(contentManager.getChannel("GEO TV"));

        contentManager.createChannel("GEO TV");

        Channel createdChannel = contentManager.getChannel("GEO TV");

        Assertions.assertNotNull(createdChannel);
        Assertions.assertEquals("GEO TV", createdChannel.getChannel());
    }

    @Test
    public void testPreviewChannelWithPreviousAndNext() {
        Channel createdChannel = contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        ChannelPreview channelPreview = contentManager.previewChannel(createdChannel, new Date(2022, 5, 29), LocalTime.of(10, 30));

        Content currentContent = channelPreview.getSchedule().getContent();
        Content previousContent = channelPreview.getPreviousSchedule().getContent();
        Content nextContent = channelPreview.getNextSchedule().getContent();

        Assertions.assertEquals("Live transmission 1", currentContent.getTitle());
        Assertions.assertEquals(60, currentContent.getDuration());
        Assertions.assertEquals("Someone playing a videogame", currentContent.getSummary());
        Assertions.assertEquals(30, channelPreview.getTimeRemaining());

        Assertions.assertEquals("Dolly Darling", previousContent.getTitle());
        Assertions.assertEquals("Live transmission 2", nextContent.getTitle());
    }

    @Test
    public void testPreviewChannelWithPreviousButNoNext() {
        Channel createdChannel = contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        ChannelPreview channelPreview = contentManager.previewChannel(createdChannel, new Date(2022, 5, 29), LocalTime.of(14, 30));

        Content currentContent = channelPreview.getSchedule().getContent();
        Content previousContent = channelPreview.getPreviousSchedule().getContent();
        Schedule nextSchedule = channelPreview.getNextSchedule();

        Assertions.assertEquals("Live transmission 3", currentContent.getTitle());
        Assertions.assertEquals(60, currentContent.getDuration());
        Assertions.assertEquals("Another streaming of someone playing videogames", currentContent.getSummary());
        Assertions.assertEquals(30, channelPreview.getTimeRemaining());

        Assertions.assertEquals("Live transmission 2", previousContent.getTitle());
        Assertions.assertEquals(null, nextSchedule);
    }

    @Test
    public void testPreviewChannelWithNextButNoPrevious() {
        Channel createdChannel = contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        ChannelPreview channelPreview = contentManager.previewChannel(createdChannel, new Date(2022, 5, 29), LocalTime.of(7, 30));

        Content currentContent = channelPreview.getSchedule().getContent();
        Schedule previousSchedule = channelPreview.getPreviousSchedule();
        Content nextContent = channelPreview.getNextSchedule().getContent();

        Assertions.assertEquals("Deewangi", currentContent.getTitle());
        Assertions.assertEquals(60, currentContent.getDuration());
        Assertions.assertEquals("Hersha meets cheff Dominnic, and start a new relationship while the Hotel event begins.", currentContent.getSummary());
        Assertions.assertEquals(30, channelPreview.getTimeRemaining());

        Assertions.assertEquals(null, previousSchedule);
        Assertions.assertEquals("Dolly Darling", nextContent.getTitle());
    }

    @Test
    public void testPreviewChannelWithNextAndPreviousButNotCurrent() {
        Channel createdChannel = contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        ChannelPreview channelPreview = contentManager.previewChannel(createdChannel, new Date(2022, 5, 29), LocalTime.of(8, 30));

        Schedule currentSchedule = channelPreview.getSchedule();
        Content previousContent = channelPreview.getPreviousSchedule().getContent();
        Content nextContent = channelPreview.getNextSchedule().getContent();

        Assertions.assertEquals(null, currentSchedule);
        Assertions.assertEquals(0, channelPreview.getTimeRemaining());

        Assertions.assertEquals("Deewangi", previousContent.getTitle());
        Assertions.assertEquals("Dolly Darling", nextContent.getTitle());
    }

    @Test
    public void testPreviewChannelWithNextButNotCurrentOrPrevious() {
        Channel createdChannel = contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        ChannelPreview channelPreview = contentManager.previewChannel(createdChannel, new Date(2022, 5, 29), LocalTime.of(6, 30));

        Schedule currentSchedule = channelPreview.getSchedule();
        Schedule previousSchedule = channelPreview.getPreviousSchedule();
        Content nextContent = channelPreview.getNextSchedule().getContent();

        Assertions.assertEquals(null, currentSchedule);
        Assertions.assertEquals(0, channelPreview.getTimeRemaining());

        Assertions.assertEquals(null, previousSchedule);
        Assertions.assertEquals("Deewangi", nextContent.getTitle());
    }

    @Test
    public void testPreviewChannelWithPreviousButNotCurrentOrNext() {
        Channel createdChannel = contentManager.createChannel("GEO TV");

        contentManager.createGuide("GEO TV", Season.MONTH, schedules, dates);

        ChannelPreview channelPreview = contentManager.previewChannel(createdChannel, new Date(2022, 5, 29), LocalTime.of(15, 30));

        Schedule currentSchedule = channelPreview.getSchedule();
        Content previousContent = channelPreview.getPreviousSchedule().getContent();
        Schedule nextSchedule = channelPreview.getNextSchedule();

        Assertions.assertEquals(null, currentSchedule);
        Assertions.assertEquals(0, channelPreview.getTimeRemaining());

        Assertions.assertEquals("Live transmission 3", previousContent.getTitle());
        Assertions.assertEquals(null, nextSchedule);
    }
}
