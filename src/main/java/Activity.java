import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Activity {

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Project project;
    private double budgetedTime;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private final String ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR = "Only the project leader can change the data of an activity";
    private final String BUDGETED_TIME_NOT_POSITIVE_ERROR = "Budgeted time has to be bigger than 0";


    public Activity(String name, LocalDateTime startDate, LocalDateTime endDate, Project project, double budgetedTime) throws InvalidDateError, IllegalArgumentException {

        String errorMessage = getCorrectInvalidDateError(startDate, endDate, project.getStartDate());
        if (errorMessage != null) throw new InvalidDateError(errorMessage);
        if (budgetedTime <= 0) throw new IllegalArgumentException(BUDGETED_TIME_NOT_POSITIVE_ERROR);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
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
        if (startDate == null || endDate == null) return "There is missing information about the dates";
        if (isDayBeforeDate(endDate, startDate)) return "The end date cannot be before the start date"; // END DATE BEFORE START
        if (isDayBeforeDate(startDate)) return "The start date cannot be before today"; // START DATE BEFORE TODAY
        if (isDayBeforeDate(startDate, projectStartDate)) return "The start date cannot be before the start date of the project"; // START DATE BEFORE PROJECT START DATE
        return null;
    }

    public void changeDates(LocalDateTime startDate, LocalDateTime endDate, Employee actor) throws InvalidDateError, MissingRequiredPermissionError {
        //Checking if the employee is the project leader
        if (!actor.equals(project.getProjectLeader())) throw new MissingRequiredPermissionError(ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR);
        // Check if new date is okay
        String errorMessage = getCorrectInvalidDateError(startDate, endDate, project.getStartDate());
        if(errorMessage != null) throw new InvalidDateError(errorMessage);
        // No errors - can change dates
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setBudgetedTime(double budgetedTime, Employee actor) throws MissingRequiredPermissionError, IllegalArgumentException {
        if (!actor.equals(project.getProjectLeader())) throw new MissingRequiredPermissionError(ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR);
        if (budgetedTime <= 0) throw new IllegalArgumentException(BUDGETED_TIME_NOT_POSITIVE_ERROR);
        this.budgetedTime = budgetedTime;
    }

    public String getName() {
        return name;
    }

    public double getBudgetedTime() {
        return budgetedTime;
    }

    public LocalDateTime getStartDate() {return startDate;}

    public LocalDateTime getEndDate() {return endDate;}

    public void addEmployee(Employee employee, Employee actor) throws IllegalArgumentException, MissingRequiredPermissionError {
        // Maybe ensure that employee is part of project? But then maybe problem of helping coworkers
        if (!actor.equals(project.getProjectLeader())) throw new MissingRequiredPermissionError(ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR);
        if (isEmployeeWorkingOnActivity(employee)) throw new IllegalArgumentException("The employee is already a part of the activity");
        employeeList.add(employee);
        employee.addActivity(this);
    }

    public boolean isEmployeeWorkingOnActivity(Employee employee) {
        return employeeList.contains(employee);
    }

}