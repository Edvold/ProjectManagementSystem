import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProjectManager {

    private String projectNumber = "001";

    private List<Project> projectList = new ArrayList<>();
    private static ProjectManager instance;

    private ProjectManager(){}

    public static ProjectManager getInstance() {
        if(instance == null){
            instance = new ProjectManager();
        }
        return instance;
    }

    public boolean hasProject(Project project){
        return projectList.contains(project);
    }

    public void createProject(LocalDateTime date, String projectName) throws InvalidDateError, DuplicateNameError {
        if(hasName(projectName)){
            throw new DuplicateNameError("Name is already in use");
        }

        if(projectNumber.equals(String.valueOf(Integer.MAX_VALUE))){
            projectNumber = "001";
        }
        projectList.add(new Project(date, projectName, projectNumber));
        Integer i = Integer.valueOf(projectNumber);
        i++;

        projectNumber = leftPadding(i, 3);
    }

    public void createProject(String projectName) throws DuplicateNameError {
        if(hasName(projectName)){
            throw new DuplicateNameError("Name is already in use");
        }
        if(projectNumber.equals(String.valueOf(Integer.MAX_VALUE))){
            projectNumber = "001";
        }
        projectList.add(new Project(projectName, projectNumber));
        Integer i = Integer.valueOf(projectNumber);
        i++;

        projectNumber = leftPadding(i, 3);

    }

    private boolean hasName(String name){
        if(getProjectByName(name) != null){
            return true;
        }
        return false;
    }

    private String leftPadding(int i, int stringLength) {
        String iString = String.valueOf(i);
        int amountOfZeroes = stringLength - iString.length();
        for (int j = 0; j < amountOfZeroes; j++)
        {
            iString = "0" + iString;
        }
        return  iString;
    }

    public Project getProjectByName(String name){
        for(Project p : projectList){
            if(p.getProjectName().equals(name)){
                return p;
            }
        }
        return null;
    }

    public void emptyList(){
        projectList.clear();
    }

    public void changeProjectStartDate(String projectName, LocalDateTime newDate) throws InvalidDateError {
        getProjectByName(projectName).setStartDate(newDate);
    }
}
