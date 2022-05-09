import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class AddEmployeeWhitebox {
    private Project project;
    private Employee employee;
    private Employee actor;
    private Activity activity;
    private Employee projectLeader;
    private LocalDateTime startDate;
    private ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();

    @Test
    public void testDataSetA() throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError, DateNotInitializedError, MissingRequiredPermissionError {
        startDate = LocalDateTime.now().plusDays(1);
        ProjectManager.getInstance().createProject(startDate,"project1");
        project = ProjectManager.getInstance().getProjectByName("project1");
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        project.setProjectLeader(projectLeader);
        project.createActivity("activity1",startDate,startDate.plusDays(20),20,projectLeader);
        activity = project.getActivityByName("activity1");
        employee = EmployeeManager.getInstance().getEmployeeByName("ebi");
        actor = EmployeeManager.getInstance().getEmployeeByName("kaha");
        try {
            activity.addEmployee(employee,actor);
        }
        catch (MissingRequiredPermissionError e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(errorMessageHolder.getErrorMessage().equals("Only the project leader can change the data of an activity"));
    }

    @Test
    public void testDataSetB() throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError, DateNotInitializedError, MissingRequiredPermissionError {
        ProjectManager.getInstance().emptyList();
        startDate = LocalDateTime.now().plusDays(1);
        ProjectManager.getInstance().createProject(startDate,"project1");
        project = ProjectManager.getInstance().getProjectByName("project1");
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        project.setProjectLeader(projectLeader);
        project.createActivity("activity1",startDate,startDate.plusDays(20),20,projectLeader);
        activity = project.getActivityByName("activity1");
        employee = EmployeeManager.getInstance().getEmployeeByName("ebi");
        actor = EmployeeManager.getInstance().getEmployeeByName("done");
        activity.addEmployee(employee,actor);
        try{
            activity.addEmployee(employee,actor);
        }
        catch (IllegalArgumentException e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(errorMessageHolder.getErrorMessage().equals("The employee is already a part of the activity"));
    }

    @Test
    public void testDataSetC() throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError, DateNotInitializedError, MissingRequiredPermissionError {
        ProjectManager.getInstance().emptyList();
        startDate = LocalDateTime.now().plusDays(1);
        ProjectManager.getInstance().createProject(startDate,"project1");
        project = ProjectManager.getInstance().getProjectByName("project1");
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        project.setProjectLeader(projectLeader);
        project.createActivity("activity1",startDate,startDate.plusDays(20),20,projectLeader);
        activity = project.getActivityByName("activity1");
        employee = EmployeeManager.getInstance().getEmployeeByName("ebi");
        actor = EmployeeManager.getInstance().getEmployeeByName("done");
        activity.addEmployee(employee,actor);
        assertTrue(activity.getEmployeeList().contains(employee));
        assertTrue(employee.isWorkingOnActivity(activity));
    }
}
