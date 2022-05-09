import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

// Written by Mathias Edvold s214973
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
    private ArrayList<Employee> availableEmployees = new ArrayList<>();

    // Written by Mathias Edvold s214973
    public ActivitySteps(ErrorMessageHolder errorMessageHolder) throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError {
        this.errorMessageHolder = errorMessageHolder;
        createProject(0);
    }

    // Written by Mathias Edvold s214973
    private void createProject(int daysInTheFuture) throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError {
        // for when just a basic project is needed
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject(LocalDateTime.now().plusDays(daysInTheFuture), "Dummy");
        project = ProjectManager.getInstance().getProjectByName("Dummy");
        if (projectLeader == null) projectLeader = new Employee("Carl");
        project.setProjectLeader(projectLeader);
        if (actor == null) actor = new Employee("James");
    }

    // Helper methods

    // Written by Mathias Edvold s214973
    private boolean isDayBeforeDate(LocalDateTime startDate) {
        // Both start date and end date are before today. Truncating to only compare dates and not time of the day as well
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    // Written by Mathias Edvold s214973
    private boolean isDayBeforeDate(LocalDateTime startDate, LocalDateTime endDate) {
        return startDate.truncatedTo(ChronoUnit.DAYS).compareTo(endDate.truncatedTo(ChronoUnit.DAYS)) < 0;
    }

    // Written by Mathias Edvold s214973
    private boolean areDatesValid(LocalDateTime startDate, LocalDateTime endDate, LocalDateTime projectStartDate) {
        return !(isDayBeforeDate(endDate, startDate)  && isDayBeforeDate(startDate) && !isDayBeforeDate(startDate, projectStartDate));
    }

    // Written by Bjarke Bak Jensen s214957
    @Given("The project has a start date")
    public void the_project_has_a_start_date(){
        assertNotNull(project.getStartDate());
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The employee is the project leader")
    public void the_employee_is_the_project_leader() {
        actor = projectLeader;
        assertEquals(actor, projectLeader);
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The dates are valid for activity")
    public void the_dates_are_valid_for_activity() {
        activityStartDate = LocalDateTime.now();
        activityEndDate = LocalDateTime.now().plusDays(20);
        assertTrue(areDatesValid(activityStartDate, activityEndDate, project.getStartDate()));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The start date is valid")
    public void the_start_date_is_valid() {
        if (project.getStartDate().isAfter(LocalDateTime.now())) activityStartDate = project.getStartDate().plusDays(1);
        else activityStartDate = LocalDateTime.now().plusDays(1);
        assertTrue(isDayBeforeDate(project.getStartDate(), activityStartDate));
        assertFalse(isDayBeforeDate(activityStartDate));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The end date is valid")
    public void the_end_date_is_valid() {
        activityEndDate = LocalDateTime.now().plusYears(2);
        assertFalse(isDayBeforeDate(activityEndDate));
        assertTrue(isDayBeforeDate(activityStartDate, activityEndDate));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The start date is after today")
    public void the_start_date_is_after_today() {
        activityStartDate = LocalDateTime.now().plusDays(1);
        assertFalse(isDayBeforeDate(activityStartDate));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The start date is before the project start date")
    public void the_start_date_is_before_the_project_start_date() {
        activityStartDate = project.getStartDate().minusDays(1);
        assertTrue(isDayBeforeDate(activityStartDate, project.getStartDate()));
    }
    // Written by Bjarke Bak Jensen s214957
    @When("The employee creates an activity")
    public void the_employee_creates_an_activity() {
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime, actor);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    // Written by Bjarke Bak Jensen s214957
    @Then("The activity is added to the project")
    public void the_activity_is_added_to_the_project() {
        assertNotNull(project.getActivityByName(activityName));
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The project has a start date a few days after today")
    public void the_project_has_a_start_date_a_few_days_after_today() throws DuplicateNameError, InvalidDateError, EmployeeIsUnavailableError {
        createProject(3);
        assertTrue(project.getStartDate().compareTo(LocalDateTime.now()) > 2); // More than two days difference
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The start date is between today and project start date")
    public void the_start_date_is_between_today_and_project_start_date() {
        activityStartDate = LocalDateTime.now().plusDays(2);
        assertTrue(isDayBeforeDate(LocalDateTime.now(), activityStartDate));
        assertTrue(isDayBeforeDate(activityStartDate, project.getStartDate()));
    }
    // Written by Mathias Edvold s214973
    @Given("The start date is before today")
    public void the_start_date_is_before_today() {
        activityStartDate = LocalDateTime.now().minusDays(5);
        activityEndDate = LocalDateTime.now().plusDays(5);
        assertTrue(isDayBeforeDate(activityStartDate)); // start date is invalid
        assertFalse(isDayBeforeDate(activityEndDate)); // end date is not before today and therefore is valid
    }
    // Written by Mathias Edvold s214973
    @Given("The end date is invalid")
    public void the_end_date_is_invalid() {
        activityStartDate = LocalDateTime.now().plusDays(5);
        activityEndDate = LocalDateTime.now().plusDays(3);
        // The start date is after the end date
        assertTrue(activityStartDate.isAfter(activityEndDate));
    }
    // Written by Mathias Edvold s214973
    @Given("Another activity in the project exists with the same name")
    public void another_activity_in_the_project_exists_with_the_same_name() {

        try {
            createProject(0);
            activityStartDate = LocalDateTime.now();
            activityEndDate = LocalDateTime.now().plusDays(20);
            budgetedTime = 20;
            project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime, actor);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException | MissingRequiredPermissionError | EmployeeIsUnavailableError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(project.hasActivityWithName(activityName));
    }
    // Written by Mathias Edvold s214973
    @When("An employee creates an activity with the same name")
    public void an_employee_creates_an_activity_with_the_same_name() {
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime, actor);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    // Written by Mathias Edvold s214973
    @Given("The project does not have a start date")
    public void the_project_does_not_have_a_start_date() throws DuplicateNameError, EmployeeIsUnavailableError {
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject("Dummy"); // Making a project with no start date
        project = ProjectManager.getInstance().getProjectByName("Dummy");
        project.setProjectLeader(projectLeader);
    }
    // Written by Mathias Edvold s214973
    @Given("The budgeted time is valid")
    public void the_budgeted_time_is_valid() {
        budgetedTime = 20;
        assertTrue(budgetedTime > 0);
    }
    // Written by Mathias Edvold s214973
    @Given("The budgeted time is invalid")
    public void the_budgeted_time_is_invalid() {
        budgetedTime = 0;
        assertFalse(budgetedTime > 0);
    }
    // Written by Mathias Edvold s214973
    @Given("There exists an activity")
    public void there_exists_an_activity() {
        activityStartDate = project.getStartDate();
        activityEndDate = activityStartDate.plusDays(20);
        budgetedTime = 20;
        try {
            project.createActivity(activityName, activityStartDate, activityEndDate, budgetedTime, projectLeader);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(project.hasActivityWithName(activityName));
    }
    // Written by Mathias Edvold s214973
    @Given("The new start date is valid")
    public void the_new_start_date_is_valid() {
        activityStartDate = activityStartDate.plusDays(1);
        assertTrue(areDatesValid(activityStartDate, activityEndDate, project.getStartDate()));
    }
    // Written by Mathias Edvold s214973
    @When("The employee changes the start date")
    public void the_employee_changes_the_start_date() {
        Activity activity = project.getActivityByName(activityName);

        try {
            activity.changeDates(activityStartDate, activityEndDate, actor);
        } catch (InvalidDateError | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }
    // Written by Bjarke Bak Jensen s214957
    @Then("The activity's start date is changed")
    public void the_activity_s_start_date_is_changed() {
        assertEquals(activityStartDate, project.getActivityByName(activityName).getStartDate());
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The new end date is valid")
    public void the_new_end_date_is_valid() {
        activityEndDate = activityEndDate.plusDays(1);
        assertTrue(areDatesValid(activityStartDate, activityEndDate, project.getStartDate()));
    }
    // Written by Bjarke Bak Jensen s214957
    @When("The employee changes the end date")
    public void the_employee_changes_the_end_date() {
        Activity activity = project.getActivityByName(activityName);
        try {
            activity.changeDates(activityStartDate, activityEndDate, actor);
        } catch (InvalidDateError | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    // Written by Bjarke Bak Jensen s214957
    @Then("The activity's end date is changed")
    public void the_activity_s_end_date_is_changed() {
        assertEquals(activityEndDate, project.getActivityByName(activityName).getEndDate());
    }
    // Written by Bjarke Bak Jensen s214957
    @Given("The given time is valid")
    public void the_given_time_is_valid() {
        budgetedTime = 1;
        assertTrue(budgetedTime > 0);
    }
    // Written by Bjarke Bak Jensen s214957
    @When("The employee changes the budgeted time")
    public void the_project_leader_changes_the_budgeted_time() {
        try {
            project.getActivityByName(activityName).setBudgetedTime(budgetedTime, actor);
        } catch (IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    // Written by Bjarke Bak Jensen s214957
    @Then("The budgeted time is changed")
    public void the_budgeted_time_is_changed() {
        Activity activity = project.getActivityByName(activityName);
        assertEquals(budgetedTime, activity.getBudgetedTime(), 0);
    }
    // Written by Mathias Edvold s214973
    @Given("The employee is not a part of the activity")
    public void the_employee_is_not_a_part_of_the_activity() {
        extraEmployee = new Employee("Tim");
        assertFalse(project.getActivityByName(activityName).isEmployeeWorkingOnActivity(extraEmployee));
    }
    // Written by Mathias Edvold s214973
    @Given("The employee is available")
    public void the_employee_is_available() {
        assertTrue(extraEmployee.isAvailable(activityStartDate, activityEndDate));
    }
    // Written by Mathias Edvold s214973
    @When("The employee adds the employee to the activity")
    public void the_employee_adds_the_employee_to_the_activity() {
        try {
            project.getActivityByName(activityName).addEmployee(extraEmployee, actor);
        } catch (IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    // Written by Mathias Edvold s214973
    @Then("The employee is now a part of the activity")
    public void the_employee_is_now_a_part_of_the_activity() {
        assertTrue(project.getActivityByName(activityName).isEmployeeWorkingOnActivity(extraEmployee));
    }
    // Written by Mathias Edvold s214973
    @Given("The employee is not the project leader")
    public void the_employee_is_not_the_project_leader() {
        assertNotEquals(projectLeader, actor);

    }
    // Written by Mathias Edvold s214973
    @When("The employee changes the dates")
    public void the_employee_changes_the_dates() {
        activityStartDate = activityStartDate.plusDays(1);
        activityEndDate = activityEndDate.plusDays(1);
        try {
            project.getActivityByName(activityName).changeDates(activityStartDate, activityEndDate, actor);
        } catch (InvalidDateError |MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    // Written by Mathias Edvold s214973
    @Given("The employee is a part of the activity")
    public void the_employee_is_a_part_of_the_activity() {
        extraEmployee = new Employee("Charles");
        try {
            project.getActivityByName(activityName).addEmployee(extraEmployee, actor);
        } catch (IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(project.getActivityByName(activityName).isEmployeeWorkingOnActivity(extraEmployee));
    }
    // Written by Mathias Edvold s214973
    @Given("Some employees are not available")
    public void some_employees_are_not_available() throws MissingRequiredPermissionError {
        project.getActivityByName(activityName).addEmployee(EmployeeManager.getInstance().getEmployeeByName("mved"), projectLeader);
        project.getActivityByName(activityName).addEmployee(EmployeeManager.getInstance().getEmployeeByName("bbje"), projectLeader);
        project.getActivityByName(activityName).addEmployee(EmployeeManager.getInstance().getEmployeeByName("done"), projectLeader);
    }
    // Written by Mathias Edvold s214973
    @When("The employee requests available employees for that activity")
    public void the_employee_requests_available_employees_for_that_activity() {

        availableEmployees = project.getActivityByName(activityName).getAvailableEmployees();
    }
    // Written by Mathias Edvold s214973
    @Then("The employee gets a list of available employees")
    public void the_employee_gets_a_list_of_available_employees() {

        ArrayList<Employee> actualAvailableEmployees = new ArrayList<>();

        ArrayList<Employee> employees = EmployeeManager.getInstance().getEmployees();

        for (Employee employee : employees) {
            if (employee.isAvailable(activityStartDate, activityEndDate)) actualAvailableEmployees.add(employee);
        }

        assertEquals(actualAvailableEmployees, availableEmployees);
    }
    // Written by Mathias Edvold s214973
    @Given("The employee does not exist")
    public void the_employee_does_not_exist() {
        actor = EmployeeManager.getInstance().getEmployeeByName("test1");
        assertTrue(actor == null);
    }
}
