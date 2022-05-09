import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class ProjectSteps {

    private ErrorMessageHolder errorMessageHolder;
    private Project project;
    private LocalDateTime projectStartDate;

    private String dummyName = "name";
    private Employee projectLeader;
    private String report;

    private Employee actor;

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
        catch (InvalidDateError | DuplicateNameError error) {
            errorMessageHolder.setErrorMessage(error.getMessage());
        }
    }

    @Then("A project is created with the given date")
    public void a_project_is_created_with_the_given_date() {
        assertTrue(project != null);
        assertTrue(project.getStartDate().equals(projectStartDate));
    }
    @Given("An employee creates a project")
    public void an_employee_creates_a_project() {
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
        catch (InvalidDateError|DuplicateNameError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
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

    @Given("The date is before today")
    public void the_date_is_before_today() {
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
        catch (EmployeeIsUnavailableError | IllegalArgumentException e){
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

    @Given("The employee requests a report")
    public void the_employee_requests_a_report() {
        //create a project
        ProjectManager.getInstance().emptyList();
        projectStartDate = LocalDateTime.now();
        projectStartDate = projectStartDate.plusDays(1);
        try{
            ProjectManager.getInstance().createProject(projectStartDate,dummyName);
            this.project = ProjectManager.getInstance().getProjectByName(dummyName);
        }
        catch (InvalidDateError|DuplicateNameError error){
            errorMessageHolder.setErrorMessage(error.getMessage());
        }

        //adding project leader
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
            this.report = project.getReport(actor);
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
                + "Activity: Hours worked / Budgeted time:" + "\n"
                + "Activity1: 12.0/20.0" + "\n" + "Activity2: 36.0/40.0" + "\n"
                + "Total: 48.0/60.0" ;
        assertTrue(report.equals(information));
    }
    @Given("The date is after an activity's start date")
    public void the_date_is_after_an_activity_s_start_date() throws DateNotInitializedError, DuplicateNameError, MissingRequiredPermissionError, InvalidDateError, EmployeeIsUnavailableError {
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
        projectLeader = new Employee("Carl");
        project.setProjectLeader(projectLeader);
        project.createActivity("test", LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(11), 10, projectLeader);
        projectStartDate = projectStartDate.plusDays(11);
        assertTrue(projectStartDate.isAfter(project.getActivityByName("test").getStartDate()));
    }
    @Given("The new project leader does not exist")
    public void the_new_project_leader_does_not_exist() {
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
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("test1");
        assertTrue(projectLeader == null);
    }
    @Given("An employee who is not the project leader requests a report")
    public void an_employee_who_is_not_the_project_leader_requests_a_report() {
        actor = EmployeeManager.getInstance().getEmployeeByName("mved");
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        assertTrue(actor != projectLeader);
    }
    @Given("The employee who requests the report is the project leader")
    public void the_employee_who_requests_the_report_is_the_project_leader() {
        projectLeader = EmployeeManager.getInstance().getEmployeeByName("done");
        actor = projectLeader;
        assertTrue(projectLeader == actor);
    }
    @Given("A project without a date already exists with the same name")
    public void a_project_without_a_date_already_exists_with_the_same_name() throws DuplicateNameError {
        ProjectManager.getInstance().emptyList();
        ProjectManager.getInstance().createProject(dummyName);
        Project project1 = ProjectManager.getInstance().getProjectByName(dummyName);
    }
    @When("An employee creates a project without a date but with the same name")
    public void an_employee_creates_a_project_without_a_date_but_with_the_same_name() {
        try {
            ProjectManager.getInstance().createProject(dummyName);
        } catch (DuplicateNameError e) {
            errorMessageHolder.setErrorMessage(e.getMessage());
        }
    }

}
