import java.util.HashMap;
import java.util.Map;

public class HourHelp {

    private Map<String, Integer> hours = new HashMap<String, Integer>();
    private Map<Integer, String> hoursInt = new HashMap<Integer, String>();

    private static HourHelp hourHelp = null;

    private HourHelp () {
        hours.put("00:00", 0);
        hours.put("00:30", 50);
        hours.put("01:00", 100);
        hours.put("01:30", 150);
        hours.put("02:00", 200);
        hours.put("02:30", 250);
        hours.put("03:00", 300);
        hours.put("03:30", 350);
        hours.put("04:00", 400);
        hours.put("04:30", 450);
        hours.put("05:00", 500);
        hours.put("05:30", 550);
        hours.put("06:00", 600);
        hours.put("06:30", 650);
        hours.put("07:00", 700);
        hours.put("07:30", 750);
        hours.put("08:00", 800);
        hours.put("08:30", 850);
        hours.put("09:00", 900);
        hours.put("09:30", 950);
        hours.put("10:00", 1000);
        hours.put("10:30", 1050);
        hours.put("11:00", 1100);
        hours.put("11:30", 1150);
        hours.put("12:00", 1200);
        hours.put("12:30", 1250);
        hours.put("13:00", 1300);
        hours.put("13:30", 1350);
        hours.put("14:00", 1400);
        hours.put("14:30", 1450);
        hours.put("15:00", 1500);
        hours.put("15:30", 1550);
        hours.put("16:00", 1600);
        hours.put("16:30", 1650);
        hours.put("17:00", 1700);
        hours.put("17:30", 1750);
        hours.put("18:00", 1800);
        hours.put("18:30", 1850);
        hours.put("19:00", 1900);
        hours.put("19:30", 1950);
        hours.put("20:00", 2000);
        hours.put("20:30", 2050);
        hours.put("21:00", 2100);
        hours.put("21:30", 2150);
        hours.put("22:00", 2200);
        hours.put("22:30", 2250);
        hours.put("23:00", 2300);
        hours.put("23:30", 2350);

        hoursInt.put(0, "00:00");
        hoursInt.put(50, "00:30");
        hoursInt.put(100, "01:00");
        hoursInt.put(150, "01:30");
        hoursInt.put(200, "02:00");
        hoursInt.put(250, "02:30");
        hoursInt.put(300, "03:00");
        hoursInt.put(350, "03:30");
        hoursInt.put(400, "04:00");
        hoursInt.put(450, "04:30");
        hoursInt.put(500, "05:00");
        hoursInt.put(550, "05:30");
        hoursInt.put(600, "06:00");
        hoursInt.put(650, "06:30");
        hoursInt.put(700, "07:00");
        hoursInt.put(750, "07:30");
        hoursInt.put(800, "08:00");
        hoursInt.put(850, "08:30");
        hoursInt.put(900, "09:00");
        hoursInt.put(950, "09:30");
        hoursInt.put(1000, "10:00");
        hoursInt.put(1050, "10:30");
        hoursInt.put(1100, "11:00");
        hoursInt.put(1150, "11:30");
        hoursInt.put(1200, "12:00");
        hoursInt.put(1250, "12:30");
        hoursInt.put(1300, "13:00");
        hoursInt.put(1350, "13:30");
        hoursInt.put(1400, "14:00");
        hoursInt.put(1450, "14:30");
        hoursInt.put(1500, "15:00");
        hoursInt.put(1550, "15:30");
        hoursInt.put(1600, "16:00");
        hoursInt.put(1650, "16:30");
        hoursInt.put(1700, "17:00");
        hoursInt.put(1750, "17:30");
        hoursInt.put(1800, "18:00");
        hoursInt.put(1850, "18:30");
        hoursInt.put(1900, "19:00");
        hoursInt.put(1950, "19:30");
        hoursInt.put(2000, "20:00");
        hoursInt.put(2050, "20:30");
        hoursInt.put(2100, "21:00");
        hoursInt.put(2150, "21:30");
        hoursInt.put(2200, "22:00");
        hoursInt.put(2250, "22:30");
        hoursInt.put(2300, "23:00");
        hoursInt.put(2350, "23:30");
    }

    public static HourHelp getHourHelp() {
        if(hourHelp == null) {
            hourHelp = new HourHelp();
        }
        return hourHelp;
    }

    public String calculateEndHour(String hourStart, String duration) {

        int endHour = hours.get(hourStart) + hours.get(duration);

        if (endHour > 2400) {
            return "24:00";
        }

        return hoursInt.get(endHour);
    }

    public String calculateDuration(String hourStart, String hourEnd) {
        int duration = hours.get(hourEnd) - hours.get(hourStart);
        return hoursInt.get(duration);
    }

    public boolean isThereCollisionBetweenTwoTimeSlots(TimeSlot tsToIn, TimeSlot tsToCompare) {
        int tsToIn_StartTimeValue = hours.get(tsToIn.getTimeStart());
        int tsToIn_EndTimeValue = hours.get(tsToIn.getTimeEnd());

        if (tsToIn_EndTimeValue > hours.get(tsToCompare.getTimeStart())
                && tsToIn_StartTimeValue < hours.get(tsToCompare.getTimeEnd())) {
            return true;
        }
        return false;
    }

    public boolean isThereCollisionBetweenTwoTimeSlots(String tsToIn_StartTime, String tsToIn_EndTime, TimeSlot tsToCompare) {
        int tsToIn_StartTimeValue = hours.get(tsToIn_StartTime);
        int tsToIn_EndTimeValue = hours.get(tsToIn_EndTime);

        if (tsToIn_EndTimeValue > hours.get(tsToCompare.getTimeStart())
                && tsToIn_StartTimeValue < hours.get(tsToCompare.getTimeEnd())) {
            return true;
        }
        return false;
    }

    public boolean isThereCollisionInTimeSlots(TimeSlot ts,HashMap<String, TimeSlot> timeSlots) {
        for(TimeSlot tsToCompare : timeSlots.values()) {
            if (isThereCollisionBetweenTwoTimeSlots(ts, tsToCompare)) {
                return true;
            }
        }
        return false;
    }

    public boolean isThereCollisionInTimeSlots(String tsToIn_StartTime, String tsToIn_EndTime,HashMap<String, TimeSlot> timeSlots) {
        for(TimeSlot tsToCompare : timeSlots.values()) {
            if (isThereCollisionBetweenTwoTimeSlots(tsToIn_StartTime, tsToIn_EndTime, tsToCompare)) {
                System.out.println("There is a collision with TimeSlot " + tsToCompare.getContentTitle()
                        + " from: " + tsToCompare.getTimeStart()
                        + "to: " + tsToCompare.getTimeEnd());
                return true;
            }
        }
        return false;
    }
}
