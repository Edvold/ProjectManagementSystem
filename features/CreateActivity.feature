Feature: Create Activity
  Description: An employee creates an activity
  Actor: Employee

  Scenario: Employee creates an activity with valid date
    Given The date is valid for activity
    When The employee creates an activity
    Then The activity is added to the project

  Scenario: Employee creates an activity with invalid start date
    Given The start date is invalid
    When The employee creates an activity
    Then An error is raised with message "Invalid date"

  Scenario: Employee creates an activity with invalid end date
    Given The end date is invalid
    When The employee creates an activity
    Then An error is raised with message "Invalid date"

  Scenario: Employee creates an activity with duplicate name
    Given Another activity in the project exists with the same name
    When An employee creates an activity with the same name
    Then An error is raised with message "Name is already in use"