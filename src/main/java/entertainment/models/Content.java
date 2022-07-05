package entertainment.models;

import java.time.Duration;

public class Content extends BaseModel{
    private String title;
    private String summary;
    private Duration duration;

    public Content(String title, String summary, Duration duration) {
        this.title = title;
        this.summary = summary;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + this.getId() +
                ", title='" + title + '\'' +
                ", duration=" + duration.toString().replace("PT", "").toLowerCase() +
                '}';
    }
}
