package entertainment.views;

import entertainment.controllers.ChannelController;
import entertainment.controllers.ContentController;
import entertainment.controllers.ScheduleController;
import entertainment.enums.ScheduleMenuOption;
import entertainment.models.Content;
import entertainment.models.Schedule;
import entertainment.models.ScheduleContent;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Map;
import java.util.Scanner;

public class ScheduleView extends BaseView{
    private ChannelController channelController = new ChannelController();
    private ContentController contentController = new ContentController();
    private ScheduleController scheduleController = new ScheduleController();
    private String channelNumber;
    private String channelName;

    public ScheduleView(String channelNumber) {
        super(ScheduleMenuOption.BACK);
        this.channelNumber = channelNumber;
        this.channelName = channelController.getChannelByNumber(channelNumber).getName();
    }

    public void showMenu() {
        System.out.println("******************* Schedules Manager *******************");
        System.out.println("Managing channel: " + this.channelName);
        System.out.println("1. List schedules");
        System.out.println("2. Add schedule");
        System.out.println("3. Delete schedule");
        System.out.println("4. List all content");
        System.out.println("5. Add content");
        System.out.println("6. Delete content");
        System.out.println("7. Back");
        System.out.print("Select option: ");

        return;
    }

    @Override
    public void ExecuteOption() {
        Scanner in = new Scanner(System.in);

        switch (option) {
            case ScheduleMenuOption.LIST_SCHEDULES:
                System.out.println("********************* All Schedules *********************");
                String schedules = scheduleController.showSchedules(this.channelNumber);
                System.out.println(schedules);
                break;
            case ScheduleMenuOption.ADD_SCHEDULE:
                System.out.println("********************* New Schedule **********************");
                Schedule schedule = new Schedule();
                scheduleController.addSchedule(this.channelNumber, schedule);
                System.out.println("New schedule Id is: " + schedule.getId());
                break;
            case ScheduleMenuOption.DELETE_SCHEDULE:
                System.out.println("******************* Delete Schedule *********************");
                System.out.println("Enter schedule Id: ");
                String scheduleId = in.nextLine();
                scheduleController.deleteSchedule(this.channelNumber, scheduleId);
                break;
            case ScheduleMenuOption.LIST_ALL_CONTENT:
                System.out.println("********************** All content **********************");
                Map<String, Content> shows = contentController.getAllContent(this.channelNumber);
                for (String contentId: shows.keySet()) {
                    System.out.println(shows.get(contentId));
                }
                break;
            case ScheduleMenuOption.ADD_CONTENT:
                System.out.println("**************** Add Content to Schedule ****************");
                System.out.println("Enter schedule Id: ");
                scheduleId = in.nextLine();
                System.out.println("Enter content Id: ");
                String contentId = in.nextLine();
                System.out.println("Enter content start time [hh:mm]: ");
                String contentStartTime = in.nextLine();
                Content content = contentController.getContent(this.channelNumber, contentId);
                LocalTime startTime = LocalTime.parse(contentStartTime+":00");
                ScheduleContent scheduleContent = new ScheduleContent(contentId, startTime, startTime.plusSeconds(content.getDuration().getSeconds()));
                scheduleController.addContent(this.channelNumber, scheduleId, scheduleContent);
                break;
            case ScheduleMenuOption.DELETE_CONTENT:
                System.out.println("************** Delete Content from Schedule *************");
                System.out.println("Enter schedule Id: ");
                scheduleId = in.nextLine();
                System.out.println("Enter Id of content on schedule: ");
                String scheduleContentId = in.nextLine();
                scheduleController.deleteContent(this.channelNumber, scheduleId, scheduleContentId);
                break;
            default: break;
        }
    }
}
