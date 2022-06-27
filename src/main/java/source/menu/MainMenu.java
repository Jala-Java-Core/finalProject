package source.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainMenu {

    private String option = null;

    public void showMainMenu() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("**************************Menu***************************");
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("3. Option 3");
        System.out.println("4. Option 4");
        System.out.println("5. Exit");
        System.out.print("Select option: ");
        option = "";
        while(!option.equals("5")) {
            if (in.hasNextLine()) {
                option = in.nextLine();
                System.out.println(option);
            }
            switch (option) {
                case "1":
                    this.selectOption(option);
                    break;
                case "2":
                    this.selectOption(option);
                    break;
                case "3":
                    this.selectOption(option);
                    break;
                case "4":
                    this.selectOption(option);
                    break;
                default: break;
            }
        }

        return;
    }

    public void selectOption(String option) throws IOException {
        cls();
        System.out.println("Selected option is " + option);
        System.out.println("Press a key to continue");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        cls();
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
