import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ActivitySteps {

    private LocalDateTime activityStartDate;
    private LocalDateTime activityEndDate;
    private String activityName = "dummyActivity";
    private Project project;
    private ErrorMessageHolder errorMessageHolder;
    private double budgetedTime;
    private Employee projectLeader;
    private Employee actor;
    private Employee extraEmployee;

    public ActivitySteps(ErrorMessageHolder errorMessageHolder) {
        this.errorMessageHolder = errorMessageHolder;
    }

    private void createProject(int daysInTheFuture) throws DuplicateNameError, InvalidDateError {
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject(LocalDateTime.now().plusDays(daysInTheFuture), "Dummy");
        project = ProjectManager.getInstance().getProjectByName("Dummy");
    }

    @Given("The project has a start date")
    public void the_project_has_a_start_date() throws DuplicateNameError, InvalidDateError{
        createProject(0);
        assertNotNull(project.getStartDate());
    }

    @Given("The dates are valid for activity")
    public void the_dates_are_valid_for_activity() {
        project.emptyList();
        activityStartDate = LocalDateTime.now();
        activityEndDate = LocalDateTime.now().plusDays(20);
        assertTrue(areDatesValid(activityStartDate, activityEndDate, project.getStartDate()));
    }
    @Given("The start date is valid")
    public void the_start_date_is_valid() {
        if (project.getStartDate().isAfter(LocalDateTime.now())) activityStartDate = project.getStartDate().plusDays(1);
        else activityStartDate = LocalDateTime.now().plusDays(1);
        assertTrue(isDayBeforeDate(project.getStartDate(), activityStartDate));
        assertFalse(isDayBeforeDate(activityStartDate));
    }
    @Given("The end date is valid")
    public void the_end_date_is_valid() {
        activityEndDate = LocalDateTime.now().plusDays(20);
        assertFalse(isDayBeforeDate(activityEndDate));
        assertTrue(isDayBeforeDate(activityStartDate, activityEndDate));
    }
    @Given("The start date is after today")
    public void the_start_date_is_after_today() {
        activityStartDate = LocalDateTime.now().plusDays(1);
        assertFalse(isDayBeforeDate(activityStartDate));
    }
    @Given("The start date is before the project start date")
    public void the_start_date_is_before_the_project_start_date() {
        activityStartDate = project.getStartDate().minusDays(1);
        assertTrue(isDayBeforeDate(activityStartDate, project.getStartDate()));
    }
    @When("The employee creates an activity")
    public void the_employee_creates_an_activity() {
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    @Then("The activity is added to the project")
    public void the_activity_is_added_to_the_project() {
        assertNotNull(project.getActivityByName(activityName));
    }

    private boolean isDayBeforeDate(LocalDateTime startDate) {
        // Both start date and end date are before today. Truncating to only compare dates and not time of the day as well
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    private boolean isDayBeforeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(endDate.truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    private boolean areDatesValid(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime projectStartDate) {
        return !(isDayBeforeDate(endDate, startDate)  && isDayBeforeDate(startDate) && !isDayBeforeDate(startDate, projectStartDate));
    }
    @Given("The project has a start date a few days after today")
    public void the_project_has_a_start_date_a_few_days_after_today() throws DuplicateNameError, InvalidDateError {
        createProject(3);
        assertTrue(project.getStartDate().compareTo(LocalDateTime.now()) > 2); // More than two days difference
    }

    @Given("The start date is between today and project start date")
    public void the_start_date_is_between_today_and_project_start_date() {
        activityStartDate = LocalDateTime.now().plusDays(2);
        assertTrue(isDayBeforeDate(LocalDateTime.now(), activityStartDate));
        assertTrue(isDayBeforeDate(activityStartDate, project.getStartDate()));
    }

    @Given("The start date is before today")
    public void the_start_date_is_before_today() {
        project.emptyList();
        activityStartDate = LocalDateTime.now().minusDays(5);
        activityEndDate = LocalDateTime.now().plusDays(5);
        assertTrue(isDayBeforeDate(activityStartDate)); // start date is invalid
        assertFalse(isDayBeforeDate(activityEndDate)); // end date is not before today and therefore is valid
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
    public void another_activity_in_the_project_exists_with_the_same_name() throws InvalidDateError, DuplicateNameError, DateNotInitializedError, IllegalArgumentException {
        createProject(0);
        project.emptyList();
        activityStartDate = LocalDateTime.now();
        activityEndDate = LocalDateTime.now().plusDays(20);
        budgetedTime = 20;
        project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime);
        assertTrue(project.hasActivityWithName(activityName));
    }
    @When("An employee creates an activity with the same name")
    public void an_employee_creates_an_activity_with_the_same_name() throws InvalidDateError, DateNotInitializedError, IllegalArgumentException {
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime);
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("The project does not have a start date")
    public void the_project_does_not_have_a_start_date() throws DuplicateNameError {
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject("Dummy"); // Making a project with no start date
        project = ProjectManager.getInstance().getProjectByName("Dummy");
    }

    @Given("The budgeted time is valid")
    public void the_budgeted_time_is_valid() {
        budgetedTime = 20;
        assertTrue(budgetedTime > 0);
    }
    @Given("The budgeted time is invalid")
    public void the_budgeted_time_is_invalid() {
        budgetedTime = 0;
        assertFalse(budgetedTime > 0);
    }

    @Given("The project leader is the project leader for the given activity")
    public void the_project_leader_is_the_project_leader_for_the_given_activity() throws DuplicateNameError, InvalidDateError {
        // Actually needs to check if the current user is the project leader
        createProject(0);
        projectLeader = new Employee("Carl");
        actor = projectLeader;
        project.setProjectLeader(projectLeader);
        assertEquals(project.getProjectLeader(), actor);
    }
    @Given("There exists an activity")
    public void there_exists_an_activity() throws InvalidDateError, DuplicateNameError, DateNotInitializedError, IllegalArgumentException {
        project.emptyList();
        activityStartDate = LocalDateTime.now();
        activityEndDate = LocalDateTime.now().plusDays(20);
        budgetedTime = 20;
        project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime);
        assertTrue(project.hasActivityWithName(activityName));
    }
    @Given("The new start date is valid")
    public void the_new_start_date_is_valid() {
        activityStartDate = activityStartDate.plusDays(1);
        assertTrue(areDatesValid(activityStartDate, activityEndDate, project.getStartDate()));
    }
    @When("The project leader changes the start date")
    public void the_project_leader_changes_the_start_date() throws MissingRequiredPermissionError, InvalidDateError {
        Activity activity = project.getActivityByName(activityName);
        activity.changeDates(activityStartDate, activityEndDate, actor);

    }
    @Then("The activity's start date is changed")
    public void the_activity_s_start_date_is_changed() {
        assertEquals(activityStartDate, project.getActivityByName(activityName).getStartDate());
    }
    @Given("The new end date is valid")
    public void the_new_end_date_is_valid() {
        activityEndDate = activityEndDate.plusDays(1);
        assertTrue(areDatesValid(activityStartDate, activityEndDate, project.getStartDate()));
    }
    @When("The project leader changes the end date")
    public void the_project_leader_changes_the_end_date() throws MissingRequiredPermissionError, InvalidDateError {
        Activity activity = project.getActivityByName(activityName);
        activity.changeDates(activityStartDate, activityEndDate, actor);
    }
    @Then("The activity's end date is changed")
    public void the_activity_s_end_date_is_changed() {
        assertEquals(activityEndDate, project.getActivityByName(activityName).getEndDate());
    }
    @Given("The given time is valid")
    public void the_given_time_is_valid() {
        budgetedTime = 1;
        assertTrue(budgetedTime > 0);
    }
    @When("The project leader changes the budgeted time")
    public void the_project_leader_changes_the_budgeted_time() throws MissingRequiredPermissionError, IllegalArgumentException {
        project.getActivityByName(activityName).setBudgetedTime(budgetedTime, actor);
    }
    @Then("The budgeted time is changed")
    public void the_budgeted_time_is_changed() {
        Activity activity = project.getActivityByName(activityName);
        assertEquals(budgetedTime, activity.getBudgetedTime(), 0);
    }
    @Given("The employee is not a part of the activity")
    public void the_employee_is_not_a_part_of_the_activity() {
        extraEmployee = new Employee("Tim");
        assertFalse(project.getActivityByName(activityName).isEmployeeWorkingOnActivity(extraEmployee));
    }
    @Given("The employee is available")
    public void the_employee_is_available() {
        // Not implemented yet
    }
    @When("The project leader adds the employee to the activity")
    public void the_project_leader_adds_the_employee_to_the_activity() {
        project.getActivityByName(activityName).addEmployee(extraEmployee);
    }
    @Then("The employee is a part of the activity")
    public void the_employee_is_a_part_of_the_activity() {
        assertTrue(project.getActivityByName(activityName).isEmployeeWorkingOnActivity(extraEmployee));
    }
}
