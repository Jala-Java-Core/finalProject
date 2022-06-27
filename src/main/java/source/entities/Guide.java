package source.entities;

import java.time.LocalDate;
import java.util.List;

public class Guide {
    // 1 month, 1 week, other
    private final LocalDate startDate;
    private final LocalDate endDate;

    private List<Schedule> schedules;

    public Guide(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // edit, delete, recurrence and repetition
}
