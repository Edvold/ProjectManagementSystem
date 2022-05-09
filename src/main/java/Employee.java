import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

public class Employee {

    private final String NAME;
    private HashMap<Activity, Double> activityHours = new HashMap<>();

    private final String NOT_WORKING_ON_ACTIVITY_ERROR = "You don't work on this activity";
    private final String ILLEGAL_HOURS_AMOUNT_ERROR = "You need to input a positive amount of hours";

    public Employee(String name) {
        this.NAME = name;
    }

    public boolean isAvailable(LocalDateTime startDate, LocalDateTime endDate) {
       /* 1 */ List<Project> projects = ProjectManager.getInstance().getProjects();
        /* 2 */ for (Project project : projects) {
            // Check if the employee is the project leader on a project with overlapping dates
            /* 3 */ if (project.getProjectLeader() == null)
                /* 3a */ continue;
            /* 4 */ if (!project.getProjectLeader().equals(this))
                /* 4a */ continue;
            /* 5 */ if (isBetweenDates(project.getStartDate(), project.getExpectedEndDate(), startDate, endDate))
                /* 5a */ return false;
        }
        /* 6 */ Set<Activity> activities = activityHours.keySet();

        /* 7 */ for (Activity activity : activities) {
            /* 8 */ if (isBetweenDates(activity.getStartDate(), activity.getEndDate(), startDate, endDate)) return false;
        }
        /* 9 */ return true;
    }

    private boolean isBetweenDates(LocalDateTime eventStartDate, LocalDateTime eventEndDate, LocalDateTime startDate, LocalDateTime endDate) {
        return (!eventStartDate.isBefore(startDate) && !eventStartDate.isAfter(endDate)) || (!eventEndDate.isAfter(endDate) && !eventStartDate.isBefore(startDate));
    }

    public void addActivity(Activity activity) throws IllegalArgumentException {
        if (isWorkingOnActivity(activity)) throw new IllegalArgumentException("The employee is already working on this project");
        activityHours.put(activity, 0d);
    }

    public boolean isWorkingOnActivity(Activity activity) {
        return activityHours.containsKey(activity);
    }

    public void registerHours(Activity activity, double hours) throws IllegalArgumentException {
        /* 1 */ if (!isWorkingOnActivity(activity))
            /* 1a */ throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        /* 2 */ if (hours <= 0)
            /* 2a */ throw new IllegalArgumentException(ILLEGAL_HOURS_AMOUNT_ERROR);
        /* 3 */ activityHours.compute(activity, (k, v) -> v + hours);
    }

    public void unregisterHours(Activity activity, double hours) throws IllegalArgumentException {
        if (!isWorkingOnActivity(activity)) throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        if (hours <= 0) throw new IllegalArgumentException(ILLEGAL_HOURS_AMOUNT_ERROR);
        if (activityHours.get(activity) < hours) activityHours.put(activity, 0d);
        else activityHours.compute(activity, (k, v) -> v - hours);
    }

    public double getHours(Activity activity) {
        if (!isWorkingOnActivity(activity)) throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        return activityHours.get(activity);
    }

    public String getName() {
        return NAME;
    }

}
