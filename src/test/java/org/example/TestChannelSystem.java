package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestChannelSystem {

    ChannelSystem channelSystem;

    @BeforeEach
    public void setup() {
        channelSystem = new ChannelSystem();
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void getChannels() {
        assertEquals(channelSystem.getChannels().size(), 0);
    }

    @Test
    public void addChannel() {
        Channel channel = new Channel("My Channel");
        channelSystem.addChannel(channel);
        assertEquals(channelSystem.getChannels().size(), 1);
    }

    @Test
    public void addChannelWithNullChannel() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.addChannel(null);
        });
    }

    @Test
    public void getChannel() {
        Channel channel = new Channel("My Channel");
        channelSystem.addChannel(channel);

        assertEquals(channelSystem.getChannel("My Channel").getName(), "My Channel");
    }

    @Test
    public void getChannelWithNullChannelName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.getChannel(null);
        });
    }

    @Test
    public void getChannelWithEmptyChannelName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.getChannel("");
        });
    }

    @Test
    public void getChannelWithChannelNameThatDoesNotExist() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.getChannel("My Channel");
        });
    }

    @Test
    public void removeChannel() {
        Channel channel = new Channel("My Channel");
        channelSystem.addChannel(channel);

        channelSystem.removeChannel("My Channel");

        assertEquals(channelSystem.getChannels().size(), 0);
    }

    @Test
    public void removeChannelWithNullChannelName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.removeChannel(null);
        });
    }

    @Test
    public void removeChannelWithEmptyChannelName() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.removeChannel("");
        });
    }

    @Test
    public void removeChannelWithChannelNameThatDoesNotExist() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.removeChannel("My Channel");
        });
    }

    @Test
    public void editChannel() {
        Channel channel = new Channel("My Channel");
        channelSystem.addChannel(channel);

        Channel channel2 = new Channel("My Channel");

        channelSystem.editChannel(channel2);

        assertEquals(channelSystem.getChannels().size(), 1);
        assertEquals(channelSystem.getChannels().get("My Channel").getName(), "My Channel");
    }

    @Test
    public void editChannelWithNullChannel() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            channelSystem.editChannel(null);
        });
    }

    @Test
    public void editChannelWithChannelNameThatDoesNotExist() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Channel channel2 = new Channel("My Channel");
            channelSystem.editChannel(channel2);
        });
    }

}
