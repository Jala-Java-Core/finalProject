import org.junit.After;
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

    @BeforeEach
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

        geoTvChannel.saveSchedule(schedule);

        geoTvChannel.saveContent(dollyDarling);
        geoTvChannel.saveContent(littleSunshine);
        geoTvChannel.saveContent(liveStream);
        geoTvChannel.saveContent(masterChef);
        geoTvChannel.saveContent(newsAbc);
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
        String dateInString = "2022-07-07"; // Saturday
        String timeInString = "11:45:00";
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Next: ", contentInformation.getNextToString());
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals("Content Title: ", contentInformation.getTitleToString());
    }

    @Test
    public void existingDateWithPreviousCurrentNext() {
        String dateInString = "2022-07-08";
        String timeInString = "11:45:00"; // previous valid, content valid, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(masterChef, contentInformation.getPrevious().getContent());
        assertEquals(dollyDarling, contentInformation.getCurrent().getContent());
        assertEquals(newsAbc, contentInformation.getNext().getContent());
    }

    @Test
    public void existingDateWithPrevious() {
        String dateInString = "2022-07-08";
        String timeInString = "23:50:00"; // previous valid, content empty, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(littleSunshine, contentInformation.getPrevious().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
        assertEquals("Content Title: ", contentInformation.getTitleToString());
    }

    @Test
    public void existingDateWithPreviousCurrent() {
        String dateInString = "2022-07-08";
        String timeInString = "13:50:00"; // previous valid, content valid, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(newsAbc, contentInformation.getPrevious().getContent());
        assertEquals(liveStream, contentInformation.getCurrent().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
    }

    @Test
    public void existingDateWithPreviousNext() {
        String dateInString = "2022-07-08";
        String timeInString = "15:30:00"; // previous valid, content empty, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals(liveStream, contentInformation.getPrevious().getContent());
        assertEquals("Content Title: ", contentInformation.getTitleToString());
        assertEquals(littleSunshine, contentInformation.getNext().getContent());
    }

    @Test
    public void existingDateWithCurrent() {
        String dateInString = "2022-07-08";
        String timeInString = "18:30:00"; // previous empty, content valid, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals(littleSunshine, contentInformation.getCurrent().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
    }

    @Test
    public void existingDateWithCurrentNoPrevious() {
        String dateInString = "2022-07-08";
        String timeInString = "00:50:00"; // previous empty, content valid, next empty
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals(dollyDarling, contentInformation.getCurrent().getContent());
        assertEquals("Next: ", contentInformation.getNextToString());
    }

    @Test
    public void existingDateWithCurrentNext() {
        String dateInString = "2022-07-08";
        String timeInString = "08:50:00"; // previous empty, content valid, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, dateInString, timeInString);
        assertEquals("Previous: ", contentInformation.getPreviousToString());
        assertEquals(littleSunshine, contentInformation.getCurrent().getContent());
        assertEquals(masterChef, contentInformation.getNext().getContent());
    }

    // Exceptions
    @Test
    public void nonExistingChannel() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> contentManager.getGuidesBasedOnChannelToString("Non Existing TV"));
    }

    @Test
    public void outRangeDurationContentLessThanMinimum30() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Content contentWithDurationLessThan30 = new PreRecorded("Sports ABC", "Summary of Sports ABC", 29);
            contentManager.getChannelBasedOnName("Geo TV").saveContent(contentWithDurationLessThan30);
        });
    }

    @Test
    public void outRangeDurationContentGreaterThanMaximum1440() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            Content contentWithDurationGreaterThan1440 = new PreRecorded("Sports ABC", "Summary of Sports ABC", 1441);
            contentManager.getChannelBasedOnName("Geo TV").saveContent(contentWithDurationGreaterThan1440);
        });
    }

    @Test
    public void saveContentInSchedule() {
        assertEquals(7, schedule.getTimeTable().size());
        schedule.saveContentInTime("20:00:00", liveStream);
        assertEquals(8, schedule.getTimeTable().size());
    }

    @Test
    public void deleteContentInSchedule() {
        String tenPm = "22:00:00";
        schedule.saveContentInTime(tenPm, liveStream);
        assertEquals(8, schedule.getTimeTable().size());
        schedule.deleteTime(tenPm);
        assertEquals(7, schedule.getTimeTable().size());
    }

    @Test
    public void deleteSchedule() {
        assertEquals(1, geoTvChannel.getSchedules().size());
        TreeMap<String, Content> timeTableToDeleteSchedule = new TreeMap<>();
        Schedule scheduleToDelete = new Schedule(timeTableToDeleteSchedule);
        scheduleToDelete.saveContentInTime("20:00:00", liveStream);
        geoTvChannel.saveSchedule(scheduleToDelete);
        assertEquals(2, geoTvChannel.getSchedules().size());
        geoTvChannel.deleteSchedule(scheduleToDelete);
        assertEquals(1, geoTvChannel.getSchedules().size());
    }
}
