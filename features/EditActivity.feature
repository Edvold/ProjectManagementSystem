Feature: Edit Activity
  Description: A project leader edits an activity
  Actor: Project leader

  Scenario: The project leader changes the start date of an activity
    Given The project leader is the project leader for the given activity
    And There exists an activity
    And The new start date is valid
    When The project leader changes the start date
    Then The activity's start date is changed


  Scenario: The project leader changes the end date of an activity
    Given The project leader is the project leader for the given activity
    And There exists an activity
    And The new end date is valid
    When The project leader changes the end date
    Then The activity's end date is changed

  # IMPLEMENT SCENARIOS WITH INVALID DATES AND NOT PROJECT LEADER

  Scenario: The project leader changes the budgeted time
    Given The project leader is the project leader for the given activity
    And There exists an activity
    And The given time is valid
    When The project leader changes the budgeted time
    Then The budgeted time is changed

  # IMPLEMENT TEST WITH INVALID BUDGETED TIME

  Scenario: The project leader adds an employee to an activity
    Given The project leader is the project leader for the given activity
    And There exists an activity
    And The employee is not a part of the activity
    And The employee is available
    When The project leader adds the employee to the activity
    Then The employee is a part of the activity

  # IMPLEMENT TEST WHERE EMPLOYEE IS ALREADY PART OF ACTIVITY