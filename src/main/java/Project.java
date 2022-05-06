import java.time.LocalDateTime;
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

    public Project(String projectName, String projectNumber){
        this.projectName=projectName;
    }

    public void createActivity(String name, LocalDateTime startDate, LocalDateTime endDate, double budgetedTime, Employee actor) throws InvalidDateError, DuplicateNameError, DateNotInitializedError, IllegalArgumentException, MissingRequiredPermissionError {
        if(actor == null) throw new IllegalArgumentException("This employee doesn't exist");
        if (!actor.equals(projectLeader)) throw new MissingRequiredPermissionError("Only the project leader can create an activity");
        if (this.startDate == null) throw new DateNotInitializedError("Cannot create activity before the start date of the project is set"); // IMPLEMENT IN TEST
        if (hasActivityWithName(name)) throw new DuplicateNameError("Name is already in use");
        if(endDate.isAfter(expectedEndDate)) expectedEndDate = endDate;
        if(activities.isEmpty()) expectedEndDate = endDate;
        activities.add(new Activity(name, startDate, endDate, this, budgetedTime));
    }

    public Activity getActivityByName(String name) {
        for (Activity a : activities) {
            if (a.getName().equals(name)) return a;
        }
        return null;
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
        if (!startDate.isAfter(LocalDateTime.now())) throw new InvalidDateError("Invalid date");
        if (startDate.getYear() != this.startDate.getYear()) updateProjectNumber(startDate.getYear());
        this.startDate = startDate;

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
        // Maybe only the project leader can change the role?
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    private void updateProjectNumber(int year) {
        projectNumber = String.valueOf(year).substring(2) + ProjectManager.getInstance().computeProjectNumber(startDate.getYear());
    }
}
