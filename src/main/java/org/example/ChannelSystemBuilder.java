package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;

public class ChannelSystemBuilder {
    private String channelsPath;
    private String guidesPath;
    private String schedulesPath;
    private String contentsPath;
    private FileReaderTool reader;

    public ChannelSystemBuilder(){
        channelsPath = "src/main/resources/channels.json";
        guidesPath = "src/main/resources/guides.json";
        schedulesPath = "src/main/resources/schedules.json";
        contentsPath = "src/main/resources/contents.json";
        reader = new FileReaderTool();
    }

    public JSONArray getChannelsJson() {
        return reader.readJsonArrayFile(channelsPath);
    }

    public JSONArray getGuidesJson() {
        return reader.readJsonArrayFile(guidesPath);
    }

    public JSONArray getSchedulesJson() {
        return reader.readJsonArrayFile(schedulesPath);
    }

    public JSONArray getContentsJson() {
        return reader.readJsonArrayFile(contentsPath);
    }

    public Channel getComedyCentralChannel() {
        //build new channel
        JSONArray channels = this.getChannelsJson();
        JSONObject channelData = (JSONObject) channels.get(0);
        Channel channel = new Channel(channelData.get("name").toString());

        //build new guide
        JSONArray guides = this.getGuidesJson();
        JSONObject guideData = (JSONObject) guides.get(0);
        String guideName = guideData.get("name").toString();
        int yearStart = Integer.parseInt(guideData.get("yearStart").toString());
        int monthStart = Integer.parseInt(guideData.get("monthStart").toString());
        int dayStart = Integer.parseInt(guideData.get("dayStart").toString());
        int yearEnd = Integer.parseInt(guideData.get("yearEnd").toString());
        int monthEnd = Integer.parseInt(guideData.get("monthEnd").toString());
        int dayEnd = Integer.parseInt(guideData.get("dayEnd").toString());
        LocalDate dateStart = LocalDate.of(yearStart,monthStart,dayStart);
        LocalDate dateEnd = LocalDate.of(yearEnd, monthEnd, dayEnd);
        Guide guide = new Guide(dateStart, dateEnd);

        //build new schedules
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();

        //build new contents and add in respectively schedule
        JSONArray contents = this.getContentsJson();
        for (int i = 0; i < contents.size(); i++) {
            //build content
            JSONObject contentData = (JSONObject) contents.get(i);
            String title = contentData.get("title").toString();
            String description = contentData.get("description").toString();
            int duration = Integer.parseInt(contentData.get("duration").toString());
            int hoursStart = Integer.parseInt(contentData.get("hoursStart").toString());
            int minutesStart = Integer.parseInt(contentData.get("minutesStart").toString());
            int secondsStart = Integer.parseInt(contentData.get("secondsStart").toString());
            LocalTime time = LocalTime.of(hoursStart, minutesStart, secondsStart);
            String contentType = contentData.get("contentType").toString();
            Content newContent = null;
            if (contentType.equals("static content")) {
                newContent = new StaticContent(title,description,duration,time);
            } else {
                newContent = new LiveContent(title,description,duration,time);
            }

            // add content in schedule
            String schedule = contentData.get("schedule").toString();
            if (schedule.equals("schedule 1")) {
                schedule1.addContent(newContent);
            } else {
                schedule2.addContent(newContent);
            }
        }

        //add schedules in guide
        guide.addSchedule(schedule1, LocalDate.of(2022, 6, 27));
        guide.addSchedule(schedule2, LocalDate.of(2022, 6, 28));
        guide.addSchedule(schedule1, LocalDate.of(2022, 6, 29));
        guide.addSchedule(schedule2, LocalDate.of(2022, 6, 30));
        guide.addSchedule(schedule1, LocalDate.of(2022, 7, 1));
        guide.addSchedule(schedule2, LocalDate.of(2022, 7, 2));
        guide.addSchedule(schedule1, LocalDate.of(2022, 7, 3));

        //add guide in channel
        channel.addGuide(guide, guideName);
        return channel;
    }
}
