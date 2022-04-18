import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    public static void main(String[] args) {

        List<String> projectFieldsList = new ArrayList<>();
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        ProjectManager projectManager = ProjectManager.getInstance();
        Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int screenWidth = rectangle.width;
        int screenHeight = rectangle.height;

        JFrame startFrame = new JFrame();

        startFrame.setLayout(null);
        startFrame.setTitle("ProjectManagementSystem");
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setSize(screenWidth, screenHeight);
        startFrame.getContentPane().setBackground(new Color(0x1F1B24));

        JButton createProjectButton = new JButton();
        createProjectButton.setText("Create Project");
        createProjectButton.setSize(new Dimension(screenWidth/4,screenHeight/4));
        createProjectButton.setBounds(0, 0, screenWidth/4, screenHeight/4);
        createProjectButton.setVisible(true);
        startFrame.add(createProjectButton);

        JList projectFieldsJList = new JList(projectFieldsList.toArray());
        projectFieldsJList.setFont(new Font("Courier New",0,30));

        JScrollPane projectView = new JScrollPane(projectFieldsJList);
        projectView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        projectView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        projectView.setBounds(0,screenHeight/4, screenWidth-20, screenHeight/2+screenHeight/4-50);
        projectView.setColumnHeaderView(new JLabel("Project Information"));
        startFrame.add(projectView);

        JPanel projectCreationPromptPanel = new JPanel();

        JTextField nameField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        JComboBox monthField = new JComboBox(months);
        JTextField dayField = new JTextField(5);

        projectCreationPromptPanel.add(new JLabel("Name:"));
        projectCreationPromptPanel.add(nameField);
        projectCreationPromptPanel.add(Box.createHorizontalStrut(20));
        projectCreationPromptPanel.add(new JLabel("Year"));
        projectCreationPromptPanel.add(yearField);
        projectCreationPromptPanel.add(Box.createHorizontalStrut(20));
        projectCreationPromptPanel.add(new JLabel("Month"));
        projectCreationPromptPanel.add(monthField);
        projectCreationPromptPanel.add(Box.createHorizontalStrut(20));
        projectCreationPromptPanel.add(new JLabel("Day"));
        projectCreationPromptPanel.add(dayField);



        //Activity screen
        List<String> activityFieldsList = new ArrayList<>();

        JButton createActivityButton = new JButton();
        createActivityButton.setText("Create Activity");
        createActivityButton.setSize(new Dimension(screenWidth/4,screenHeight/4));
        createActivityButton.setBounds(0, 0, screenWidth/4, screenHeight/4);
        createActivityButton.setVisible(false);
        startFrame.add(createActivityButton);

        JButton changeStartDateButton = new JButton();
        changeStartDateButton.setText("change the start date of the project");
        changeStartDateButton.setSize(new Dimension(screenWidth/4,screenHeight/4));
        changeStartDateButton.setBounds(screenWidth/4, 0, screenWidth/4, screenHeight/4);
        changeStartDateButton.setVisible(false);
        startFrame.add(changeStartDateButton);

        JList activityFieldsJList = new JList(activityFieldsList.toArray());
        activityFieldsJList.setFont(new Font("Courier New",0,30));

        JScrollPane activityView = new JScrollPane(activityFieldsJList);
        activityView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        activityView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        activityView.setBounds(0,screenHeight/4, screenWidth-20, screenHeight/2+screenHeight/4-50);
        activityView.setVisible(false);
        startFrame.add(activityView);

        JPanel projectDateChangePromptPanel = new JPanel();

        JTextField yearField2 = new JTextField(5);
        JComboBox monthField2 = new JComboBox(months);
        JTextField dayField2 = new JTextField(5);

        projectDateChangePromptPanel.add(new JLabel("Year"));
        projectDateChangePromptPanel.add(yearField2);
        projectDateChangePromptPanel.add(Box.createHorizontalStrut(20));
        projectDateChangePromptPanel.add(new JLabel("Month"));
        projectDateChangePromptPanel.add(monthField2);
        projectDateChangePromptPanel.add(Box.createHorizontalStrut(20));
        projectDateChangePromptPanel.add(new JLabel("Day"));
        projectDateChangePromptPanel.add(dayField2);

        JPanel createActivityPromptPanel = new JPanel();
        JPanel createActivityPromptPanel2 = new JPanel();

        JTextField nameField2 = new JTextField(5);
        JTextField yearField3 = new JTextField(5);
        JComboBox monthField3 = new JComboBox(months);
        JTextField dayField3 = new JTextField(5);
        JTextField yearField4 = new JTextField(5);
        JComboBox monthField4 = new JComboBox(months);
        JTextField dayField4 = new JTextField(5);
        JTextField budgetedTimeField = new JTextField(5);

        createActivityPromptPanel.add(new JLabel("Name"));
        createActivityPromptPanel.add(nameField2);
        createActivityPromptPanel.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel.add(new JLabel("Start Year"));
        createActivityPromptPanel.add(yearField3);
        createActivityPromptPanel.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel.add(new JLabel("Start Month"));
        createActivityPromptPanel.add(monthField3);
        createActivityPromptPanel.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel.add(new JLabel("Start Day"));
        createActivityPromptPanel.add(dayField3);
        //createActivityPromptPanel.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel2.add(new JLabel("End Year"));
        createActivityPromptPanel2.add(yearField4);
        createActivityPromptPanel2.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel2.add(new JLabel("End Month"));
        createActivityPromptPanel2.add(monthField4);
        createActivityPromptPanel2.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel2.add(new JLabel("End Day"));
        createActivityPromptPanel2.add(dayField4);
        createActivityPromptPanel2.add(Box.createHorizontalStrut(20));
        createActivityPromptPanel2.add(new JLabel("Expected Time"));
        createActivityPromptPanel2.add(budgetedTimeField);

        JPanel createActivityPromptPanel3 = new JPanel();
        createActivityPromptPanel3.setLayout(new BoxLayout(createActivityPromptPanel3,BoxLayout.Y_AXIS));
        createActivityPromptPanel3.add(createActivityPromptPanel);
        createActivityPromptPanel3.add(createActivityPromptPanel2);







        startFrame.setVisible(true);


        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == createProjectButton){
                    int inputs = JOptionPane.showConfirmDialog(null,projectCreationPromptPanel,"Enter name and date",JOptionPane.OK_CANCEL_OPTION);
                    if(inputs == JOptionPane.OK_OPTION){
                        LocalDateTime projectDate = null;
                        try {
                            projectDate = LocalDateTime.of(Integer.valueOf(yearField.getText()),monthField.getSelectedIndex()+1,Integer.valueOf(dayField.getText()),0,0 );
                        }
                        catch (Exception error){
                            JOptionPane.showMessageDialog(null,error.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                        String projectName = nameField.getText();
                        try {
                            projectManager.getInstance().createProject(projectDate, projectName);

                            //creating a string with project name, date and number
                            String projectFields = projectName + "   " + projectDate.getDayOfMonth() + "." + projectDate.getMonth() + "." +
                                    projectDate.getYear() + "   " + projectManager.getInstance().getProjectByName(projectName).getPROJECT_NUMBER();
                            projectFieldsList.add(projectFields);
                            projectFieldsJList.setListData(projectFieldsList.toArray());

                            // only clearing optionpane if project is created
                            nameField.setText("");
                            yearField.setText("");
                            dayField.setText("");
                            monthField.setSelectedIndex(0);
                        } catch (DuplicateNameError | InvalidDateError ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        projectFieldsJList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if((e.getKeyCode() == KeyEvent.VK_ENTER) & (projectFieldsJList.getSelectedIndex() >= 0)){
                    createProjectButton.setVisible(false);
                    projectView.setVisible(false);
                    activityView.setColumnHeaderView(new JLabel("List of activities"));
                    changeStartDateButton.setVisible(true);
                    createActivityButton.setVisible(true);
                    activityView.setVisible(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //do nothing
            }
        });

        changeStartDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == changeStartDateButton){

                    int inputs = JOptionPane.showConfirmDialog(null,projectDateChangePromptPanel,"Enter new date",JOptionPane.OK_CANCEL_OPTION);
                    if(inputs == JOptionPane.OK_OPTION){
                        LocalDateTime projectDate = null;
                        try {
                            projectDate = LocalDateTime.of(Integer.valueOf(yearField2.getText()),monthField2.getSelectedIndex()+1,Integer.valueOf(dayField2.getText()),0,0 );
                        }
                        catch (Exception error){
                            JOptionPane.showMessageDialog(null,error.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            int index = projectFieldsJList.getSelectedIndex();
                            String name = projectFieldsList.get(index).split(" ")[0];
                            projectManager.getInstance().getProjectByName(name).setStartDate(projectDate);

                            // only clearing optionpane if project is created
                            yearField.setText("");
                            dayField.setText("");
                            monthField.setSelectedIndex(0);
                        } catch (InvalidDateError ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        createActivityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == createActivityButton){
                    int inputs = JOptionPane.showConfirmDialog(null,createActivityPromptPanel3,"Enter Name, Start Date and End Date",JOptionPane.OK_CANCEL_OPTION);
                    if(inputs == JOptionPane.OK_OPTION){
                        LocalDateTime startDate = null;
                        LocalDateTime endDate = null;
                        try {
                            startDate = LocalDateTime.of(Integer.valueOf(yearField3.getText()),monthField3.getSelectedIndex()+1,Integer.valueOf(dayField3.getText()),0,0 );
                            endDate = LocalDateTime.of(Integer.valueOf(yearField4.getText()),monthField4.getSelectedIndex()+1,Integer.valueOf(dayField4.getText()),0,0 );
                        }
                        catch (Exception error){
                            JOptionPane.showMessageDialog(null,error.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                        try {
                            String activityName = nameField2.getText();
                            int budgetedTime = Integer.valueOf(budgetedTimeField.getText());
                            int index = projectFieldsJList.getSelectedIndex();
                            String currentProjectName = projectFieldsList.get(index).split(" ")[0];
                            projectManager.getInstance().getProjectByName(currentProjectName).createActivity(activityName,startDate,endDate,budgetedTime);

                            //creating a string with activity name and dates
                            String activityFields = activityName + "   " + startDate.getDayOfMonth() + "." + startDate.getMonth() + "." +
                                    startDate.getYear() + "   " + endDate.getDayOfMonth() + "." + endDate.getMonth() + "." +
                                    endDate.getYear();
                                    activityFieldsList.add(activityFields);
                            activityFieldsJList.setListData(activityFieldsList.toArray());

                            // only clearing optionpane if activity is created
                            nameField2.setText("");
                            yearField3.setText("");
                            dayField3.setText("");
                            monthField3.setSelectedIndex(0);
                            yearField4.setText("");
                            dayField4.setText("");
                            monthField4.setSelectedIndex(0);

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            }
        });
    }





}
