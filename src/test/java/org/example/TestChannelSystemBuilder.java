package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChannelSystemBuilder {
    ChannelSystemBuilder channelSystemBuilder;

    @BeforeEach
    public void setup() {
        channelSystemBuilder = new ChannelSystemBuilder();
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getChannels() {
        JSONArray channels = channelSystemBuilder.getChannelsJson();
        JSONObject channel = (JSONObject) channels.get(0);
        assertEquals(channel.get("name"), "Comedy Central");
    }

    @Test
    public void getGuides() {
        JSONArray guides = channelSystemBuilder.getGuidesJson();
        JSONObject guide = (JSONObject) guides.get(0);
        assertEquals(guide.get("name"), "Week 1");
        assertEquals(guide.get("yearStart").toString(), "2022");
        assertEquals(guide.get("monthStart").toString(), "6");
        assertEquals(guide.get("dayStart").toString(), "27");
        assertEquals(guide.get("yearEnd").toString(), "2022");
        assertEquals(guide.get("monthEnd").toString(), "7");
        assertEquals(guide.get("dayEnd").toString(), "3");
    }

    @Test
    public void getSchedules() {
        JSONArray schedules = channelSystemBuilder.getSchedulesJson();
        JSONObject schedule1 = (JSONObject) schedules.get(0);
        JSONObject schedule2 = (JSONObject) schedules.get(1);
        assertEquals(schedule1.get("name"), "Schedule 1");
        assertEquals(schedule2.get("name"), "Schedule 2");
    }

    @Test
    public void getContentsJson() {
        JSONArray contents = channelSystemBuilder.getContentsJson();
        JSONObject content = (JSONObject) contents.get(0);
        assertEquals(content.get("schedule"), "schedule 1");
        assertEquals(content.get("contentType"), "static content");
        assertEquals(content.get("title"), "Maraton South park");
        assertEquals(content.get("description"), "Aventuras de 4 amigos que van a la escuela");
        assertEquals(content.get("duration").toString(), "360");
        assertEquals(content.get("hoursStart").toString(), "0");
        assertEquals(content.get("minutesStart").toString(), "0");
        assertEquals(content.get("secondsStart").toString(), "0");
    }

    @Test
    public void getComedyCentralChannel() {
        Channel comedyCentralChannel = channelSystemBuilder.getComedyCentralChannel();
        assertEquals(comedyCentralChannel.getName(), "Comedy Central");
    }
}
