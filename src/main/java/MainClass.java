import source.ContentManager;
import source.entities.*;
import source.entities.content.PreRecorded;
import source.entities.content.Stream;
import source.menu.MainMenu;
import source.utility.ScheduleType;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.TreeMap;

public class MainClass {
    public static void main(String[] args) throws IOException {
        ContentManager contentManager = CreateContentManager();
        MainMenu menu =  new MainMenu();
        menu.showMainMenu();
    }

    private static ContentManager CreateContentManager() {
        // channel GEO TV
        Content dollyDarling = new PreRecorded("Dolly Darling", "Summary of Dolly Darling", 60);
        Content littleSunshine = new PreRecorded("Little Sunshine", "Summary of Little Sunshine", 90);
        Content liveStream = new Stream("Live Stream", "Summary of Live Stream", 60);
        Content masterChef = new PreRecorded("Master Chef", "Summary of Master Chef", 90);
        Content newsAbc = new Stream("News ABC", "Summary of News ABC", 60);

        TreeMap<String, Content> timeTableSchedule1 = new TreeMap<>();
        timeTableSchedule1.put("00:00:00", dollyDarling);
        timeTableSchedule1.put("08:00:00", littleSunshine);
        timeTableSchedule1.put("09:30:00", masterChef);
        timeTableSchedule1.put("11:00:00", dollyDarling);
        timeTableSchedule1.put("12:00:00", newsAbc);
        timeTableSchedule1.put("13:00:00", liveStream);
        timeTableSchedule1.put("18:00:00", littleSunshine);

        TreeMap<String, Content> timeTableSchedule2 = new TreeMap<>();
        timeTableSchedule2.put("09:00:00", littleSunshine);
        timeTableSchedule2.put("12:00:00", dollyDarling);
        timeTableSchedule2.put("17:00:00", liveStream);

        Schedule schedule1 = new Schedule(timeTableSchedule1);
        Schedule schedule2 = new Schedule(timeTableSchedule2);

        Season seasonJune2022 = new Season("06/01/2022", "06/30/2022");
        Season seasonAnnually = new Season(ScheduleType.ANNUALLY, "06/30/2022");

        LinkedHashSet<String> datesInSchedule1 = new LinkedHashSet<>(){};
        datesInSchedule1.add("06/06/2022");
        datesInSchedule1.add("06/13/2022");
        datesInSchedule1.add("06/20/2022");
        datesInSchedule1.add("06/27/2022");

        datesInSchedule1.add("06/08/2022");
        datesInSchedule1.add("06/15/2022");
        datesInSchedule1.add("06/22/2022");
        datesInSchedule1.add("06/29/2022");

        Guide programmedGuideMonthly = new Guide(seasonJune2022);
        programmedGuideMonthly.saveRepeatedSchedule(schedule1, datesInSchedule1);

        Channel geoTvChannel  = new Channel();
        geoTvChannel.createGuide(programmedGuideMonthly);

        Guide programmedGuideAnnually = new Guide(seasonAnnually);
        programmedGuideAnnually.saveRecurringSchedule(schedule2, DayOfWeek.MONDAY);

        Channel sonyTabChannel = new Channel();
        sonyTabChannel.createGuide(programmedGuideAnnually);

        ContentManager contentManager = new ContentManager();
        contentManager.createChannel(geoTvChannel);
        contentManager.createChannel(sonyTabChannel);

        // ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "18:30:00"); // previous empty, content valid, next empty
        // ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "15:30:00"); // previous valid, content empty, next valid
        // ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "13:50:00"); // previous valid, content valid, next empty
        // ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "00:50:00"); // previous empty, content valid, next empty
        // ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "08:50:00"); // previous empty, content valid, next valid
        // ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "11:50:00"); // previous valid, content valid, next valid
        ContentInformation contentInformation = contentManager.preview(geoTvChannel, "06/27/2022", "23:50:00"); // previous valid, content empty, next empty
        System.out.println( "Content Title: " + contentInformation.getContent().getTitle());
        System.out.println( "Content Summary: " + contentInformation.getContent().getSummary());
        System.out.println( "Duration in Minutes: " + contentInformation.getContent().getDurationInMinutes());
        System.out.println( "Remaining Time: " + contentInformation.getTimeRemaining());
        System.out.println( "Previous: " + contentInformation.getPreviousContent().getTitle());
        System.out.println( "Next: " + contentInformation.getNextContent().getTitle());

        return contentManager;
    }
}
