import java.time.LocalDateTime;

public class Project {

    private final String PROJECT_NUMBER;
    private LocalDateTime startDate;
    private String projectName;

    public Project(LocalDateTime date, String projectName, String projectNumber) throws InvalidDateError {
        if(date.isAfter(LocalDateTime.now()) || ((date.getDayOfMonth()==LocalDateTime.now().getDayOfMonth()) && (date.getMonth()==LocalDateTime.now().getMonth()) && (date.getYear()==LocalDateTime.now().getYear()))){
            startDate = date;
        }
        else {
            throw new InvalidDateError("Invalid date");
        }
        this.projectName=projectName;
        this.PROJECT_NUMBER = String.valueOf(date.getYear()).substring(2) + projectNumber;
    }

    public Project(String projectName, String projectNumber){
        LocalDateTime date = LocalDateTime.now();
        this.projectName=projectName;
        this.PROJECT_NUMBER = String.valueOf(date.getYear()).substring(2) + projectNumber;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public String getProjectName() {
        return projectName;
    }
}
