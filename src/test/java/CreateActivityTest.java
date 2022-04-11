import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class CreateActivityTest {

    private LocalDateTime activityStartDate;
    private LocalDateTime activityEndDate;
    private String activityName = "dummyActivity";
    private Project project;
    private ErrorMessageHolder errorMessageHolder;

    public CreateActivityTest(ErrorMessageHolder errorMessageHolder) throws DuplicateNameError {
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject("Dummy");
        project = ProjectManager.getInstance().getProjectByName("Dummy");
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("The date is valid")
    public void the_date_is_valid() {
        project.emptyList();
        activityStartDate = LocalDateTime.now();
        activityEndDate = LocalDateTime.now().plusDays(20);
        assertTrue(isDateValid(activityStartDate));
        assertTrue(isDateValid(activityEndDate));
    }
    @When("The employee creates an activity")
    public void the_employee_creates_an_activity() throws InvalidDateError {
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate);
        } catch (InvalidDateError | DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    @Then("The activity is added to the project")
    public void the_activity_is_added_to_the_project() {
        assertNotNull(project.getActivityByName(activityName));
    }

    private boolean isDateValid(LocalDateTime activityDate) {
        if (activityDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0) return false;
        return true;
    }

    @Given("The start date is invalid")
    public void the_start_date_is_invalid() {
        project.emptyList();
        activityStartDate = LocalDateTime.now().minusDays(5);
        activityEndDate = LocalDateTime.now().plusDays(5);
        assertFalse(isDateValid(activityStartDate));
        assertTrue(isDateValid(activityEndDate));
    }
    @Given("The end date is invalid")
    public void the_end_date_is_invalid() {
        project.emptyList();
        activityStartDate = LocalDateTime.now().plusDays(5);
        activityEndDate = LocalDateTime.now().plusDays(3);
        // The start date is after the end date
        assertTrue(activityStartDate.isAfter(activityEndDate));
    }

    @Given("Another activity in the project exists with the same name")
    public void another_activity_in_the_project_exists_with_the_same_name() throws InvalidDateError, DuplicateNameError {
        project.emptyList();
        activityStartDate = LocalDateTime.now();
        activityEndDate = LocalDateTime.now().plusDays(20);
        project.createActivity(activityName, activityStartDate, activityEndDate);
        assertTrue(project.hasActivtyWithName(activityName));
    }
    @When("An employee creates an activity with the same name")
    public void an_employee_creates_an_activity_with_the_same_name() throws InvalidDateError, DuplicateNameError {
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate);
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
}
