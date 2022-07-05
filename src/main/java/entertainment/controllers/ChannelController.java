package entertainment.controllers;

import entertainment.helpers.JsonHelper;
import entertainment.models.Channel;
import entertainment.models.ChannelPreview;

import java.util.Date;
import java.util.Map;

public class ChannelController {
    private JsonHelper<Channel> jsonHelper;

    public ChannelController() {
        jsonHelper = new JsonHelper<Channel>(Channel.class);
    }

    public void addChannel(Channel newChannel) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        if (channels.containsKey(newChannel.getId())) {
            return;
        }
        channels.put(newChannel.getId(), newChannel);
        jsonHelper.saveCollection(channels);
    }

    public Channel getChannelByNumber(String channelNumber) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        return channels.getOrDefault(channelNumber, null);
    }

    public void updateChannel(Channel channel) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        String channelId = channel.getId();
        Channel oldChannel = channels.getOrDefault(channelId, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelId + " does not exist");
        }
        channels.put(channelId, channel);
        jsonHelper.saveCollection(channels);
    }

    public void deleteChannel(String channelNumber) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        channels.remove(channelNumber);
        jsonHelper.saveCollection(channels);
    }

    public Map<String, Channel> getChannels() {
        return jsonHelper.getCollection();
    }

    public ChannelPreview preview(String channelNumber) {
        Channel channel = getChannelByNumber(channelNumber);
        return new ChannelPreview(channel);
    }
}
