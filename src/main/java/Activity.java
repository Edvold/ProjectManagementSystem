import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Activity {

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Activity(String name, LocalDateTime startDate, LocalDateTime endDate) throws InvalidDateError {
        this.name = name;
        if (areDatesInvalid(startDate, endDate)) throw new InvalidDateError("Invalid date");
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private boolean areDatesInvalid(LocalDateTime startDate, LocalDateTime endDate) {
        // Start date is after the end date
        if(startDate.isAfter(endDate)) return true;
        // Both start date and end date are before today. Truncating to only compare dates and not time of the day as well
        if(startDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0) return true;
        return false;
    }

    public String getName() {
        return name;
    }


}
