import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;

public class Employee {

    private String name;
    private HashMap<Project, Double> projectHours = new HashMap<>();

    public Employee(String name) {
        this.name = name;
    }

    public boolean isAvailable(LocalDateTime startDate, LocalDateTime endDate) {
        // NOT IMPLEMENTED
        return false;
    }

    public boolean isWorkingOnActivity(Activity activity) {
        return (getProjectByActivity(activity) != null);
    }

    public Project getProjectByActivity(Activity activity) {
        Set<Project> projectSet = projectHours.keySet();
        for (Project project : projectSet) {
            if (project.hasActivity(activity)) {
                return project;
            }
        }
        return null;
    }

    public void registerHours(Activity activity, double hours) {
        Project project = getProjectByActivity(activity);
        projectHours.computeIfPresent(project, (k, v) -> v + hours);

    }

    public String getName() {
        return name;
    }
}
