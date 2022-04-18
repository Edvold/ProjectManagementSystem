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

    public void createActivity(String name, LocalDateTime startDate, LocalDateTime endDate, double budgetedTime) throws InvalidDateError, DuplicateNameError, DateNotInitializedError, IllegalArgumentException {
        if (this.startDate == null) throw new DateNotInitializedError("Cannot create activity before the start date of the project is set"); // IMPLEMENT IN TEST
        try {
            if (hasActivityWithName(name)) throw new DuplicateNameError("Name is already in use");
            activities.add(new Activity(name, startDate, endDate, this, budgetedTime));
        } catch (InvalidDateError | IllegalArgumentException e) {
            // Maybe do some stuff here - if not then delete
            throw e;
        }
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

    public void setStartDate(LocalDateTime startDate) throws InvalidDateError {
        if((this.startDate != null) && (startDate.isAfter(this.startDate))){
            this.startDate = startDate;
        }
        else if ((this.startDate == null) && (startDate.isAfter(LocalDateTime.now()))){
            this.startDate = startDate;
        }
        else {
            throw new InvalidDateError("Invalid date");
        }

    }

    public void setProjectLeader(Employee projectLeader) {
        // Maybe only the project leader can change the role?
        this.projectLeader = projectLeader;
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

}
