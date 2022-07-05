import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class EntertainmentTest {

    @Test
    public void getPreview() {
        Content movie = new Content(ContentType.STATIC, "02:00", "Movie City", "Classic movies");
        Content sports = new Content(ContentType.LIVE, "01:00", "ESPN", "Soccer news");
        Content news = new Content(ContentType.LIVE, "01:00", "BBC", "Europa news");

        Schedule schedule = new Schedule();

        schedule.associateContent(movie, "21:00");
        schedule.associateContent(sports, "19:00");
        schedule.associateContent(news, "20:00");

        Guide guide = new Guide(LocalDate.of(2022, 07, 11), LocalDate.of(2022, 07, 17));

        ArrayList<LocalDate> datesForSchedule = new ArrayList<LocalDate>();
        datesForSchedule.add(LocalDate.of(2022, 07, 11));
        datesForSchedule.add(LocalDate.of(2022, 07, 13));
        datesForSchedule.add(LocalDate.of(2022, 07, 15));

        guide.addScheduleInMultipleDates("Normal Schedule", schedule, datesForSchedule);

        Channel tvPublica = new Channel("TV Publica");
        tvPublica.addGuide(guide);

        Entertainment entertainment = new Entertainment();
        entertainment.addChannel(tvPublica);

        String preview = entertainment.getChannel("TV Publica")
                .getGuide()
                .getSchedule("Normal Schedule", LocalDate.of(2022, 07, 13))
                .channelPreview("20:00");
        System.out.println(preview);
        Assert.assertEquals("Title: BBC Duration: 01:00 Previous: ESPN(19:00 - 20:00) Next: Movie City (21:00 - 23:00) Summary: Europa news", preview);
    }
}
