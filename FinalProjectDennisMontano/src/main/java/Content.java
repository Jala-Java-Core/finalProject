public class Content {
    ContentType type;
    StringBuilder duration = new StringBuilder();
    StringBuilder title = new StringBuilder();
    StringBuilder summaryOrDescription = new StringBuilder();

    public Content(ContentType type, String duration, String title, String summary){
        this.type = type;
        this.duration.append(duration);
        this.title.append(title);
        this.summaryOrDescription.append(summary);
    }
}
