package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class ChannelSystemMenu {

    private String option;
    private LocalDateTime dateTime;
    private ChannelSystem channelSystem;
    private Channel comedyCentralChannel;

    public ChannelSystemMenu(ChannelSystem channelSystem) {
        this.option = "";
        this.dateTime = null;
        this.channelSystem = channelSystem;
        this.comedyCentralChannel = channelSystem.getChannel("Comedy Central");
    }

    public void showMainMenu() throws IOException {
        this.displayMenu();
        Scanner in = new Scanner(System.in);
        option = "";
        do {
            if (in.hasNextLine()) {
                option = in.nextLine();
                System.out.println(option);
            }
            this.cls();
            switch (option) {
                case "1":
                    System.out.println("Enter date (YYYY-MM-DD): ");
                    String date = in.nextLine();
                    if (validateDate(date)){
                        System.out.println("Enter time (HH:MM:SS): ");
                        String time = in.nextLine();
                        if (validateTime(time)) {
                            dateTime = LocalDateTime.parse(date + "T" + time);
                            Content chanelPreviewContents[] = comedyCentralChannel.channelPreview(dateTime);
                            System.out.println("\n");
                            displayChannelPreviewContent(chanelPreviewContents, dateTime);
                        }
                    }
                    this.selectNewOption();
                    break;
                case "2":
                    dateTime = LocalDateTime.now();
                    Content chanelPreviewContents[] = comedyCentralChannel.channelPreview(dateTime);
                    displayChannelPreviewContent(chanelPreviewContents, dateTime);
                    this.selectNewOption();
                    break;
                case "3":
                    break;
                default:
                    System.out.println(option + " is not a valid option");
                    this.selectNewOption();
                    break;
            }
        }

        while(!option.equals("3"));
        return;
    }

    public void displayChannelPreviewContent(Content[] contents, LocalDateTime dateTime) {
        Content actualContent = contents[0];
        Content previousContent = contents[1];
        Content nextContent = contents[2];

        if (actualContent != null){
            LocalTime actualTime = LocalTime.of(dateTime.getHour(),dateTime.getMinute(), dateTime.getSecond());
            LocalTime endTime = actualContent.getStart().plusMinutes(actualContent.getDuration());
            LocalTime remaining = endTime.minusHours(actualTime.getHour());
            remaining = remaining.minusMinutes(actualTime.getMinute());
            remaining = remaining.minusSeconds(actualTime.getSecond());
            System.out.println("Title: " + actualContent.getTitle());
            System.out.println("Duration: " + actualContent.getDuration());
            System.out.println("Summary: " + actualContent.getDescription());
            System.out.println("Remaining: " + remaining);
        } else {
            System.out.println("Title: ");
            System.out.println("Duration: ");
            System.out.println("Summary: ");
            System.out.println("Remaining: ");
        }
        if (previousContent != null){
            System.out.println("Previous: " + previousContent.getTitle());
        } else {
            System.out.println("Previous: ");
        }
        if (nextContent != null){
            System.out.println("Next: " + nextContent.getTitle());
        } else {
            System.out.println("Next: ");
        }
    }

    public void selectNewOption() throws IOException {
        System.out.println("");
        System.out.println("Press Enter key to continue");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        cls();
        showMainMenu();
    }

    public void displayMenu() {
        cls();
        System.out.println("**************************Menu***************************");
        System.out.println("1. Get channel preview of specific hour of Comedy Central Channel");
        System.out.println("2. Get channel preview of actual hour of Comedy Central Channel");
        System.out.println("3. Exit");
        System.out.print("Select option: ");
    }

    public boolean validateDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (Exception e){
            System.out.println("");
            System.out.println("Invalid date");
            return false;
        }
    }

    public boolean validateTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (Exception e){
            System.out.println("");
            System.out.println("Invalid time");
            return false;
        }
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