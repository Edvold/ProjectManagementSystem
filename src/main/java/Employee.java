import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Employee {

    private String name;
    private HashMap<Project, Double> projectHours = new HashMap<>();
    private ArrayList<Activity> activities = new ArrayList<>();

    public Employee(String name) {
        this.name = name;
    }

    public boolean isAvailable(LocalDateTime startDate, LocalDateTime endDate) {
        Set<Project> projects = projectHours.keySet();
        for (Project project : projects) {
            if(!project.getProjectLeader().equals(this)) continue;

            if(isBetweenDates(project.getStartDate(), project.getLastEndDate(), startDate, endDate)) return false;
        }

        for (Activity activity : activities) {
            if (isBetweenDates(activity.getStartDate(), activity.getEndDate(), startDate, endDate)) return false;
        }

        return true;
    }

    private boolean isBetweenDates(LocalDateTime eventStartDate, LocalDateTime eventEndDate, LocalDateTime startDate, LocalDateTime endDate) {
        return (eventStartDate.isAfter(startDate) && eventStartDate.isBefore(endDate)) || (eventEndDate.isBefore(endDate) && eventStartDate.isAfter(startDate));
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public boolean isWorkingOnActivity(Activity activity) {
        return activities.contains(activity);
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
