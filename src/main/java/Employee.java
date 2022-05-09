import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Set;

// Written by Bjarke Bak Jensen s214957
public class Employee {

    private final String NAME;
    private HashMap<Activity, Double> activityHours = new HashMap<>();

    private final String NOT_WORKING_ON_ACTIVITY_ERROR = "You don't work on this activity";
    private final String ILLEGAL_HOURS_AMOUNT_ERROR = "You need to input a positive amount of hours";

    // Written by Bjarke Bak Jensen s214957
    public Employee(String name) {
        this.NAME = name;
    }

    // Written by Bjarke Bak Jensen s214957
    public boolean isAvailable(LocalDateTime startDate, LocalDateTime endDate) {
        List<Project> projects = ProjectManager.getInstance().getProjects();
        for (Project project : projects) {
            // Check if the employee is the project leader on a project with overlapping dates
            if (project.getProjectLeader() == null)
                continue;
            if (!project.getProjectLeader().equals(this))
                continue;
            if (isBetweenDates(project.getStartDate(), project.getExpectedEndDate(), startDate, endDate))
                return false;
        }
        Set<Activity> activities = activityHours.keySet();

        for (Activity activity : activities) {
            if (isBetweenDates(activity.getStartDate(), activity.getEndDate(), startDate, endDate)) return false;
        }
        return true;
    }

    // Written by Mathias Edvold s214973
    private boolean isBetweenDates(LocalDateTime eventStartDate, LocalDateTime eventEndDate, LocalDateTime startDate, LocalDateTime endDate) {
        return (!eventStartDate.isBefore(startDate) && !eventStartDate.isAfter(endDate)) || (!eventEndDate.isAfter(endDate) && !eventStartDate.isBefore(startDate));
    }

    // Written by Mathias Edvold s214973
    public void addActivity(Activity activity) throws IllegalArgumentException {
        if (isWorkingOnActivity(activity)) throw new IllegalArgumentException("The employee is already working on this project");
        activityHours.put(activity, 0d);
    }

    // Written by Mathias Edvold s214973
    public boolean isWorkingOnActivity(Activity activity) {
        return activityHours.containsKey(activity);
    }

    // Written by Mathias Edvold 214973
    public void registerHours(Activity activity, double hours) throws IllegalArgumentException {
        /* 1 */ if (!isWorkingOnActivity(activity))
            /* 1a */ throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        /* 2 */ if (hours <= 0)
            /* 2a */ throw new IllegalArgumentException(ILLEGAL_HOURS_AMOUNT_ERROR);
        /* pre condition */ assert isWorkingOnActivity(activity) && hours > 0;
        double preHours = activityHours.get(activity);
        /* 3 */ activityHours.compute(activity, (k, v) -> v + hours);
        /* post condition */ assert preHours + hours == activityHours.get(activity);
    }

    // Written by Bjarke Bak Jensen s214957
    public void unregisterHours(Activity activity, double hours) throws IllegalArgumentException {
        if (!isWorkingOnActivity(activity)) throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        if (hours <= 0) throw new IllegalArgumentException(ILLEGAL_HOURS_AMOUNT_ERROR);
        if (activityHours.get(activity) < hours) activityHours.put(activity, 0d);
        else activityHours.compute(activity, (k, v) -> v - hours);
    }

    // Written by Mathias Edvold s214973
    public double getHours(Activity activity) {
        if (!isWorkingOnActivity(activity)) throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        return activityHours.get(activity);
    }

    // Written by Mathias Edvold s214973
    public String getName() {
        return NAME;
    }

}
