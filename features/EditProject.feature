Feature: Change project start date
  Description: An employee changes the start date of a project
  Actor: Employee

  Scenario: Employee changes the start date
    Given The date is valid for project
    When The employee changes the start date of the project
    Then The project start date is the new date
    And The project has the correct project number

  Scenario: Employee changes the start date with invalid date
    Given The date is invalid
    When The employee changes the start date of the project
    Then An error is raised with message "Invalid date"