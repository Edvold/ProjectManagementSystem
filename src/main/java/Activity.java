import io.cucumber.java.bs.I;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Activity {

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime projectStartDate;
    private double budgetedTime;


    public Activity(String name, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime projectStartDate, double budgetedTime) throws InvalidDateError, TimeNotSetError {

        String errorMessage = getCorrectInvalidDateError(startDate, endDate, projectStartDate);
        if (errorMessage != null) throw new InvalidDateError(errorMessage);
        if (budgetedTime <= 0) throw new TimeNotSetError("Budgeted time has to be bigger than 0");
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectStartDate = projectStartDate;
        this.budgetedTime = budgetedTime;
    }

    private static boolean isDayBeforeDate(LocalDateTime startDate) {
        // Both start date and end date are before today. Truncating to only compare dates and not time of the day as well
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    private static boolean isDayBeforeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(endDate.truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    private static String getCorrectInvalidDateError(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime projectStartDate) {
        if (isDayBeforeDate(endDate, startDate)) return "The end date cannot be before the start date"; // END DATE BEFORE START
        if (isDayBeforeDate(startDate)) return "The start date cannot be before today"; // START DATE BEFORE TODAY
        if (isDayBeforeDate(startDate, projectStartDate)) return "The start date cannot be before the start date of the project"; // START DATE BEFORE PROJECT START DATE
        return null;
    }

    public void setStartDate(LocalDateTime startDate) throws InvalidDateError {
        String errorMessage = getCorrectInvalidDateError(startDate, endDate, projectStartDate);
        if(errorMessage != null) throw new InvalidDateError(errorMessage);
        this.startDate = startDate; // No errors - can change start date
    }

    public void setEndDate(LocalDateTime endDate) throws InvalidDateError {
        if (isDayBeforeDate(endDate, startDate)) {
            throw new InvalidDateError("The end date cannot be before the start date");
        }
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }


}