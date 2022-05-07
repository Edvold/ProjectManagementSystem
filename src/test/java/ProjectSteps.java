import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProjectSteps {

    private ErrorMessageHolder errorMessageHolder;
    private Project project;
    private LocalDateTime projectStartDate;
    private ProjectManager projectManager;
    private String dummyName = "name";
    private Employee projectLeader;
    private String report;

    public ProjectSteps(ErrorMessageHolder errorMessageHolder){
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("The employee creates a project with today as the date")
    public void the_employee_creates_a_project_with_today_as_the_date() {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }

    @Given("The employee creates a project with a day after today as the date")
    public void the_employee_creates_a_project_with_a_day_after_today_as_the_date() {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("A project is created with the given date")
    public void a_project_is_created_with_the_given_date() {
        assertTrue(project != null);
        assertTrue(project.getStartDate().equals(projectStartDate));
    }
    @Given("An employee creates a project")
    public void an_employee_creates_a_project() throws DuplicateNameError {
        projectManager.getInstance().emptyList();
        try {
            projectManager.getInstance().createProject(dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (DuplicateNameError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        }

    }
    @Then("A project is created")
    public void a_project_is_created() {
        assertNotNull(project);
    }

    @Then("The date of the project is not initialized")
    public void the_date_of_the_project_is_not_initialized() {
        assertTrue(project.getStartDate() == null);
    }

    @Given("The employee creates a project with an invalid date")
    public void the_employee_creates_a_project_with_an_invalid_date() {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.minusDays(1);
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }
    @Then("An error is raised with message {string}")
    public void an_error_is_raised_with_message(String string) {
        assertTrue(string.equals(errorMessageHolder.getErrorMessage()));
    }
    @Given("A project already exists with the same name")
    public void a_project_already_exists_with_the_same_name() throws DuplicateNameError, InvalidDateError {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try{
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError|DuplicateNameError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        }
    }

    @When("An employee creates a project with the same name")
    public void an_employee_creates_a_project_with_the_same_name() throws DuplicateNameError, InvalidDateError {
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try{
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            Project project1 = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("The date is valid for project")
    public void the_date_is_valid_for_project() {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        projectStartDate = projectStartDate.plusDays(1);
    }

    @When("The employee changes the start date of the project")
    public void the_employee_changes_the_start_date_of_the_project() {
        try {
            projectManager.getInstance().changeProjectStartDate(project.getProjectName(),projectStartDate);
        } catch (InvalidDateError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Then("The project start date is the new date")
    public void the_project_start_date_is_the_new_date() {
        assertTrue(project.getStartDate().equals(projectStartDate));
    }

    @Given("The date is invalid")
    public void the_date_is_invalid() {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        projectStartDate = projectStartDate.minusDays(2);
    }

    @Given("The new project leader is available")
    public void the_new_project_leader_is_available() {
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        assertTrue(projectLeader.isAvailable(projectStartDate,project.getExpectedEndDate()));
    }

    @When("The employee changes the project leader")
    public void the_employee_changes_the_project_leader() throws EmployeeIsUnavailableError {
        try {
            project.setProjectLeader(projectLeader);
        }
        catch (EmployeeIsUnavailableError e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }

    @Then("The project leader is changed to the new project leader")
    public void the_project_leader_is_changed_to_the_new_project_leader() {
        assertTrue(project.getProjectLeader().equals(projectLeader));
    }

    @Given("The new project leader is unavailable")
    public void the_new_project_leader_is_unavailable() {
        projectManager.getInstance().emptyList();
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        try {
            project.setProjectLeader(projectLeader);
        }
        catch (EmployeeIsUnavailableError e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        try {
            projectManager.getInstance().createProject(projectStartDate,"dummyName");
            this.project = projectManager.getInstance().getProjectByName("dummyName");
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(!projectLeader.isAvailable(projectStartDate,project.getExpectedEndDate()));
    }

    @Given("The project leader requests a report")
    public void the_project_leader_requests_a_report() {
        //create a project
        projectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try{
            projectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError|DuplicateNameError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        }

        //adding project leader
        this.projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        try {
            project.setProjectLeader(projectLeader);
        }
        catch (EmployeeIsUnavailableError e){
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //creating a couple of activities
        LocalDateTime activityStartDate = project.getStartDate();
        LocalDateTime activityEndDate = activityStartDate.plusDays(20);
        int budgetedTime = 20;
        try {
            project.createActivity("Activity1", activityStartDate, activityEndDate, budgetedTime, projectLeader);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        budgetedTime = 40;
        try {
            project.createActivity("Activity2", activityStartDate, activityEndDate, budgetedTime, projectLeader);
        } catch (InvalidDateError | DuplicateNameError | DateNotInitializedError | IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

        //adding employees and registering hours
        try {
            Employee emp1 = EmployeeManager.getInstance().getEmployeeByName("bbje");
            Employee emp2 = EmployeeManager.getInstance().getEmployeeByName("mved");
            project.getActivityByName("Activity1").addEmployee(emp1, projectLeader);
            project.getActivityByName("Activity2").addEmployee(emp2,projectLeader);
            emp1.registerHours(project.getActivityByName("Activity1"),12);
            emp2.registerHours(project.getActivityByName("Activity2"),36);
        } catch (IllegalArgumentException | MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        try {
            this.report = project.getReport(projectLeader);
        } catch (MissingRequiredPermissionError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }

    }

    @Then("The project leader receives a report")
    public void the_project_leader_receives_a_report() {
        assertTrue(!report.equals(null));
    }

    @Then("The information is correct")
    public void the_information_is_correct() {
        String information = "Start Date: " + project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n" + "End Date: " + project.getExpectedEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\n"
                + "Activity Name: hours worked/budgeted time" + "\n"
                + "Activity1: 12.0/20.0" + "\n" + "Activity2: 36.0/40.0";
        assertTrue(report.equals(information));
    }


}
