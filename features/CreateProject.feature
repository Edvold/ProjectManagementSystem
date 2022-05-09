Feature: Create Project
	Description: An employee creates a project
	Actor: Employee

	Scenario: Employee creates a project with a date
		Given The employee creates a project with today as the date
		Then A project is created with the given date
		And The project has the correct project number

	Scenario: Employee creates a project without a date
		Given An employee creates a project
		Then A project is created
		And The date of the project is not initialized

	Scenario: Employee creates a project with invalid date
		Given The employee creates a project with an invalid date
		Then An error is raised with message "Invalid date"

	Scenario: Employee creates a project with duplicate name
		Given A project already exists with the same name
		When An employee creates a project with the same name
		Then An error is raised with message "Name is already in use"

	Scenario: Employee creates a project without a date but with duplicate name
		Given A project without a date already exists with the same name
		When An employee creates a project without a date but with the same name
		Then An error is raised with message "Name is already in use"