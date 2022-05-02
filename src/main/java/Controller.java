import javax.swing.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

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

                        //creating a string with project name and number
                        String projectFields = projectName + "   " + ProjectManager.getInstance().getProjectByName(projectName).getProjectNumber();
                        view.projectFieldsList.add(projectFields);
                        view.projectFieldsJList.setListData(view.projectFieldsList.toArray());

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

                        //creating a string with project name, date and number
                        String projectFields = projectName + "   " + projectDate.getDayOfMonth() + "." + projectDate.getMonth() + "." +
                                projectDate.getYear() + "   " + ProjectManager.getInstance().getProjectByName(projectName).getProjectNumber();
                        view.projectFieldsList.add(projectFields);
                        view.projectFieldsJList.setListData(view.projectFieldsList.toArray());

                        // only clearing optionpane if project is created
                        view.projectCreationPromptPanel.clear();
                    } catch (DuplicateNameError | InvalidDateError ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
        }
        view.startFrame.requestFocusInWindow();
    }

    public void changeToProjectScreen(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_ENTER) & (view.projectFieldsJList.getSelectedIndex() >= 0)) {
            changeScreen();
        }
        view.startFrame.requestFocusInWindow();
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
                int index = view.projectFieldsJList.getSelectedIndex();
                String name = view.projectFieldsList.get(index).split(" ")[0];
                ProjectManager.getInstance().getProjectByName(name).setStartDate(projectDate);

                // only clearing optionpane if project is created
                view.projectDateChangePromptPanel.clear();
            } catch (InvalidDateError ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        view.startFrame.requestFocusInWindow();
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
                int index = view.projectFieldsJList.getSelectedIndex();
                String currentProjectName = view.projectFieldsList.get(index).split(" ")[0];
                String employeeName = view.createActivityPromptPanel.getEmployeeName();
                Employee employee = EmployeeManager.getInstance().getEmployeeByName(employeeName);
                ProjectManager.getInstance().getProjectByName(currentProjectName).createActivity(activityName, startDate, endDate, budgetedTime, employee);

                //creating a string with activity name and dates
                String activityFields = activityName + "   " + startDate.getDayOfMonth() + "." + startDate.getMonth() + "." +
                        startDate.getYear() + "   " + endDate.getDayOfMonth() + "." + endDate.getMonth() + "." +
                        endDate.getYear();
                view.activityFieldsList.add(activityFields);
                view.activityFieldsJList.setListData(view.activityFieldsList.toArray());

                // only clearing optionpane if activity is created
                view.createActivityPromptPanel.clear();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
        view.startFrame.requestFocusInWindow();
    }

    public void addAProjectLeader(){
        int inputs = JOptionPane.showConfirmDialog(null, view.addAProjectLeaderPromptPanel, "Enter Initials of new Project Leader", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            String employeeName = view.addAProjectLeaderPromptPanel.getInput();
            int index = view.projectFieldsJList.getSelectedIndex();
            String projectName = view.projectFieldsList.get(index).split(" ")[0];
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            try {
                Employee projectLeader = EmployeeManager.getInstance().getEmployeeByName(employeeName);
                currentProject.setProjectLeader(projectLeader);
                view.addAProjectLeaderPromptPanel.clear();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        view.startFrame.requestFocusInWindow();
    }

    public void changeToActivityScreen(KeyEvent e){
        if ((e.getKeyCode() == KeyEvent.VK_ENTER) & (view.activityFieldsJList.getSelectedIndex() >= 0)) {
            changeScreen();
        }
        view.startFrame.requestFocusInWindow();
    }

    public void goToPreviousScreen(KeyEvent e){
        if ((e.getKeyCode() == KeyEvent.VK_ESCAPE)) {
            view.screens[currentScreen].setVisible(false);
            if (currentScreen > 0) {
                currentScreen--;
            }
            view.screens[currentScreen].setVisible(true);
            view.projectCreationPromptPanel.clear();
            view.createActivityPromptPanel.clear();
            view.projectDateChangePromptPanel.clear();
        }
        view.startFrame.requestFocusInWindow();
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
            int index1 = view.projectFieldsJList.getSelectedIndex();
            String projectName = view.projectFieldsList.get(index1).split(" ")[0];
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJList.getSelectedIndex();
            String activityName = view.activityFieldsList.get(index2).split(" ")[0];
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
        view.startFrame.requestFocusInWindow();
    }

    public void changeActivityDates(){
        int inputs = JOptionPane.showConfirmDialog(null, view.activityDatesChangePromptPanel, "Enter Your Initials, Start Date and End Date", JOptionPane.OK_CANCEL_OPTION);
        if (inputs == JOptionPane.OK_OPTION) {
            int index1 = view.projectFieldsJList.getSelectedIndex();
            String projectName = view.projectFieldsList.get(index1).split(" ")[0];
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJList.getSelectedIndex();
            String activityName = view.activityFieldsList.get(index2).split(" ")[0];
            Activity currentActivity = currentProject.getActivityByName(activityName);
            String actorName = view.activityDatesChangePromptPanel.getActorName();
            try {
                LocalDateTime startDate = LocalDateTime.of(view.activityDatesChangePromptPanel.getStartYear(), view.activityDatesChangePromptPanel.getStartMonth(), view.activityDatesChangePromptPanel.getStartDay(), 0, 0);
                LocalDateTime endDate = LocalDateTime.of(view.activityDatesChangePromptPanel.getEndYear(), view.activityDatesChangePromptPanel.getEndMonth(), view.activityDatesChangePromptPanel.getEndDay(), 0, 0);
                Employee actor = EmployeeManager.getInstance().getEmployeeByName(actorName);
                currentActivity.changeDates(startDate,endDate,actor);
                view.activityDatesChangePromptPanel.clear();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, error.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        view.startFrame.requestFocusInWindow();
    }

    public void changeBudgetedTime(){
        int inputs = JOptionPane.showConfirmDialog(null, view.changeBudgetedTimePromptPanel, "Enter Budgeted Time and Your Initials", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            int index1 = view.projectFieldsJList.getSelectedIndex();
            String projectName = view.projectFieldsList.get(index1).split(" ")[0];
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJList.getSelectedIndex();
            String activityName = view.activityFieldsList.get(index2).split(" ")[0];
            Activity currentActivity = currentProject.getActivityByName(activityName);
            String actorName = view.changeBudgetedTimePromptPanel.getActorName();
            try {
                int newBudgetedTime = Integer.valueOf(view.changeBudgetedTimePromptPanel.getFirstField());
                Employee actor = EmployeeManager.getInstance().getEmployeeByName(actorName);
                currentActivity.setBudgetedTime(newBudgetedTime,actor);
                view.changeBudgetedTimePromptPanel.clear();
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
        view.startFrame.requestFocusInWindow();
    }

    public void registerHoursForActivity(){
        int inputs = JOptionPane.showConfirmDialog(null, view.registerHoursPromptPanel, "Enter Hours and Your Initials", JOptionPane.OK_CANCEL_OPTION);
        if(inputs == JOptionPane.OK_OPTION){
            int index1 = view.projectFieldsJList.getSelectedIndex();
            String projectName = view.projectFieldsList.get(index1).split(" ")[0];
            Project currentProject = ProjectManager.getInstance().getProjectByName(projectName);
            int index2 = view.activityFieldsJList.getSelectedIndex();
            String activityName = view.activityFieldsList.get(index2).split(" ")[0];
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
        view.startFrame.requestFocusInWindow();
    }
}
