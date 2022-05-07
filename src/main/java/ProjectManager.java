import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProjectManager {
    private List<Project> projectList = new ArrayList<>();
    private static ProjectManager instance;

    private HashMap<Integer, Integer> projectNumberMap = new HashMap<>();

    private ProjectManager(){}

    public static ProjectManager getInstance() {
        if(instance == null){
            instance = new ProjectManager();
        }
        return instance;
    }

    public void createProject(LocalDateTime date, String projectName) throws InvalidDateError, DuplicateNameError {
        if(hasName(projectName)){
            throw new DuplicateNameError("Name is already in use");
        }

        projectList.add(new Project(date, projectName, computeProjectNumber(date.getYear())));

    }

    public void createProject(String projectName) throws DuplicateNameError {
        if(hasName(projectName)){
            throw new DuplicateNameError("Name is already in use");
        }

        projectList.add(new Project(projectName));

    }

    public String computeProjectNumber(int year) {
        if(projectNumberMap.containsKey(year)) {
            projectNumberMap.put(year,projectNumberMap.get(year)+1);
        } else {
            projectNumberMap.put(year, 1);
        }

        return leftPadding(projectNumberMap.get(year), 4);
    }

    private boolean hasName(String name){
        return getProjectByName(name) != null;
    }

    private String leftPadding(int i, int stringLength) {
        // Inserts zeros into the project number such that all project numbers are of equal length
        StringBuilder paddedString = new StringBuilder(String.valueOf(i));
        int amountOfZeroes = stringLength - paddedString.length();
        for (int j = 0; j < amountOfZeroes; j++)
        {
            paddedString.insert(0, "0");
        }
        return paddedString.toString();
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
        projectNumberMap.clear();
    }

    public void changeProjectStartDate(String projectName, LocalDateTime newDate) throws InvalidDateError {
        getProjectByName(projectName).setStartDate(newDate);
    }

    public List<Project> getProjects() {
        return projectList;
    }
}
