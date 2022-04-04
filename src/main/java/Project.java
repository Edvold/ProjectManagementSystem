import java.time.LocalDateTime;

public class Project {

    private final int projectNumber;
    private LocalDateTime startDate;
    private String projectName;

    public Project(LocalDateTime date, String projectName, Integer projectNumber) throws InvalidDateError {
        if(date.isAfter(LocalDateTime.now()) || ((date.getDayOfMonth()==LocalDateTime.now().getDayOfMonth()) && (date.getMonth()==LocalDateTime.now().getMonth()) && (date.getYear()==LocalDateTime.now().getYear()))){
            startDate = date;
        }
        else {
            throw new InvalidDateError("Invalid date");
        }
        this.projectName=projectName;
        this.projectNumber = projectNumber;

    }

    public Project(String projectName, Integer projectNumber){
        this.projectName=projectName;
        this.projectNumber = projectNumber;
    }

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public String getProjectName() {
        return projectName;
    }
}
