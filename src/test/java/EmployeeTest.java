import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// Written by Mathias Edvold s214973
public class EmployeeTest {

    private ErrorMessageHolder errorMessageHolder;
    private double hours = 0;
    private double pastHours = 0;
    private Employee actor;
    private Employee projectLeader;
    private Activity activity;

    // Written by Mathias Edvold s214973
    public EmployeeTest(ErrorMessageHolder errorMessageHolder) throws DuplicateNameError, InvalidDateError, DateNotInitializedError, MissingRequiredPermissionError, EmployeeIsUnavailableError {
        this.errorMessageHolder = errorMessageHolder;
        actor = new Employee("Jeff");
        projectLeader = new Employee("Tristan");
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject(LocalDateTime.now(), "dummy");
        Project project = ProjectManager.getInstance().getProjectByName("dummy");
        project.setProjectLeader(projectLeader);
        ProjectManager.getInstance().getProjectByName("dummy")
                .createActivity("dummy", LocalDateTime.now(), LocalDateTime.now().plusDays(20),
                        20, projectLeader);
        activity = project.getActivityByName("dummy");
    }

    // Written by Mathias Edvold s214973
    @Given("The given hours are valid")
    public void the_given_hours_are_valid() {
        hours = 5;
        assertTrue(hours > 0);
    }

    // Written by Mathias Edvold s214973
    @Given("The employee works on the activity")
    public void the_employee_works_on_the_activity() throws MissingRequiredPermissionError {
        activity.addEmployee(actor, projectLeader);
        assertTrue(activity.isEmployeeWorkingOnActivity(actor));
    }
    // Written by Mathias Edvold s214973
    @When("The employee registers hours for the activity")
    public void the_employee_registers_hours_for_the_activity() {
        try {
            pastHours = actor.getHours(activity);
            actor.registerHours(activity, hours);
        } catch (IllegalArgumentException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    // Written by Mathias Edvold s214973
    @Then("The employee's hours are added to the old registered hours")
    public void the_employee_s_hours_are_added_to_the_old_registered_hours() {
        assertTrue(pastHours + hours == actor.getHours(activity));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The employee has registered hours for the activity")
    public void the_employee_has_registered_hours_for_the_activity() {
        actor.registerHours(activity, 15d);
    }
    // Written by Bjarke Bak Jensen s214957
    @When("The employee unregisters hours for the activity")
    public void the_employee_unregisters_hours_for_the_activity() {
        try {
            pastHours = actor.getHours(activity);
            actor.unregisterHours(activity, hours);
        } catch (IllegalArgumentException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    // Written by Bjarke Bak Jensen s214957
    @Then("The employee's hours are subtracted from the old registered hours")
    public void the_employee_s_hours_are_subtracted_from_the_old_registered_hours() {
        assertTrue(pastHours - hours == actor.getHours(activity));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The hours are invalid")
    public void the_hours_are_invalid() {
        hours = -5;
        assertTrue(hours <= 0);
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The employee doesn't work on the activity")
    public void the_employee_doesn_t_work_on_the_activity() {
        assertFalse(activity.isEmployeeWorkingOnActivity(actor));
    }
}
