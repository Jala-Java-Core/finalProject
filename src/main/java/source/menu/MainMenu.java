package source.menu;

import source.ContentManager;
import source.utility.Example;
import source.utility.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainMenu {

    private String option = null;

    public void showMainMenu() throws IOException {
        ContentManager contentManager = Example.GetContentManager();
        Scanner in = new Scanner(System.in);
        System.out.println("************************** Menu ***************************");
        System.out.println("1. Show Channels");
        System.out.println("2. Show Contents based on Channel");
        System.out.println("3. Show Schedules based on Channel");
        System.out.println("4. Show Guides based on Channel");
        System.out.println("5. Preview based on channel, time and date");
        System.out.println("6. Exit");
        System.out.print("Select option: ");
        option = "";
        String channelString = "";
        while(!option.equals("6")) {
            if (in.hasNextLine()) {
                option = in.nextLine();
                System.out.println(option);
            }
            switch (option) {
                case "1":
                    System.out.println(contentManager.getChannelsToString());
                    this.selectOption(option);
                    break;
                case "2":
                    System.out.print("Channel: ");
                    channelString = in.nextLine();
                    System.out.println(contentManager.getContentBasedOnChannelToString(channelString));
                    this.selectOption(option);
                    break;
                case "3":
                    System.out.print("Channel: ");
                    channelString = in.nextLine();
                    System.out.println(contentManager.getSchedulesBasedOnChannelToString(channelString));
                    this.selectOption(option);
                    break;
                case "4":
                    System.out.print("Channel: ");
                    channelString = in.nextLine();
                    System.out.println(contentManager.getGuidesBasedOnChannelToString(channelString));
                    this.selectOption(option);
                    break;
                case "5":
                    System.out.print("Channel: ");
                    channelString = in.nextLine();
                    System.out.print("Date (" + LocalDate.now().format(Helper.formatterDate) + "): ");
                    String dateString = in.nextLine();
                    System.out.print("Time (" + LocalTime.now().format(Helper.formatterTime) + "): ");
                    String timeString = in.nextLine();
                    System.out.println(contentManager.getPreview(channelString, dateString, timeString));
                    this.selectOption(option);
                    break;
                default: break;
            }
        }

        return;
    }

    public void selectOption(String option) throws IOException {
        System.out.println("Selected option is " + option);
        System.out.println("Press a enter to continue");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        showMainMenu();
    }

    public void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
            for(int clear = 0; clear < 1000; clear++) {
                System.out.println("\b") ;
            }
        } catch (InterruptedException ex) {
        } catch (IOException e) {
            for(int clear = 0; clear < 1000; clear++) {
                System.out.println("\b") ;
            }
        }
    }


}
