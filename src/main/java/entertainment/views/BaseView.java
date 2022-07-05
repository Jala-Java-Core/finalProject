package entertainment.views;

import entertainment.enums.MainMenuOption;

import java.io.IOException;
import java.util.Scanner;

public abstract class BaseView {
    protected String option = MainMenuOption.START;
    private String quitOption;

    public BaseView(String quitOption) {
        this.quitOption = quitOption;
    }

    public abstract void showMenu();
    public abstract void ExecuteOption();

    public void start() throws IOException {
        Scanner in = new Scanner(System.in);

        cls();
        while(!option.equals(this.quitOption)) {
            showMenu();
            if (in.hasNextLine()) {
                cls();
                option = in.nextLine();
            }
            ExecuteOption();
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
