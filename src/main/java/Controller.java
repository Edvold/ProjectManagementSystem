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
                        String projectFields = projectName + "   " + ProjectManager.getInstance().getProjectByName(projectName).getPROJECT_NUMBER();
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
                                projectDate.getYear() + "   " + ProjectManager.getInstance().getProjectByName(projectName).getPROJECT_NUMBER();
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

    public void changeToActivityScreen(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_ENTER) & (view.projectFieldsJList.getSelectedIndex() >= 0)) {
                    view.screens[currentScreen].setVisible(false);
                    if (currentScreen < 2) {
                        currentScreen++;
                    } else {
                        currentScreen = 0;
                    }
                    view.screens[currentScreen].setVisible(true);
                    view.projectCreationPromptPanel.clear();
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
                ProjectManager.getInstance().getProjectByName(currentProjectName).createActivity(activityName, startDate, endDate, budgetedTime, view.createActivityPromptPanel.getEmployee());

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
}
