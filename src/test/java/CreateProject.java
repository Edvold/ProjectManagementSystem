import io.cucumber.java.ca.Cal;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreateProject {

    private ErrorMessageHolder errorMessageHolder;
    private Project project;
    private LocalDateTime projectDate;
    private ProjectManager projectManager;
    private String dummyName = "name";

    public CreateProject(ErrorMessageHolder errorMessageHolder){
        this.errorMessageHolder = errorMessageHolder;
    }

    @Given("The employee creates a project with today as the date")
    public void the_employee_creates_a_project_with_today_as_the_date() {
        projectManager.getInstance().emptyList();
        projectDate = LocalDateTime.now();
        try {
            projectManager.getInstance().createProject(projectDate,dummyName);
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
        projectDate = LocalDateTime.now();
        projectDate = projectDate.plusDays(1);
        try {
            projectManager.getInstance().createProject(projectDate,dummyName);
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
        assertTrue(project.getStartDate().equals(projectDate));
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
        projectDate = LocalDateTime.now();
        projectDate = projectDate.minusDays(1);
        try {
            projectManager.getInstance().createProject(projectDate,dummyName);
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
        projectDate = LocalDateTime.now();
        projectDate = projectDate.plusDays(1);
        try{
            projectManager.getInstance().createProject(projectDate,dummyName);
            this.project = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }


    }
    @When("An employee creates a project with the same name")
    public void an_employee_creates_a_project_with_the_same_name() throws DuplicateNameError, InvalidDateError {
        projectDate = LocalDateTime.now();
        projectDate = projectDate.plusDays(1);
        try{
            projectManager.getInstance().createProject(projectDate,dummyName);
            Project project1 = projectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }


}
