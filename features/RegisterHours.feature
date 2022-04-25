Feature: Register Hours
  Description: An employee registers the amount of hours they've worked
  Actor: Employee

  Scenario: Employee registers hours for activity
    Given The given hours are valid
    And The employee works on the activity
    When The employee registers hours for the activity
    Then The employee's hours are added to the old registered hours

  Scenario: Employee unregisters hours for activity
    Given The given hours are valid
    And The employee works on the activity
    And The employee has registered hours for the activity
    When The employee unregisters hours for the activity
    Then The employee's hours are subtracted from the old registered hours

  Scenario: Employee registers hours for activity with invalid hours
    Given The hours are invalid
    And The employee works on the activity
    When The employee registers hours for the activity
    Then An error is raised with message "You need to input a positive amount of hours"

  Scenario: Employee unregisters hours for activity with invalid hours
    Given The hours are invalid
    And The employee works on the activity
    When The employee unregisters hours for the activity
    Then An error is raised with message "You need to input a positive amount of hours"

  Scenario: Employee registers hours for activity with invalid date
    Given The employee doesn't work on the activity
    When The employee registers hours for the activity
    Then An error is raised with message "You don't work on this activity"

  Scenario: Employee unregisters hours for activity with invalid date
    Given The employee doesn't work on the activity
    When The employee unregisters hours for the activity
    Then An error is raised with message "You don't work on this activity"