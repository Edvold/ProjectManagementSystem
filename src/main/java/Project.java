import io.cucumber.java.sl.In;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Project {

    private final String PROJECT_NUMBER;
    private LocalDateTime startDate;
    private String projectName;
    private Employee projectLeader;
    private ArrayList<Activity> activities = new ArrayList<>();
    private ArrayList<Employee> employeeList = new ArrayList<>();

    public Project(LocalDateTime date, String projectName, String projectNumber) throws InvalidDateError {
        if (date == null) throw new InvalidDateError("There is missing information about the date");
        if(date.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0){
            // date is before creation date
            throw new InvalidDateError("Invalid date");
        }
        startDate = date;
        this.projectName=projectName;
        this.PROJECT_NUMBER = String.valueOf(date.getYear()).substring(2) + projectNumber;
    }

    public Project(String projectName, String projectNumber){
        LocalDateTime date = LocalDateTime.now();
        this.projectName=projectName;
        this.PROJECT_NUMBER = String.valueOf(date.getYear()).substring(2) + projectNumber;
    }

    public void createActivity(String name, LocalDateTime startDate, LocalDateTime endDate, double budgetedTime, Employee actor) throws InvalidDateError, DuplicateNameError, DateNotInitializedError, IllegalArgumentException, MissingRequiredPermissionError {
        if (!actor.equals(projectLeader)) throw new MissingRequiredPermissionError("Only the project leader can create an activity");
        if (this.startDate == null) throw new DateNotInitializedError("Cannot create activity before the start date of the project is set"); // IMPLEMENT IN TEST
        if (hasActivityWithName(name)) throw new DuplicateNameError("Name is already in use");
        activities.add(new Activity(name, startDate, endDate, this, budgetedTime));
    }

    public Activity getActivityByName(String name) {
        for (Activity a : activities) {
            if (a.getName().equals(name)) return a;
        }
        return null;
    }

    public boolean hasActivity(Activity activity) {
        return hasActivityWithName(activity.getName());
    }

    public boolean hasActivityWithName(String name) {
        return getActivityByName(name) != null;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getPROJECT_NUMBER() {
        return PROJECT_NUMBER;
    }

    public LocalDateTime getLastEndDate() {
        LocalDateTime lastDate = activities.get(0).getEndDate();

        for (int i = 1; i < activities.size(); i++) {
            LocalDateTime date = activities.get(i).getEndDate();
            if (lastDate.isBefore(date)) lastDate = date;
        }

        return lastDate;
    }

    public void setStartDate(LocalDateTime startDate) throws InvalidDateError {
        if (!startDate.isAfter(LocalDateTime.now())) throw new InvalidDateError("Invalid date");
        this.startDate = startDate;

    }

    public void setProjectLeader(Employee projectLeader) {
        // Maybe only the project leader can change the role?
        this.projectLeader = projectLeader;
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

}
