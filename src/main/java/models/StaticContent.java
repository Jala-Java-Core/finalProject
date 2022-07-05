package models;

public class StaticContent extends Content {
    private String fileURL;

    public StaticContent(int duration, String title, String summary, String fileURL) {
        super(duration, title, summary);
        this.fileURL = fileURL;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
}