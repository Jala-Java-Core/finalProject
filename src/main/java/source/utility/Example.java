package source.utility;

import source.ContentManager;
import source.entities.*;
import source.entities.content.PreRecorded;
import source.entities.content.Stream;

import java.time.DayOfWeek;
import java.util.LinkedHashSet;
import java.util.TreeMap;

public class Example {
    public static ContentManager GetContentManager() {
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

        Season seasonJuly2022 = new Season(ScheduleType.MONTHLY, "2022-07-01");
        Season seasonPersonalized = new Season("2022-06-01", "2022-07-31");

        LinkedHashSet<String> datesInSchedule2 = new LinkedHashSet<>(){};
        datesInSchedule2.add("2022-06-06");

        // Geo TV Channel
        Guide programmedGuideMonthly = new Guide(seasonJuly2022);
        programmedGuideMonthly.saveRecurringSchedule(schedule1, DayOfWeek.FRIDAY);

        Guide programmedGuidePersonalized = new Guide(seasonPersonalized);
        programmedGuidePersonalized.saveRepeatedSchedule(schedule2, datesInSchedule2);

        Channel geoTvChannel  = new Channel("Geo TV");
        geoTvChannel.saveGuide(programmedGuideMonthly);
        geoTvChannel.saveGuide(programmedGuidePersonalized);

        geoTvChannel.saveSchedule(schedule1);
        geoTvChannel.saveSchedule(schedule2);

        geoTvChannel.saveContent(dollyDarling);
        geoTvChannel.saveContent(littleSunshine);
        geoTvChannel.saveContent(liveStream);
        geoTvChannel.saveContent(masterChef);
        geoTvChannel.saveContent(newsAbc);

        // Sony TV
        Content onlineVideos = new Stream("Online Videos", "Summary of Online Videos", 1440);
        Content musicVideo = new PreRecorded("Music Videos", "Summary of Music Videos", 30);

        Channel sonyTvChannel = new Channel("Sony TV");
        sonyTvChannel.saveContent(onlineVideos);
        sonyTvChannel.saveContent(musicVideo);

        TreeMap<String, Content> timeTableScheduleSony1 = new TreeMap<>();
        timeTableScheduleSony1.put("00:00:00", onlineVideos);
        Schedule scheduleSony1 = new Schedule(timeTableScheduleSony1);

        TreeMap<String, Content> timeTableScheduleSony2 = new TreeMap<>();
        timeTableScheduleSony2.put("08:30:00", musicVideo);
        timeTableScheduleSony2.put("09:30:00", musicVideo);
        timeTableScheduleSony2.put("10:00:00", musicVideo);
        timeTableScheduleSony2.put("12:00:00", musicVideo);
        timeTableScheduleSony2.put("19:00:00", musicVideo);
        Schedule scheduleSony2 = new Schedule(timeTableScheduleSony2);

        Season seasonAnnually = new Season(ScheduleType.ANNUALLY, "2022-07-01");
        Guide programmedGuideAnnually = new Guide(seasonAnnually);
        programmedGuideAnnually.saveRecurringSchedule(scheduleSony1, DayOfWeek.FRIDAY);

        Season seasonWeekJuly = new Season(ScheduleType.WEEKLY, "2022-07-13");
        Guide programmedGuideWeekly = new Guide(seasonWeekJuly);
        LinkedHashSet<String> datesInScheduleSony2 = new LinkedHashSet<>(){};
        datesInScheduleSony2.add("2022-07-02");
        datesInScheduleSony2.add("2022-07-03");
        datesInScheduleSony2.add("2022-07-15");
        programmedGuideAnnually.saveRepeatedSchedule(scheduleSony2, datesInScheduleSony2);

        LinkedHashSet<String> datesInScheduleSony3 = new LinkedHashSet<>(){};
        datesInScheduleSony3.add("2022-07-16");

        Schedule scheduleSony3 = new Schedule(timeTableScheduleSony2);
        programmedGuideWeekly.saveRepeatedSchedule(scheduleSony3, datesInScheduleSony3);

        sonyTvChannel.saveSchedule(scheduleSony1);
        sonyTvChannel.saveSchedule(scheduleSony2);
        sonyTvChannel.saveSchedule(scheduleSony3);

        sonyTvChannel.saveGuide(programmedGuideAnnually);
        sonyTvChannel.saveGuide(programmedGuideWeekly);

        ContentManager contentManager = new ContentManager();
        contentManager.createChannel(geoTvChannel);
        contentManager.createChannel(sonyTvChannel);

        return contentManager;
    }
}
