package source.entities.content;

import source.entities.Content;

// Live Transmission
public class Stream extends Content {

    public Stream(String title, String summary, int durationInMinutes) {
        super(title, summary, durationInMinutes);
    }
}
