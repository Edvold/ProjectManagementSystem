Feature: Get available employees
  Description: See which employees are available to work on an activity.
  Actor: Employee

  Scenario: An employee requests available employees with valid date
    Given There exists an activity
    When The employee requests available employees for that activity
    Then The employee gets a list of available employees
