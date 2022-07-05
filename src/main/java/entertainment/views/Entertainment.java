package entertainment.views;

import entertainment.controllers.ChannelController;
import entertainment.enums.MainMenuOption;
import entertainment.models.Channel;

import java.io.IOException;
import java.util.*;

public class Entertainment extends BaseView {
    private ChannelController channelController = new ChannelController();

    public Entertainment() {
        super(MainMenuOption.QUIT);
    }

    public void showMenu() {
        System.out.println("************************* Menu **************************");
        System.out.println("1. Show channels");
        System.out.println("2. Add channel");
        System.out.println("3. Update channel");
        System.out.println("4. Delete channel");
        System.out.println("5. Manage channel");
        System.out.println("6. Quit");
        System.out.print("Select option: ");

        return;
    }

    public void ExecuteOption() {
        Scanner in = new Scanner(System.in);

        switch (this.option) {
            case MainMenuOption.SHOW_CHANNELS:
                Map<String, Channel> channels = channelController.getChannels();
                for (String channelNumber: channels.keySet()) {
                    System.out.println(channels.get(channelNumber));
                }
                break;
            case MainMenuOption.ADD_CHANNEL:
                System.out.println("********************** New Channel **********************");
                System.out.println("Enter channel number: ");
                String channelNumber = in.nextLine();
                System.out.println("Enter channel name: ");
                String channelName = in.nextLine();
                Channel channel = new Channel(channelNumber, channelName);
                channelController.addChannel(channel);
                break;
            case MainMenuOption.UPDATE_CHANNEL:
                System.out.println("******************* Update a Channel ********************");
                System.out.println("Enter channel number: ");
                channelNumber = in.nextLine();
                System.out.println("Enter channel name: ");
                channelName = in.nextLine();
                channel = new Channel(channelNumber, channelName);
                channelController.updateChannel(channel);
                break;
            case MainMenuOption.DELETE_CHANNEL:
                System.out.println("******************* Delete a Channel ********************");
                System.out.println("Enter channel number: ");
                channelNumber = in.nextLine();
                channelController.deleteChannel(channelNumber);
                break;
            case MainMenuOption.MANAGE_CHANNEL:
                System.out.println("Enter channel number: ");
                channelNumber = in.nextLine();
                ChannelView channelView = new ChannelView(channelNumber);
                try {
                    channelView.start();
                } catch (IOException ioException) {
                    System.out.println("Something went wrong with channel management menu.");
                }
                this.option = MainMenuOption.START;
                break;
            default: break;
        }
    }
}


