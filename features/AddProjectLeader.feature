Feature: Add project leader
  Description: Adds/Changes a project leader
  Actor: Employee

  Scenario: Employee changes the project leader of a project
    Given The new project leader is available
    When The employee changes the project leader
    Then The project leader is changed to the new project leader

  Scenario: Employee changes the project leader of a project to an unavailable employee
    Given The new project leader is unavailable
    When The employee changes the project leader
    Then An error is raised with message "The new project leader is unavailable for this project"