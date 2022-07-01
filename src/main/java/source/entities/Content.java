package source.entities;

import source.utility.Helper;

public class Content {
    private final String title;
    private final String summary;
    private final int durationInMinutes;

    public static int MINIMUM_TIME = 30;
    public static int MAXIMUM_TIME = 1440;

    public Content() {
        this.title = "";
        this.summary = "";
        this.durationInMinutes = 0;
    }

    public Content(String title, String summary, int durationInMinutes) {
        Helper.ValidateStringIsNullOrEmpty("Title", title);
        Helper.ValidateStringIsNullOrEmpty("Summary", summary);
        ValidateDurationInMinutes(durationInMinutes);

        this.title = title;
        this.summary = summary;
        this.durationInMinutes = durationInMinutes;
    }

    public int getDurationInMinutes() { return this.durationInMinutes; }
    public String getTitle() { return this.title; }
    public String getSummary() { return this.summary; }

    private void ValidateDurationInMinutes(int durationInMinutes) {
        if (durationInMinutes < MINIMUM_TIME || durationInMinutes > MAXIMUM_TIME) {
            throw new IllegalStateException("Duration in minutes should be greater or equal than " + MINIMUM_TIME + " or less or equal than " + MAXIMUM_TIME);
        }
    }
}
