Feature: Show Report
  Description: Show report for a project, including time usage, and expected work hours left.
  Actor: Project leader

  Scenario: The project leader requests a report for a project
    Given The employee who requests the report is the project leader
    And The employee requests a report
    Then The project leader receives a report
    And The information is correct

  Scenario: An employee requests a report for a project
    Given An employee who is not the project leader requests a report
    When The employee requests a report
    Then An error is raised with message "You are not the leader of this project"