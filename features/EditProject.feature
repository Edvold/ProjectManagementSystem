Feature: Change project start date
  Description: An employee changes the start date of a project
  Actor: Employee

  Scenario: Employee changes the start date
    Given The date is valid for project
    When The employee changes the start date of the project
    Then The project start date is the new date
    And The project has the correct project number

  Scenario: Employee changes the start date to a date before today
    Given The date is before today
    When The employee changes the start date of the project
    Then An error is raised with message "The date cannot be before today"

  Scenario: Employee changes the start date to a date after an activity's start date
    Given The date is after an activity's start date
    When The employee changes the start date of the project
    Then An error is raised with message "The date cannot be after an activity's start date"