
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

// Written by Mathias Edvold s214973
public class RegisterHoursWhitebox {
    private Project project;
    private Employee employee;
    private Activity activity;
    private Employee projectLeader;
    private LocalDateTime startDate;
    private ErrorMessageHolder errorMessageHolder = new ErrorMessageHolder();


    // Written by Mathias Edvold s214973
    @Test
    public void testDataSetA() throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError, DateNotInitializedError, MissingRequiredPermissionError {
        ProjectManager.getInstance().emptyList();
        startDate = LocalDateTime.now().plusDays(1);
        ProjectManager.getInstance().createProject(startDate,"project1");
        project = ProjectManager.getInstance().getProjectByName("project1");
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        project.setProjectLeader(projectLeader);
        project.createActivity("activity1",startDate,startDate.plusDays(20),20,projectLeader);
        activity = project.getActivityByName("activity1");
        employee = EmployeeManager.getInstance().getEmployeeByName("ebi");
        try{
            employee.registerHours(activity,10);
        }
        catch (IllegalArgumentException e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(errorMessageHolder.getErrorMessage().equals("You don't work on this activity"));
    }

    // Written by Mathias Edvold s214973
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
        activity.addEmployee(employee,projectLeader);

        try{
            employee.registerHours(activity,-1);
        }
        catch (IllegalArgumentException e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(errorMessageHolder.getErrorMessage().equals("You need to input a positive amount of hours"));
    }

    // Written by Mathias Edvold s214973
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
        activity.addEmployee(employee,projectLeader);
        employee.registerHours(activity,10);
        assertTrue(employee.getHours(activity) == 10.0);
    }
}
