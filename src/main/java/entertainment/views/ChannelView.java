package entertainment.views;

import entertainment.controllers.ChannelController;
import entertainment.enums.ChannelMenuOption;
import entertainment.enums.MainMenuOption;
import entertainment.models.ChannelPreview;

import java.io.IOException;
import java.util.Scanner;

public class ChannelView extends BaseView{
    private ChannelController channelController = new ChannelController();
    private String channelNumber;
    private String channelName;

    public ChannelView(String channelNumber) {
        super(ChannelMenuOption.BACK);
        this.channelNumber = channelNumber;
        this.channelName = channelController.getChannelByNumber(channelNumber).getName();
    }

    public void showMenu() {
        System.out.println("******************** Channel Manager ********************");
        System.out.println("Managing channel: " + this.channelName);
        System.out.println("1. Content");
        System.out.println("2. Schedules");
        System.out.println("3. Guides");
        System.out.println("4. Preview");
        System.out.println("5. Back");
        System.out.print("Select option: ");

        return;
    }

    @Override
    public void ExecuteOption() {
        Scanner in = new Scanner(System.in);

        switch (option) {
            case ChannelMenuOption.CONTENT:
                ContentView contentView = new ContentView(this.channelNumber);
                try {
                    contentView.start();
                } catch (IOException ioException) {
                    System.out.println("Something went wrong with channel content management menu.");
                }
                this.option = MainMenuOption.START;
                break;
            case ChannelMenuOption.SCHEDULES:
                ScheduleView scheduleView = new ScheduleView(this.channelNumber);
                try {
                    scheduleView.start();
                } catch (IOException ioException) {
                    System.out.println("Something went wrong with channel schedules management menu.");
                }
                this.option = MainMenuOption.START;
                break;
            case ChannelMenuOption.GUIDES:
                GuideView guideView = new GuideView(this.channelNumber);
                try {
                    guideView.start();
                } catch (IOException ioException) {
                    System.out.println("Something went wrong with channel guides management menu.");
                }
                this.option = MainMenuOption.START;
                break;
            case ChannelMenuOption.PREVIEW:
                System.out.println("************************ Preview ************************");
                ChannelPreview preview = channelController.preview(this.channelNumber);
                System.out.println(preview);
                break;
            default: break;
        }
    }
}
