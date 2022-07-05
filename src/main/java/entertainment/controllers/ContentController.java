package entertainment.controllers;

import entertainment.helpers.JsonHelper;
import entertainment.models.Channel;
import entertainment.models.Content;

import java.time.Duration;
import java.util.Map;

public class ContentController {
    private JsonHelper<Channel> jsonHelper;
    private Duration MIN_DURATION = Duration.ofMinutes(30);
    private Duration MAX_DURATION = Duration.ofHours(24);

    public ContentController() {
        jsonHelper = new JsonHelper<Channel>(Channel.class);
    }

    public void addContent(String channelNumber, Content content) {
        if (content.getDuration().compareTo(MAX_DURATION) > 0 || content.getDuration().compareTo(MIN_DURATION) < 0) {
            throw new IllegalArgumentException("Content duration should be multiples of 30 minutes, no less than 30 minutes and no more than 24 hours");
        }
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        channel.addContent(content);
        jsonHelper.saveCollection(channels);
    }

    public Map<String, Content> getAllContent(String channelNumber) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        return channel.getAllContent();
    }

    public Content getContent(String channelNumber, String contentId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Content content = channel.getContent(contentId);
        if (content == null) {
            throw new IllegalArgumentException("Content with id " + contentId + " does not exist");
        }
         return content;
    }

    public void updateContent(String channelNumber, Content content) {
        if (content.getDuration().compareTo(MAX_DURATION) > 0 || content.getDuration().compareTo(MIN_DURATION) < 0) {
            throw new IllegalArgumentException("Content duration should be multiples of 30 minutes, no less than 30 minutes and no more than 24 hours");
        }
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        Content oldContent = channel.getContent(content.getId());
        if (oldContent == null) {
            throw new IllegalArgumentException("Content with id " + content.getId() + " does not exist");
        }

        channel.addContent(content);
        jsonHelper.saveCollection(channels);
    }

    public void deleteContent(String channelNumber, String contentId) {
        Map<String, Channel> channels = jsonHelper.getCollection();
        Channel channel = channels.getOrDefault(channelNumber, null);
        if (channel == null) {
            throw new IllegalArgumentException("Channel number " + channelNumber + " does not exist");
        }
        if (!channel.hasContent(contentId)) {
            throw new IllegalArgumentException("Content with id " + contentId + " does not exist");
        }

        channel.deleteContent(contentId);
        jsonHelper.saveCollection(channels);
    }
}
