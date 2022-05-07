import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ProjectSteps {

    private ErrorMessageHolder errorMessageHolder;
    private Project project;
    private LocalDateTime projectStartDate;

    private String dummyName = "name";
    private Employee projectLeader;

    public ProjectSteps(ErrorMessageHolder errorMessageHolder){
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("The employee creates a project with today as the date")
    public void the_employee_creates_a_project_with_today_as_the_date() {
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        try {
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
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
        ProjectManager.getInstance().emptyList();
        try {
            ProjectManager.getInstance().createProject(dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
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
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.minusDays(1);
        try {
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
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
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try{
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }


    }
    @When("An employee creates a project with the same name")
    public void an_employee_creates_a_project_with_the_same_name() throws DuplicateNameError, InvalidDateError {
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try{
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            Project project1 = ProjectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

    @Given("The date is valid for project")
    public void the_date_is_valid_for_project() {
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        try {
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        projectStartDate = projectStartDate.plusYears(1);
    }

    @When("The employee changes the start date of the project")
    public void the_employee_changes_the_start_date_of_the_project() {
        try {
            ProjectManager.getInstance().changeProjectStartDate(project.getProjectName(),projectStartDate);
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
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
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
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
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
        ProjectManager.getInstance().emptyList();
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try {
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
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
            ProjectManager.getInstance().createProject(projectStartDate,"dummyName");
            this.project = ProjectManager.getInstance().getProjectByName("dummyName");
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
        assertTrue(!projectLeader.isAvailable(projectStartDate,project.getExpectedEndDate()));
    }
    @Then("The project has the correct project number")
    public void the_project_has_the_correct_project_number() {
        String trueProjectNumber = String.valueOf(project.getStartDate().getYear()).substring(2) + "0001";
        assertEquals(project.getProjectNumber(), trueProjectNumber);
    }


}
