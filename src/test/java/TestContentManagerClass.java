import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import source.ContentManager;
import source.entities.*;
import source.entities.content.PreRecorded;
import source.entities.content.Stream;
import source.utility.ScheduleType;

import java.time.DayOfWeek;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestContentManagerClass {
    ContentManager contentManager;

    Content dollyDarling;
    Content littleSunshine;
    Content liveStream;
    Content masterChef;
    Content newsAbc;

    // TimeTable, Schedule and Season
    TreeMap<String, Content> timeTableSchedule;
    Schedule schedule;
    Season seasonMonthly;
    Guide programmedGuideMonthly;
    // Channel
    Channel geoTvChannel;

    @Before
    public void setup() {
        // Content
        dollyDarling = new PreRecorded("Dolly Darling", "Summary of Dolly Darling", 60);
        littleSunshine = new PreRecorded("Little Sunshine", "Summary of Little Sunshine", 90);
        liveStream = new Stream("Live Stream", "Summary of Live Stream", 60);
        masterChef = new PreRecorded("Master Chef", "Summary of Master Chef", 90);
        newsAbc = new Stream("News ABC", "Summary of News ABC", 60);
        // TimeTable
        timeTableSchedule = new TreeMap<>();
        AddElementsTimeTable(timeTableSchedule);
        // Schedule
        schedule = new Schedule(timeTableSchedule);
        // Season
        seasonMonthly = new Season(ScheduleType.MONTHLY, "2022-07-07");
        // Guide
        programmedGuideMonthly = new Guide(seasonMonthly);
        programmedGuideMonthly.saveRecurringSchedule(schedule, DayOfWeek.FRIDAY);
        // Channel
        geoTvChannel  = new Channel("Geo TV");
        geoTvChannel.saveGuide(programmedGuideMonthly);
        // Content Manager
        contentManager = new ContentManager();
        contentManager.createChannel(geoTvChannel);
    }

    private void AddElementsTimeTable(TreeMap<String, Content> timeTableSchedule) {
        timeTableSchedule.put("00:00:00", dollyDarling);
        timeTableSchedule.put("08:00:00", littleSunshine);
        timeTableSchedule.put("09:30:00", masterChef);
        timeTableSchedule.put("11:00:00", dollyDarling);
        timeTableSchedule.put("12:00:00", newsAbc);
        timeTableSchedule.put("13:00:00", liveStream);
        timeTableSchedule.put("18:00:00", littleSunshine);
    }

    @After
    public void cleanup() {
    }

    @Test
    public void nonExistingDateInSchedule() {
        setup();
        String dateInString = "2022-07-07"; // Saturday
        String timeInString = "11:45:00";
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Next: ", contentInformation.getNextToString());
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals("Content Title: ", contentInformation.getTitleToString());
    }

    @Test
    public void existingDateWithPreviousCurrentNext() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "11:45:00"; // previous valid, content valid, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(masterChef, contentInformation.getPrevious().getContent());
        assertEquals(dollyDarling, contentInformation.getCurrent().getContent());
        assertEquals(newsAbc, contentInformation.getNext().getContent());
    }

    @Test
    public void existingDateWithPrevious() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "23:50:00"; // previous valid, content empty, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(littleSunshine, contentInformation.getPrevious().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
        assertEquals("Content Title: ", contentInformation.getTitleToString());
    }

    @Test
    public void existingDateWithPreviousCurrent() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "13:50:00"; // previous valid, content valid, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(newsAbc, contentInformation.getPrevious().getContent());
        assertEquals(liveStream, contentInformation.getCurrent().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
    }

    @Test
    public void existingDateWithPreviousNext() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "15:30:00"; // previous valid, content empty, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(liveStream, contentInformation.getPrevious().getContent());
        assertEquals("Content Title: ", contentInformation.getTitleToString());
        assertEquals(littleSunshine, contentInformation.getNext().getContent());
    }

    @Test
    public void existingDateWithCurrent() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "18:30:00"; // previous empty, content valid, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals(littleSunshine, contentInformation.getCurrent().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
    }

    @Test
    public void existingDateWithCurrentNoPrevious() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "00:50:00"; // previous empty, content valid, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals(dollyDarling, contentInformation.getCurrent().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
    }

    @Test
    public void existingDateWithCurrentNext() {
        setup();
        String dateInString = "2022-07-08";
        String timeInString = "08:50:00"; // previous empty, content valid, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals(littleSunshine, contentInformation.getCurrent().getContent());
        assertEquals(masterChef, contentInformation.getNext().getContent());
    }
}
