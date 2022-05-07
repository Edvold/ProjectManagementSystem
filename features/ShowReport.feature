Feature: Show Report
  Description: Show report for a project, including time usage, and expected work hours left.
  Actor: Project leader

  Scenario: The project leader requests a report for a project
    Given The project leader requests a report
    Then The project leader receives a report
    And The information is correct