package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChannel {
    Channel channel;

    @BeforeEach
    public void setup() {
        channel = new Channel("My Channel");
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getName() {
        assertEquals(channel.getName(), "My Channel");
    }

    @Test
    public void getGuides() {
        assertEquals(channel.getGuides().size(), 0);
    }

    @Test
    public void addGuide() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);
        Guide guide = new Guide(date1, date2);
        channel.addGuide(guide, "guide1");
        assertEquals(channel.getGuides().size(), 1);
    }

    @Test
    public void addGuide2() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);
        LocalDate date3 = LocalDate.of(2022, 5, 11);
        LocalDate date4 = LocalDate.of(2022, 5, 20);

        Guide guide = new Guide(date1, date2);
        Guide guide2 = new Guide(date3, date4);

        channel.addGuide(guide, "guide1");
        channel.addGuide(guide2, "guide2");
        assertEquals(channel.getGuides().size(), 2);
    }

    @Test
    public void addGuide2WithOverwriting() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);
        LocalDate date3 = LocalDate.of(2022, 5, 20);

        Guide guide = new Guide(date1, date2);
        Guide guide2 = new Guide(date2, date3);

        channel.addGuide(guide, "guide1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.addGuide(guide2, "guide2");
        });
    }

    @Test
    public void addGuideWithNewGuideNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.addGuide(null, "guide1");
        });
    }

    @Test
    public void addGuideWithNewGuideNameNull() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);

        Guide guide = new Guide(date1, date2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.addGuide(guide, null);
        });
    }

    @Test
    public void addGuideWithNewGuideNameEmpty() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);

        Guide guide = new Guide(date1, date2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.addGuide(guide, "");
        });
    }
    @Test
    public void removeGuide() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);
        Guide guide = new Guide(date1, date2);
        channel.addGuide(guide, "guide1");

        channel.removeGuide("guide1");
        assertEquals(channel.getGuides().size(), 0);
    }
    @Test
    public void removeGuideThatDoesNotExist() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.removeGuide("guide1");
        });
    }

    @Test
    public void removeGuideWithGuideNameEmpty() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);

        Guide guide = new Guide(date1, date2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.removeGuide("");
        });
    }

    @Test
    public void removeGuideWithGuideNameNull() {
        LocalDate date1 = LocalDate.of(2022, 5, 1);
        LocalDate date2 = LocalDate.of(2022, 5, 10);

        Guide guide = new Guide(date1, date2);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channel.removeGuide(null);
        });
    }

    @Test
    public void getGuideByDate() {
        LocalDate date1 = LocalDate.of(2022,6,1);
        LocalDate date2 = LocalDate.of(2022,6,2);
        Guide guide = new Guide(date1, date2);

        channel.addGuide(guide, "Guide 1");
        Guide foundGuide = channel.getGuideByDate(date1);

        assertEquals(foundGuide.getStart(), date1);
        assertEquals(foundGuide.getEnd(), date2);
    }

    @Test
    public void getScheduleByDayWithNullDate() {
        LocalDate date1 = LocalDate.of(2022,6,1);
        LocalDate date2 = LocalDate.of(2022,6,2);
        Guide guide = new Guide(date1, date2);

        channel.addGuide(guide, "Guide 1");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Guide foundGuide = channel.getGuideByDate(null);
        });
    }

    @Test
    public void channelPreviewWithParameter() {
        ChannelSystemBuilder channelSystemBuilder = new ChannelSystemBuilder();
        Channel channel = channelSystemBuilder.getComedyCentralChannel();
        LocalDateTime date = LocalDateTime.of(2022,7,1,12,0,0);
        Content response[] = channel.channelPreview(date);
        assertEquals(response[0].getTitle(), "Maraton vecinos");
        assertEquals(response[1].getTitle(), "Maraton Casa de los dibujos");
        assertEquals(response[2].getTitle(), "Maraton Familia Peluche");
    }

    @Test
    public void channelPreviewDoesNotReturnPreviousAndLaterContent() {
        ChannelSystemBuilder channelSystemBuilder = new ChannelSystemBuilder();
        Channel channel = channelSystemBuilder.getComedyCentralChannel();
        LocalDateTime date = LocalDateTime.of(2022,7,2,14,30,0);
        Content response[] = channel.channelPreview(date);
        assertEquals(response[0].getTitle(), "Maraton Principe del rap");
        assertEquals(response[1], null);
        assertEquals(response[2], null);
    }

    @Test
    public void channelPreviewDoesNotReturnActualContent() {
        ChannelSystemBuilder channelSystemBuilder = new ChannelSystemBuilder();
        Channel channel = channelSystemBuilder.getComedyCentralChannel();
        LocalDateTime date = LocalDateTime.of(2022,7,2,12,0,0);
        Content response[] = channel.channelPreview(date);
        assertEquals(response[0], null);
        assertEquals(response[1].getTitle(), "Maraton Nosostros los guapos");
        assertEquals(response[2].getTitle(), "Maraton Principe del rap");
    }

}
