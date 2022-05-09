import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// Written by Bjarke Bak Jensen s214957
public class Activity {

    private final String NAME;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final Project PROJECT;
    private double budgetedTime;
    private ArrayList<Employee> employeeList = new ArrayList<>();
    private final String ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR = "Only the project leader can change the data of an activity";
    private final String BUDGETED_TIME_NOT_POSITIVE_ERROR = "Budgeted time has to be bigger than 0";

    // Written by Bjarke Bak Jensen s214957
    public Activity(String name, LocalDateTime startDate, LocalDateTime endDate, Project project, double budgetedTime) throws InvalidDateError, IllegalArgumentException {

        String errorMessage = getCorrectInvalidDateError(startDate, endDate, project.getStartDate());
        if (errorMessage != null) throw new InvalidDateError(errorMessage);
        if (budgetedTime <= 0) throw new IllegalArgumentException(BUDGETED_TIME_NOT_POSITIVE_ERROR);
        this.NAME = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.PROJECT = project;
        this.budgetedTime = budgetedTime;
    }

    // Written by Bjarke Bak Jensen s214957
    private static boolean isDayBeforeDate(LocalDateTime startDate) {
        // Both start date and end date are before today. Truncating to only compare dates and not time of the day as well
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    // Written by Bjarke Bak Jensen s214957
    private static boolean isDayBeforeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(endDate.truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    // Written by Mathias Edvold s214973
    private static String getCorrectInvalidDateError(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime projectStartDate) {
        if (startDate == null || endDate == null) return "There is missing information about the dates";
        if (isDayBeforeDate(endDate, startDate)) return "The end date cannot be before the start date"; // END DATE BEFORE START
        if (isDayBeforeDate(startDate)) return "The start date cannot be before today"; // START DATE BEFORE TODAY
        if (isDayBeforeDate(startDate, projectStartDate)) return "The start date cannot be before the start date of the project"; // START DATE BEFORE PROJECT START DATE
        return null;
    }

    // Written by Mathias Edvold s214973
    public void changeDates(LocalDateTime startDate, LocalDateTime endDate, Employee actor) throws InvalidDateError, MissingRequiredPermissionError {
        //Checking if the employee is the project leader
        if (!actor.equals(PROJECT.getProjectLeader())) throw new MissingRequiredPermissionError(ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR);
        // Check if new date is okay
        String errorMessage = getCorrectInvalidDateError(startDate, endDate, PROJECT.getStartDate());
        if(errorMessage != null) throw new InvalidDateError(errorMessage);
        // No errors - can change dates
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Written by Mathias Edvold s214973
    public void setBudgetedTime(double budgetedTime, Employee actor) throws MissingRequiredPermissionError, IllegalArgumentException {
        if (!actor.equals(PROJECT.getProjectLeader())) throw new MissingRequiredPermissionError(ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR);
        if (budgetedTime <= 0) throw new IllegalArgumentException(BUDGETED_TIME_NOT_POSITIVE_ERROR);
        this.budgetedTime = budgetedTime;
    }

    // Written by Bjarke Bak Jensen s214957
    public String getName() {
        return NAME;
    }

    // Written by Bjarke Bak Jensen s214957
    public double getBudgetedTime() {
        return budgetedTime;
    }

    // Written by Mathias Edvold s214973
    public LocalDateTime getStartDate() {return startDate;}

    // Written by Mathias Edvold s214973
    public LocalDateTime getEndDate() {return endDate;}

    // Written by Bjarke Bak Jensen s214957
    public void addEmployee(Employee employee, Employee actor) throws IllegalArgumentException, MissingRequiredPermissionError {
        /* 1 */ if (!actor.equals(PROJECT.getProjectLeader()))
            /* 1a */ throw new MissingRequiredPermissionError(ONLY_PROJECT_LEADER_HAS_PERMISSION_ERROR);
        /* 2 */ if (isEmployeeWorkingOnActivity(employee))
            /* 2a */ throw new IllegalArgumentException("The employee is already a part of the activity");
        /* pre condition */ assert actor.equals(PROJECT.getProjectLeader()) && !isEmployeeWorkingOnActivity(employee);
        int preEmployeeListSize = employeeList.size();
        /* 3 */ employeeList.add(employee);
        /* 4 */ employee.addActivity(this);
        /* post condition */ assert employeeList.size() == preEmployeeListSize+1 && employeeList.contains(employee) && employee.isWorkingOnActivity(this);
    }

    // Written by Bjarke Bak Jensen s214957
    public ArrayList<Employee> getAvailableEmployees() {
        return EmployeeManager.getInstance().getAvailableEmployees(startDate, endDate);
    }

    // Written by Mathias Edvold s214973
    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    // Written by Mathias Edvold s214973
    public boolean isEmployeeWorkingOnActivity(Employee employee) {
        return employeeList.contains(employee);
    }
}