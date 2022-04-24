Feature: Edit Activity
  Description: A project leader edits an activity
  Actor: Employee

  Scenario: The project leader changes the start date of an activity
    Given There exists an activity
    And The employee is the project leader
    And The new start date is valid
    When The employee changes the start date
    Then The activity's start date is changed

  Scenario: The project leader changes the end date of an activity
    Given There exists an activity
    And The employee is the project leader
    And The new end date is valid
    When The employee changes the end date
    Then The activity's end date is changed

  Scenario: The project leader changes the start date of an activity to a date before today
    Given There exists an activity
    And The employee is the project leader
    And The start date is before today
    When The employee changes the start date
    Then An error is raised with message "The start date cannot be before today"

  Scenario: The project leader changes the start date of an activity to a date before the start date of the project
    Given The project has a start date a few days after today
    And There exists an activity
    And The employee is the project leader
    # Now set the new start date to be after today but before the project start date
    And The start date is between today and project start date
    When The employee changes the start date
    Then An error is raised with message "The start date cannot be before the start date of the project"

  Scenario: The project leader changes the end date of an activity to an invalid date
    Given There exists an activity
    And The employee is the project leader
    And The end date is invalid
    When The employee changes the end date
    Then An error is raised with message "The end date cannot be before the start date"

  Scenario: An employee who is not the project leader changes the dates
    Given There exists an activity
    And The employee is not the project leader
    When The employee changes the dates
    Then An error is raised with message "Only the project leader can change the data of an activity"
  
  Scenario: The project leader changes the budgeted time
    Given There exists an activity
    And The employee is the project leader
    And The given time is valid
    When The employee changes the budgeted time
    Then The budgeted time is changed

  Scenario: The project leader changes the budgeted time to an invalid time
    Given There exists an activity
    And The employee is the project leader
    And The budgeted time is invalid
    When The employee changes the budgeted time
    Then An error is raised with message "Budgeted time has to be bigger than 0"

  Scenario: An employee who is not the project leader changes the budgeted time
    Given There exists an activity
    And The employee is not the project leader
    When The employee changes the budgeted time
    Then An error is raised with message "Only the project leader can change the data of an activity"

  Scenario: The project leader adds an employee to an activity
    Given There exists an activity
    And The employee is the project leader
    And The employee is not a part of the activity
    And The employee is available
    # Now the project leader adds the employee to the activity
    When The employee adds the employee to the activity
    Then The employee is now a part of the activity

  Scenario: The project leader adds an employee to an activity
    Given There exists an activity
    And The employee is the project leader
    And The employee is a part of the activity
    # Now the project leader adds the employee to the activity
    When The employee adds the employee to the activity
    Then An error is raised with message "The employee is already a part of the activity"

  Scenario: An employee who is not the project leader adds an employee to an activity
    Given There exists an activity
    And The employee is not the project leader
    When The employee adds the employee to the activity
    Then An error is raised with message "Only the project leader can change the data of an activity"

  # Missing scenario for employee not available