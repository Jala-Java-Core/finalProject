import source.menu.MainMenu;
import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {

        MainMenu menu =  new MainMenu();
        menu.showMainMenu();

        //int dda_D4eds31f=0;
        int[] dda_D4eds31f = {};
        for(int da_D4es31=0;da_D4es31<dda_D4eds31f.length;da_D4es31++)
            if(dda_D4eds31f[da_D4es31]<0)da_D4es31 = da_D4es31 + 1;

        int ant = 0;
        int[] cow = {};
        for (int bat = 0; bat < cow.length; bat += 1){
            if (cow[bat] < 0) {
                ant = ant + 1;
            }
        }

        ant = 0;
        for (int bat = 0; bat < cow.length; bat += 1)
            if (cow[bat] < 0) ant = ant + 1;

        int count = 0;
        int[] values = {};
        for (int i = 0; i < values.length; i += 1)
            if (values[i] < 0) count = count + 1;

        count = 0;
        for (int i = 0; i < values.length; i++)
            if (values[i] < 0)
                count++;

        // Sets counts to the number of negative elements in values array
        count = 0;
        for (int i = 0; i < values.length; i += 1) {
            if (values[i] < 0) {
                count = count + 1;
            }
        }

        int s = 0;
        int b = 10;
        int c = 0;
        int a = 1;
        int d = 5;
        int f = 5;
        int e = 5;
        while (s > b) {
            if (c % a == b + (d--)) {
                b = e + c;
                a *= -1;
            } else {
                while ((c == e) || (a > f)) {
                    b = f % a;
                    a++;
                    c = a* b;
                }
            }
        }

        long start = System.currentTimeMillis();
        myMethod();
        long end = System.currentTimeMillis();
        long myMethodExecutionTime = end -start;

    }

    private static void myMethod() {
    }
}
