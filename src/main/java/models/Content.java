package models;

public class Content {
    private int duration;
    private String title;
    private String summary;


    public Content(int duration, String title, String summary) {
        this.duration = duration;
        this.title = title;
        this.summary = summary;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
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
}