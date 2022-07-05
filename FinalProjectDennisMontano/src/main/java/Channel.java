public class Channel {

    private StringBuilder channelName = new StringBuilder();
    private Guide guide;

    public Channel(String name) {
        channelName.append(name);
    }

    public String getChannelName() {
        return this.channelName.toString();
    }

    public void addGuide(Guide guide) {
        this.guide = guide;
    }

    public void removeGuide() {
        this.guide = null;
    }

    public Guide getGuide() {
        return guide;
    }
}
