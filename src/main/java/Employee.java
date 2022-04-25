import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Employee {

    private String name;
    private HashMap<Activity, Double> activityHours = new HashMap<>();

    private final String NOT_WORKING_ON_ACTIVITY_ERROR = "You don't work on this activity";
    private final String ILLEGAL_HOURS_AMOUNT_ERROR = "You need to input a positive amount of hours";

    public Employee(String name) {
        this.name = name;
    }

    public boolean isAvailable(LocalDateTime startDate, LocalDateTime endDate) {

        Set<Activity> activities = activityHours.keySet();

        for (Activity activity : activities) {
            Project project = activity.getProject();
            if (project.getProjectLeader().equals(this)) {
                if(isBetweenDates(project.getStartDate(), project.getLastEndDate(), startDate, endDate)) return false;
            }
            if (isBetweenDates(activity.getStartDate(), activity.getEndDate(), startDate, endDate)) return false;
        }

        return true;
    }

    private boolean isBetweenDates(LocalDateTime eventStartDate, LocalDateTime eventEndDate, LocalDateTime startDate, LocalDateTime endDate) {
        return (eventStartDate.isAfter(startDate) && eventStartDate.isBefore(endDate)) || (eventEndDate.isBefore(endDate) && eventStartDate.isAfter(startDate));
    }

    public void addActivity(Activity activity) throws IllegalArgumentException {
        if (isWorkingOnActivity(activity)) throw new IllegalArgumentException("The employee is already working on this project");
        activityHours.put(activity, 0d);
    }

    public boolean isWorkingOnActivity(Activity activity) {
        return activityHours.containsKey(activity);
    }

    public void registerHours(Activity activity, double hours) throws IllegalArgumentException {
        if (!isWorkingOnActivity(activity)) throw new IllegalArgumentException(NOT_WORKING_ON_ACTIVITY_ERROR);
        if (hours <= 0) throw new IllegalArgumentException(ILLEGAL_HOURS_AMOUNT_ERROR);
        activityHours.compute(activity, (k, v) -> v + hours);

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
        return name;
    }

}
