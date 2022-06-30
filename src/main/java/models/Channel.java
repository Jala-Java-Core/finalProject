package models;

import enums.Season;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Channel {
    private String channel;
    private Guide guide;

    public Channel(String channel) {
        this.channel = channel;
        this.guide = null;
    }

    public Channel(String channel, Guide guide) {
        this.channel = channel;
        this.guide = guide;
    }

    // Setters and getters
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    // Custom methods
    public Guide addGuide(Season season, List<Schedule> schedules, List<Date> dates) {
        Guide createdGuide = new Guide(season, schedules, dates);
        setGuide(createdGuide);
        return createdGuide;
    }

    public void deleteGuide() {
        guide = null;
    }
}