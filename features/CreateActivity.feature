Feature: Create Activity
  Description: An employee creates an activity
  Actor: Employee

  Scenario: Employee creates an activity with valid date
    Given The project has a start date
    And The dates are valid for activity
    And The budgeted time is valid
    When The employee creates an activity
    Then The activity is added to the project

  Scenario: Employee creates an activity with start date before today
    Given The project has a start date
    And The start date is before today
    And The end date is valid
    And The budgeted time is valid
    When The employee creates an activity
    Then An error is raised with message "The start date cannot be before today"

  Scenario: Employee creates an activity with start date before project date
    Given The project has a start date a few days after today
    And The start date is between today and project start date
    And The end date is valid
    And The budgeted time is valid
    When The employee creates an activity
    Then An error is raised with message "The start date cannot be before the start date of the project"

  Scenario: Employee creates an activity with invalid end date
    Given The project has a start date
    And The start date is valid
    And The end date is invalid
    And The budgeted time is valid
    When The employee creates an activity
    Then An error is raised with message "The end date cannot be before the start date"
    
  Scenario:  Employee creates an activity when project has no start date
    Given The project does not have a start date
    When The employee creates an activity
    Then An error is raised with message "Cannot create activity before the start date of the project is set"
    
  Scenario: Employee creates an activity with duplicate name
    Given Another activity in the project exists with the same name
    And The start date is valid
    And The end date is valid
    And The budgeted time is valid
    When An employee creates an activity with the same name
    Then An error is raised with message "Name is already in use"
    
  Scenario: Employee creates an activity with non-positive budgeted time
    Given The project has a start date
    And The start date is valid
    And The end date is valid
    And The budgeted time is invalid
    When The employee creates an activity
    Then An error is raised with message "Budgeted time has to be bigger than 0"