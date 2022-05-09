import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Project {

    private String projectNumber;
    private LocalDateTime startDate;
    private LocalDateTime expectedEndDate;
    private String projectName;
    private Employee projectLeader;
    private ArrayList<Activity> activities = new ArrayList<>();

    public Project(LocalDateTime startDate, String projectName, String projectNumber) throws InvalidDateError {
        if (startDate == null) throw new InvalidDateError("There is missing information about the date");
        if(startDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0){
            // date is before creation date
            throw new InvalidDateError("Invalid date");
        }
        this.startDate = startDate;
        expectedEndDate = startDate.plusMonths(6);
        this.projectName=projectName;
        this.projectNumber = String.valueOf(startDate.getYear()).substring(2) + projectNumber;
    }

    public Project(String projectName){
        this.projectName=projectName;
    }

    public void createActivity(String name, LocalDateTime startDate, LocalDateTime endDate, double budgetedTime, Employee actor) throws InvalidDateError, DuplicateNameError, DateNotInitializedError, IllegalArgumentException, MissingRequiredPermissionError {
        /* 1 */ if(actor == null)
            /* 1a */ throw new IllegalArgumentException("This employee doesn't exist");
        /* 2 */ if (!actor.equals(projectLeader))
            /* 2a */ throw new MissingRequiredPermissionError("Only the project leader can create an activity");
        /* 3 */ if (this.startDate == null)
            /* 3a */ throw new DateNotInitializedError("Cannot create activity before the start date of the project is set");
        /* 4 */ if (hasActivityWithName(name))
            /* 4a */ throw new DuplicateNameError("Name is already in use");
        /* 5 */ if(endDate.isAfter(expectedEndDate))
            /* 5a */ expectedEndDate = endDate;
        /* 6 */ if(activities.isEmpty())
            /* 6a */ expectedEndDate = endDate;
        /* 7 */ activities.add(new Activity(name, startDate, endDate, this, budgetedTime));
    }

    public Activity getActivityByName(String name) {
        for (Activity a : activities) {
            if (a.getName().equals(name)) return a;
        }
        return null;
    }

    public String getReport(Employee actor) throws MissingRequiredPermissionError {
        if (actor == null) throw new IllegalArgumentException("This employee doesn't exist");
        if(actor.equals(this.projectLeader)){
            StringBuilder report = new StringBuilder("Start Date: " + startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" + "End Date: " + expectedEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n"
                    + "Activity: Hours worked / Budgeted time:");
            double totalWorked = 0;
            double totalBudgeted = 0;
            for (Activity ac : activities){
                double hoursWorked = 0;
                for(Employee emp : ac.getEmployeeList()){
                    hoursWorked += emp.getHours(ac);
                }
                totalWorked += hoursWorked;
                totalBudgeted += ac.getBudgetedTime();
                report.append("\n").append(ac.getName()).append(": ").append(hoursWorked).append("/").append(ac.getBudgetedTime());
            }
            report.append("\nTotal: ").append(totalWorked).append("/").append(totalBudgeted);
            return report.toString();
        }
        else{
            throw new MissingRequiredPermissionError("You are not the Leader of this Project");
        }
    }

    public boolean hasActivityWithName(String name) {
        return getActivityByName(name) != null;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public LocalDateTime getExpectedEndDate() {
        return expectedEndDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setStartDate(LocalDateTime startDate) throws InvalidDateError {
        if (!startDate.isAfter(LocalDateTime.now())) throw new InvalidDateError("The date cannot be before today");
        if (isDateAfterActivityStartDate(startDate)) throw new InvalidDateError("The date cannot be after an activity's start date");
        if (startDate.getYear() != this.startDate.getYear()) updateProjectNumber(startDate.getYear());
        this.startDate = startDate;

    }

    private boolean isDateAfterActivityStartDate(LocalDateTime date) {
        for (Activity activity : activities) {
            if (date.truncatedTo(ChronoUnit.DAYS).isAfter(activity.getStartDate().truncatedTo(ChronoUnit.DAYS))) return true;
        }
        return false;
    }

    public void setProjectLeader(Employee newProjectLeader) throws EmployeeIsUnavailableError {

        if(newProjectLeader == null) {
            throw new IllegalArgumentException("This employee doesn't exist");
        }

        if(newProjectLeader.isAvailable(startDate,expectedEndDate)){
            this.projectLeader = newProjectLeader;
        }
        else {
            throw new EmployeeIsUnavailableError("The new project leader is unavailable for this project");
        }
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    private void updateProjectNumber(int year) {
        projectNumber = String.valueOf(year).substring(2) + ProjectManager.getInstance().computeProjectNumber(year);
    }
}
