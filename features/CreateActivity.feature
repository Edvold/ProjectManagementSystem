Feature: Create Activity
  Description: An employee creates an activity
  Actor: Employee

  Scenario: Project leader creates an activity with valid date
    Given The employee is the project leader
    And The project has a start date
    And The dates are valid for activity
    And The budgeted time is valid
    When The employee creates an activity
    Then The activity is added to the project

  Scenario: Project leader creates an activity with start date before today
    Given The employee is the project leader
    And The project has a start date
    And The start date is before today
    And The end date is valid
    And The budgeted time is valid
    When The employee creates an activity
    Then An error is raised with message "The start date cannot be before today"

  Scenario: Project leader creates an activity with start date before project date
    Given The employee is the project leader
    And The project has a start date a few days after today
    And The start date is between today and project start date
    And The end date is valid
    And The budgeted time is valid
    When The employee creates an activity
    Then An error is raised with message "The start date cannot be before the start date of the project"

  Scenario: Project leader creates an activity with invalid end date
    Given The employee is the project leader
    And The project has a start date
    And The start date is valid
    And The end date is invalid
    And The budgeted time is valid
    When The employee creates an activity
    Then An error is raised with message "The end date cannot be before the start date"

 Scenario:  Project leader creates an activity when project has no start date
    Given The project does not have a start date
    And The employee is the project leader
    When The employee creates an activity
    Then An error is raised with message "Cannot create activity before the start date of the project is set"

  Scenario: Project leader creates an activity with duplicate name
    Given The employee is the project leader
    And Another activity in the project exists with the same name
    And The start date is valid
    And The end date is valid
    And The budgeted time is valid
    When An employee creates an activity with the same name
    Then An error is raised with message "Name is already in use"
    
  Scenario: Project leader creates an activity with non-positive budgeted time
    Given The employee is the project leader
    And The project has a start date
    And The start date is valid
    And The end date is valid
    And The budgeted time is invalid
    When The employee creates an activity
    Then An error is raised with message "Budgeted time has to be bigger than 0"

  Scenario: Employee creates an activity
    Given The employee is not the project leader
    When The employee creates an activity
    Then An error is raised with message "Only the project leader can create an activity"
