public class TimeSlot {
    private Content content;
    private StringBuilder timeStart = new StringBuilder();
    private StringBuilder timeEnd = new StringBuilder();

    public TimeSlot(Content content, String timeStart) {
        this.content = content;
        this.timeStart.append(timeStart);
        this.timeEnd.append(HourHelp.getHourHelp().calculateEndHour(timeStart, content.duration.toString()));
    }

    public void setContentTitle(String newTitle) {
        this.content.title.setLength(0);
        this.content.title.append(newTitle);
    }

    public void setDuration(String newDuration) {
        this.content.duration.setLength(0);
        this.content.duration.append(newDuration);
        this.timeEnd.setLength(0);
        this.timeEnd.append(HourHelp.getHourHelp().calculateEndHour(timeStart.toString(), content.duration.toString()));
    }

    public void setTimeStart(String newStart) {
        this.timeStart.setLength(0);
        this.timeStart.append(newStart);
        this.content.duration.setLength(0);
        this.content.duration.append(HourHelp.getHourHelp().calculateDuration(newStart, this.timeEnd.toString()));
    }

    public void setSummary(String newSummary) {
        this.content.summaryOrDescription.setLength(0);
        this.content.summaryOrDescription.append(newSummary);
    }

    public String getTimeStart() {
        return timeStart.toString();
    }

    public String getTimeEnd() {
        return timeEnd.toString();
    }

    public String getContentTitle() {
        return content.title.toString();
    }

    public String getDuration() { return content.duration.toString();}

    public String getSummary() {return content.summaryOrDescription.toString();}
}
