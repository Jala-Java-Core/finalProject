package entertainment.views;

import entertainment.controllers.ChannelController;
import entertainment.controllers.ContentController;
import entertainment.enums.ContentMenuOption;
import entertainment.models.Channel;
import entertainment.models.Content;

import java.time.Duration;
import java.util.Map;
import java.util.Scanner;

public class ContentView extends BaseView{
    private ChannelController channelController = new ChannelController();
    private ContentController contentController = new ContentController();
    private String channelNumber;
    private String channelName;

    public ContentView(String channelNumber) {
        super(ContentMenuOption.BACK);
        this.channelNumber = channelNumber;
        this.channelName = channelController.getChannelByNumber(channelNumber).getName();
    }

    public void showMenu() {
        System.out.println("******************** Content Manager ********************");
        System.out.println("Managing channel: " + this.channelName);
        System.out.println("1. List all content");
        System.out.println("2. Add content");
        System.out.println("3. Update content");
        System.out.println("4. Delete content");
        System.out.println("5. Back");
        System.out.print("Select option: ");

        return;
    }

    @Override
    public void ExecuteOption() {
        Scanner in = new Scanner(System.in);

        switch (option) {
            case ContentMenuOption.LIST_ALL_CONTENT:
                System.out.println("********************** All content **********************");
                Map<String, Content> shows = contentController.getAllContent(this.channelNumber);
                for (String contentId: shows.keySet()) {
                    System.out.println(shows.get(contentId));
                }
                break;
            case ContentMenuOption.ADD_CONTENT:
                System.out.println("********************** New Content **********************");
                System.out.println("Enter content title: ");
                String contentTitle = in.nextLine();
                System.out.println("Enter content summary: ");
                String contentSummary = in.nextLine();
                System.out.println("Enter content duration (example 5h30m): ");
                String contentDuration = in.nextLine();
                Content content = new Content(contentTitle, contentSummary, Duration.parse("PT" + contentDuration));
                contentController.addContent(this.channelNumber, content);
                break;
            case ContentMenuOption.UPDATE_CONTENT:
                System.out.println("********************* Update Content ********************");
                System.out.println("Enter content id: ");
                String contentId = in.nextLine();
                Channel channel = channelController.getChannelByNumber(this.channelNumber);
                if (!channel.hasContent(contentId)) {
                    System.out.println("Content id doesn't exist");
                    break;
                }
                System.out.println("Enter content title: ");
                contentTitle = in.nextLine();
                System.out.println("Enter content summary: ");
                contentSummary = in.nextLine();
                System.out.println("Enter content duration (example 5h30m): ");
                contentDuration = in.nextLine();
                content = new Content(contentTitle, contentSummary, Duration.parse("PT" + contentDuration));
                content.setId(contentId);
                contentController.updateContent(this.channelNumber, content);
                break;
            case ContentMenuOption.DELETE_CONTENT:
                System.out.println("********************* Delete Content ********************");
                System.out.println("Enter content id: ");
                contentId = in.nextLine();
                contentController.deleteContent(this.channelNumber, contentId);
                break;
            default: break;
        }
    }
}
