package entertainment.models;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;

public class ChannelPreview extends BaseModel {
    private Channel channel;
    private ScheduleContent scheduleContent;
    private Content content;

    public ChannelPreview(Channel channel) {
        this.channel = channel;
        this.scheduleContent = this.channel.getCurrentScheduledContent();
        this.content = this.channel.getContent(scheduleContent.getContentId());
    }

    @Override
    public String toString() {
        return "Title: " + content.getTitle() +
                "\nDuration: " + content.getDuration().toString().replace("PT", "").toLowerCase() +
                "\nRemaining: " + scheduleContent.getRemainingTime().toString().replace("PT", "").toLowerCase() +
                "\nSummary: " + content.getSummary();
    }
}
