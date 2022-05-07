import javax.swing.*;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller {

    public int currentScreen = 0;
    private View view;

    public Controller(View view){
        this.view = view;
    }

    public void CreateAProject(){
        int inputs = JOptionPane.showConfirmDialog(null,view.projectCreationPromptPanel,"Enter name and date",JOptionPane.OK_CANCEL_OPTION);
            if(inputs == JOptionPane.OK_OPTION){
                LocalDateTime projectDate = null;
                String projectName = view.projectCreationPromptPanel.getTheName();
                if(view.projectCreationPromptPanel.noDate()){
                    try {
                        ProjectManager.getInstance().createProject(projectName);
                        Project project = ProjectManager.getInstance().getProjectByName(projectName);

                        Object[] fields = new Object[]{projectName, "", project.getProjectNumber(),""};
                         view.tableModel.addRow(fields);
                         view.projectFieldsJTable.setModel(view.tableModel);

                        // only clearing optionpane if project is created
                        view.projectCreationPromptPanel.clear();
                    } catch (DuplicateNameError ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    try {
                        projectDate = LocalDateTime.of(view.projectCreationPromptPanel.getYear(),view.projectCreationPromptPanel.getMonth(),view.projectCreationPromptPanel.getDay(),0,0 );
                    }
                    catch (Exception error){
                        JOptionPane.showMessageDialog(null,error.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }

                    try {
                        ProjectManager.getInstance().createProject(projectDate, projectName);
                        Project project = ProjectManager.getInstance().getProjectByName(projectName);

                        Object[] fields = new Object[]{projectName, project.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), project.getProjectNumber(),""};
                        view.tableModel.addRow(fields);
                        view.projectFieldsJTable.setModel(view.tableModel);

                        // only clearing optionpane if project is created
                        view.projectCreationPromptPanel.clear();
                    } catch (DuplicateNameError | InvalidDateError ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
        }
    }

    public void changeToProjectScreen(MouseEvent e) {
        if ((e.getClickCount() == 2) & (view.projectFieldsJTable.getSelectedRow() >= 0)) {
            changeScreen();
        }
    }

    public void changeProjectStartDate(){
        int inputs = JOptionPane.showConfirmDialog(null, view.projectDateChangePromptPanel, "Enter new date", JOptionPane.OK_CANCEL_OPTION);
        if (inputs == JOptionPane.OK_OPTION) {
            LocalDateTime projectDate = null;
            try {
                projectDate = LocalDateTime.of(view.projectDateChangePromptPanel.getYear(), view.projectDateChangePromptPanel.getMonth(), view.projectDateChangePromptPanel.getDay(), 0, 0);
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            try {
                int index = view.projectFieldsJTable.getSelectedRow();
                String projectName = (String) view.projectFieldsJTable.getValueAt(index,0);
                Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
                currentProject.setStartDate(projectDate);
                view.projectFieldsJTable.setValueAt(currentProject.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),view.projectFieldsJTable.getSelectedRow(),1);
                view.projectFieldsJTable.setValueAt(currentProject.getProjectNumber(),view.projectFieldsJTable.getSelectedRow(),2);

                // only clearing optionpane if date is changed
                view.projectDateChangePromptPanel.clear();
            } catch (InvalidDateError ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void createAnActivity(){
        int inputs = JOptionPane.showConfirmDialog(null, view.createActivityPromptPanel, "Enter Name, Start Date and End Date", JOptionPane.OK_CANCEL_OPTION);
        if (inputs == JOptionPane.OK_OPTION) {
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;
            try {
                startDate = LocalDateTime.of(view.createActivityPromptPanel.getStartYear(), view.createActivityPromptPanel.getStartMonth(), view.createActivityPromptPanel.getStartDay(), 0, 0);
                endDate = LocalDateTime.of(view.createActivityPromptPanel.getEndYear(), view.createActivityPromptPanel.getEndMonth(), view.createActivityPromptPanel.getEndDay(), 0, 0);
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            try {
                String activityName = view.createActivityPromptPanel.getTheName();
                double budgetedTime = view.createActivityPromptPanel.getExpectedTime();
                int index = view.projectFieldsJTable.getSelectedRow();
                String currentProjectName = (String) view.projectFieldsJTable.getValueAt(index,0);
                String employeeName = view.createActivityPromptPanel.getEmployeeName();
                Employee employee = EmployeeManager.getInstance().getEmployeeByName(employeeName);
                ProjectManager.getInstance().getProjectByName(currentProjectName).createActivity(activityName, startDate, endDate, budgetedTime, employee);

                Activity activity = ProjectManager.getInstance().getProjectByName(currentProjectName).getActivityByName(activityName);
                Object[] fields = new Object[]{activityName, activity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), activity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),activity.getBudgetedTime()};
                view.activityTableModel.addRow(fields);
                view.activityFieldsJTable.setModel(view.activityTableModel);

                // only clearing optionpane if activity is created
                view.createActivityPromptPanel.clear();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    public void addAProjectLeader(){
        int inputs = JOptionPane.showConfirmDialog(null, view.addAProjectLeaderPromptPanel, "Enter Initials of new Project Leader", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            String employeeName = view.addAProjectLeaderPromptPanel.getInput();
            int index = view.projectFieldsJTable.getSelectedRow();
            try {
                String projectName = (String) view.projectFieldsJTable.getValueAt(index,0);
                Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
                Employee projectLeader = EmployeeManager.getInstance().getEmployeeByName(employeeName);
                currentProject.setProjectLeader(projectLeader);
                view.addAProjectLeaderPromptPanel.clear();
                view.projectFieldsJTable.setValueAt(projectLeader.getName(),view.projectFieldsJTable.getSelectedRow(),3);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void changeToActivityScreen(MouseEvent e){
        if ((e.getClickCount() == 2) & (view.activityFieldsJTable.getSelectedRow() >= 0)) {
            changeScreen();
        }
    }

    public void goToPreviousScreen(){
            view.screens[currentScreen].setVisible(false);
            if (currentScreen > 0) {
                currentScreen--;
            }
            view.screens[currentScreen].setVisible(true);
            view.projectCreationPromptPanel.clear();
            view.createActivityPromptPanel.clear();
            view.projectDateChangePromptPanel.clear();
            view.registerHoursPromptPanel.clear();
            view.changeBudgetedTimePromptPanel.clear();
            view.activityDatesChangePromptPanel.clear();
            view.addEmployeePromptPanel.clear();
            view.addAProjectLeaderPromptPanel.clear();
    }

    private void changeScreen(){
        view.screens[currentScreen].setVisible(false);
        if (currentScreen < 2) {
            currentScreen++;
        } else {
            currentScreen = 0;
        }
        view.screens[currentScreen].setVisible(true);
        view.projectCreationPromptPanel.clear();
    }

    public void addAnEmployee(){
        int inputs = JOptionPane.showConfirmDialog(null, view.addEmployeePromptPanel, "Enter Initials of Employee to be added and Your Initials", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            int index1 = view.projectFieldsJTable.getSelectedRow();
            String projectName = (String) view.projectFieldsJTable.getValueAt(index1,0);
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJTable.getSelectedRow();
            String activityName = (String) view.activityFieldsJTable.getValueAt(index2,0);
            Activity currentActivity = currentProject.getActivityByName(activityName);
            String employeeName = view.addEmployeePromptPanel.getFirstField();
            String actorName = view.addEmployeePromptPanel.getActorName();
            try {
                Employee employee = EmployeeManager.getInstance().getEmployeeByName(employeeName);
                Employee actor = EmployeeManager.getInstance().getEmployeeByName(actorName);
                currentActivity.addEmployee(employee,actor);
                view.addEmployeePromptPanel.clear();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void changeActivityDates(){
        int inputs = JOptionPane.showConfirmDialog(null, view.activityDatesChangePromptPanel, "Enter Your Initials, Start Date and End Date", JOptionPane.OK_CANCEL_OPTION);
        if (inputs == JOptionPane.OK_OPTION) {
            int index1 = view.projectFieldsJTable.getSelectedRow();
            String projectName = (String) view.projectFieldsJTable.getValueAt(index1,0);
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJTable.getSelectedRow();
            String activityName = (String) view.activityFieldsJTable.getValueAt(index2,0);
            Activity currentActivity = currentProject.getActivityByName(activityName);
            String actorName = view.activityDatesChangePromptPanel.getActorName();
            try {
                LocalDateTime startDate = LocalDateTime.of(view.activityDatesChangePromptPanel.getStartYear(), view.activityDatesChangePromptPanel.getStartMonth(), view.activityDatesChangePromptPanel.getStartDay(), 0, 0);
                LocalDateTime endDate = LocalDateTime.of(view.activityDatesChangePromptPanel.getEndYear(), view.activityDatesChangePromptPanel.getEndMonth(), view.activityDatesChangePromptPanel.getEndDay(), 0, 0);
                Employee actor = EmployeeManager.getInstance().getEmployeeByName(actorName);
                currentActivity.changeDates(startDate,endDate,actor);
                view.activityDatesChangePromptPanel.clear();
                view.activityFieldsJTable.setValueAt(currentActivity.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),index2,1);
                view.activityFieldsJTable.setValueAt(currentActivity.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),index2,2);
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void changeBudgetedTime(){
        int inputs = JOptionPane.showConfirmDialog(null, view.changeBudgetedTimePromptPanel, "Enter Budgeted Time and Your Initials", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            int index1 = view.projectFieldsJTable.getSelectedRow();
            String projectName = (String) view.projectFieldsJTable.getValueAt(index1,0);
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJTable.getSelectedRow();
            String activityName = (String) view.activityFieldsJTable.getValueAt(index2,0);
            Activity currentActivity = currentProject.getActivityByName(activityName);
            String actorName = view.changeBudgetedTimePromptPanel.getActorName();
            try {
                int newBudgetedTime = Integer.valueOf(view.changeBudgetedTimePromptPanel.getFirstField());
                Employee actor = EmployeeManager.getInstance().getEmployeeByName(actorName);
                currentActivity.setBudgetedTime(newBudgetedTime,actor);
                view.changeBudgetedTimePromptPanel.clear();
                view.activityFieldsJTable.setValueAt(currentActivity.getBudgetedTime(),index2,3);
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void registerHoursForActivity(){
        int inputs = JOptionPane.showConfirmDialog(null, view.registerHoursPromptPanel, "Enter Hours and Your Initials", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            int index1 = view.projectFieldsJTable.getSelectedRow();
            String projectName = (String) view.projectFieldsJTable.getValueAt(index1,0);
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJTable.getSelectedRow();
            String activityName = (String) view.activityFieldsJTable.getValueAt(index2,0);
            Activity currentActivity = currentProject.getActivityByName(activityName);
            String actorName = view.registerHoursPromptPanel.getActorName();
            try {
                int hours = Integer.valueOf(view.registerHoursPromptPanel.getFirstField());
                Employee actor = EmployeeManager.getInstance().getEmployeeByName(actorName);
                actor.registerHours(currentActivity,hours);
                view.registerHoursPromptPanel.clear();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void getAvailableEmployees(){
        int index1 = view.projectFieldsJTable.getSelectedRow();
        String projectName = (String) view.projectFieldsJTable.getValueAt(index1,0);
        Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
        int index2 = view.activityFieldsJTable.getSelectedRow();
        String activityName = (String) view.activityFieldsJTable.getValueAt(index2,0);
        Activity currentActivity = currentProject.getActivityByName(activityName);
        ArrayList<Employee> employees = currentActivity.getAvailableEmployees();
        String[] employeeNames = new String[employees.size()];
        for (int i = 0; i < employees.size(); i++){
            employeeNames[i] = employees.get(i).getName();
        }
        view.availableEmployeesJList.setListData(employeeNames);
        JOptionPane.showMessageDialog(null, view.availableEmployeesScrollPane, "Available Employees for Activity : " + currentActivity.getName(), JOptionPane.OK_OPTION);
    }
}
