package source.entities;

public class Content {
    private final String title;
    private final String summary;
    private final int durationInMinutes;

    public Content(String title, String summary, int durationInMinutes) {
        this.title = title;
        this.summary = summary;
        this.durationInMinutes = durationInMinutes;
    }
}
