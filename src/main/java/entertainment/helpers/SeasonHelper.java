package entertainment.helpers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.IntStream;

public class SeasonHelper {
    public static int getDaysOfMonth(LocalDate startDate) {
        int month = startDate.getMonthValue();
        if (month > 12 || month < 1) {
            throw new IllegalArgumentException("Month should be between 1 and 12");
        }

        int[] longMonths = {1, 3, 5, 7, 8, 10, 12};
        int[] shortMonths = {4, 6, 9, 11};

        int daysOfMonth;
        if (Arrays.binarySearch(longMonths, month) >= 0) {
            daysOfMonth = 31;
        } else if (Arrays.binarySearch(shortMonths, month) >= 0){
            daysOfMonth = 30;
        } else {
            if (startDate.isLeapYear()) {
                daysOfMonth = 29;
            } else {
                daysOfMonth = 28;
            }
        }

        if (startDate.getDayOfMonth() > 1) {
            daysOfMonth = daysOfMonth - startDate.getDayOfMonth() + 1;
        }
        return daysOfMonth;
    }

    public static int getDaysOfWeek(LocalDate date) {
        if (!date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            return 7 - date.getDayOfWeek().getValue() + 1;
        }

        return 7;
    }
}
