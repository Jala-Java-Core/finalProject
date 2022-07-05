package entertainment.views;

import entertainment.controllers.ChannelController;
import entertainment.controllers.GuideController;
import entertainment.controllers.ScheduleController;
import entertainment.enums.GuideMenuOption;
import entertainment.enums.Season;
import entertainment.models.Guide;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class GuideView extends BaseView{
    private ChannelController channelController = new ChannelController();
    private GuideController guideController = new GuideController();
    private ScheduleController scheduleController = new ScheduleController();
    private String channelNumber;
    private String channelName;

    public GuideView(String channelNumber) {
        super(GuideMenuOption.BACK);
        this.channelNumber = channelNumber;
        this.channelName = channelController.getChannelByNumber(channelNumber).getName();
    }

    public void showMenu() {
        System.out.println("********************* Guides Manager ********************");
        System.out.println("1. List Guides");
        System.out.println("2. Add guide");
        System.out.println("3. Delete guide");
        System.out.println("4. List all schedules");
        System.out.println("5. Add schedule");
        System.out.println("6. Add schedule - Recurring");
        System.out.println("7. Delete schedule");
        System.out.println("8. Back");
        System.out.print("Select option: ");

        return;
    }

    @Override
    public void ExecuteOption() {
        Scanner in = new Scanner(System.in);

        switch (option) {
            case GuideMenuOption.LIST_GUIDES:
                System.out.println("*********************** All Guides **********************");
                String guides = guideController.showGuides(this.channelNumber);
                System.out.println(guides);
                break;
            case GuideMenuOption.ADD_GUIDE:
                System.out.println("*********************** New Guide ***********************");
                System.out.println("Enter season [WEEK or MONTH]: ");
                Season season = Season.valueOf(in.nextLine());
                System.out.println("Enter start date [ENTER for current date]: ");
                LocalDate startDate;
                String stringDate = in.nextLine();
                startDate= LocalDate.now();
                if (!stringDate.trim().isEmpty()) {
                    startDate = LocalDate.parse(stringDate);
                }
                Guide guide = new Guide(season, startDate);
                guideController.addGuide(this.channelNumber, guide);
                break;
            case GuideMenuOption.DELETE_GUIDE:
                System.out.println("********************* Delete Guide **********************");
                System.out.println("Enter guide Id: ");
                String guideId = in.nextLine();
                guideController.deleteGuide(this.channelNumber, guideId);
                break;
            case GuideMenuOption.LIST_ALL_SCHEDULES:
                System.out.println("********************* All Schedules *********************");
                String schedules = scheduleController.showSchedules(this.channelNumber);
                System.out.println(schedules);
                break;
            case GuideMenuOption.ADD_SCHEDULE:
                System.out.println("***************** Add Schedule to Guide *****************");
                System.out.println("Enter guide Id: ");
                guideId = in.nextLine();
                System.out.println("Enter schedule Id: ");
                String scheduleId = in.nextLine();
                System.out.println("Enter repetitions: ");
                int repetitions = Integer.parseInt(in.nextLine());
                guideController.addSchedule(this.channelNumber, guideId, scheduleId, repetitions);
                break;
            case GuideMenuOption.ADD_RECURRING_SCHEDULE:
                System.out.println("***************** Add Schedule to Guide *****************");
                System.out.println("********************* Recurring days ********************");
                System.out.println("Enter guide Id: ");
                guideId = in.nextLine();
                System.out.println("Enter schedule Id: ");
                scheduleId = in.nextLine();
                System.out.println("Enter day of week: ");
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(in.nextLine());
                guideController.addRecurringSchedule(this.channelNumber, guideId, scheduleId, dayOfWeek);
                break;
            case GuideMenuOption.DELETE_SCHEDULE:
                System.out.println("************** Delete Schedule from Guide ***************");
                System.out.println("Enter guide Id: ");
                guideId = in.nextLine();
                System.out.println("Enter schedule day: ");
                String date = in.nextLine();
                guideController.deleteSchedule(this.channelNumber, guideId, date);
                break;
            default: break;
        }
    }
}
