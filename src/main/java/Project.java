import java.util.Calendar;
import java.util.Date;
import java.time.LocalDateTime;

public class Project {

    private LocalDateTime startDate;
    private String projectName;

    public Project(LocalDateTime date, String projectName) throws InvalidDateError {
        if(date.isAfter(LocalDateTime.now()) || ((date.getDayOfMonth()==LocalDateTime.now().getDayOfMonth()) && (date.getMonth()==LocalDateTime.now().getMonth()) && (date.getYear()==LocalDateTime.now().getYear()))){
            startDate = date;
        }
        else {
            throw new InvalidDateError("Invalid date");
        }
        this.projectName=projectName;
    }

    public Project(String projectName){this.projectName=projectName;}

    public LocalDateTime getStartDate(){
        return this.startDate;
    }

    public String getProjectName() {
        return projectName;
    }
}
