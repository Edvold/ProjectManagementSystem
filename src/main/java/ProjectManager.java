import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Written by Mathias Edvold s214973
public class ProjectManager {
    private List<Project> projectList = new ArrayList<>();
    private static ProjectManager instance;

    private HashMap<Integer, Integer> projectNumberMap = new HashMap<>();

    private ProjectManager(){}

    // Written by Mathias Edvold s214973
    public static ProjectManager getInstance() {
        if(instance == null){
            instance = new ProjectManager();
        }
        return instance;
    }

    // Written by Mathias Edvold s214973// Written by Mathias Edvold s214973
    public void createProject(LocalDateTime date, String projectName) throws InvalidDateError, DuplicateNameError {
        if(hasName(projectName)){
            throw new DuplicateNameError("Name is already in use");
        }

        projectList.add(new Project(date, projectName, computeProjectNumber(date.getYear())));

    }

    // Written by Mathias Edvold s214973
    public void createProject(String projectName) throws DuplicateNameError {
        if(hasName(projectName)){
            throw new DuplicateNameError("Name is already in use");
        }

        projectList.add(new Project(projectName));

    }

    // Written by Mathias Edvold s214973
    public String computeProjectNumber(int year) {
        if(projectNumberMap.containsKey(year)) {
            projectNumberMap.put(year,projectNumberMap.get(year)+1);
        } else {
            projectNumberMap.put(year, 1);
        }

        return leftPadding(projectNumberMap.get(year), 4);
    }

    // Written by Bjarke Bak Jensen s214957
    private boolean hasName(String name){
        return getProjectByName(name) != null;
    }

    // Written by Mathias Edvold s214973
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

    // Written by Bjarke Bak Jensen s214957
    public Project getProjectByName(String name){
        for(Project p : projectList){
            if(p.getProjectName().equals(name)){
                return p;
            }
        }
        return null;
    }

    // Written by Bjarke Bak Jensen s214957
    public void emptyList(){
        projectList.clear();
        projectNumberMap.clear();
    }

    // Written by Bjarke Bak Jensen s214957
    public void changeProjectStartDate(String projectName, LocalDateTime newDate) throws InvalidDateError {
        getProjectByName(projectName).setStartDate(newDate);
    }

    // Written by Bjarke Bak Jensen s214957
    public List<Project> getProjects() {
        return projectList;
    }
}
