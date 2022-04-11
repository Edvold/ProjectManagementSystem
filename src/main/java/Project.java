import io.cucumber.java.bs.A;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Project {

    private final String PROJECT_NUMBER;
    private LocalDateTime startDate;
    private String projectName;
    private ArrayList<Activity> activities = new ArrayList<>();

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

    public void createActivity(String name, LocalDateTime startDate, LocalDateTime endDate) throws InvalidDateError, DuplicateNameError {
        try {
            if (hasActivtyWithName(name)) throw new DuplicateNameError("Name is already in use");
            activities.add(new Activity(name, startDate, endDate));
        } catch (InvalidDateError e) {
            throw e;
        }
    }

    public Activity getActivityByName(String name) {
        for (Activity a : activities) {
            if (a.getName().equals(name)) return a;
        }
        return null;
    }

    public boolean hasActivtyWithName(String name) {
        if(getActivityByName(name) != null) return true;
        return false;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void emptyList() {
        activities.clear();
    }
}
