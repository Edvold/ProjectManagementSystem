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
        startFrame.add(createProjectButton);

        JList projectFieldsJList = new JList(projectFieldsList.toArray());
        projectFieldsJList.setFont(new Font("Courier New",0,30));

        JScrollPane projectView = new JScrollPane(projectFieldsJList);
        projectView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        projectView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        projectView.setBounds(0,screenHeight/4, screenWidth-20, screenHeight/2+screenHeight/4-50);
        projectView.setColumnHeaderView(new JLabel("Project Information"));
        startFrame.add(projectView);

        JPanel project = new JPanel();

        JTextField nameField = new JTextField(5);
        JTextField yearField = new JTextField(5);
        JComboBox monthField = new JComboBox(months);
        JTextField dayField = new JTextField(5);

        project.add(new JLabel("Name:"));
        project.add(nameField);
        project.add(Box.createHorizontalStrut(20));
        project.add(new JLabel("Year"));
        project.add(yearField);
        project.add(Box.createHorizontalStrut(20));
        project.add(new JLabel("Month"));
        project.add(monthField);
        project.add(Box.createHorizontalStrut(20));
        project.add(new JLabel("Day"));
        project.add(dayField);







        startFrame.setVisible(true);


        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == createProjectButton){
                    int inputs = JOptionPane.showConfirmDialog(null,project,"Enter name and date",JOptionPane.OK_CANCEL_OPTION);
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

                            //creating a string with projectname, date and number
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
                    System.out.println("Hello");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //do nothing
            }
        });
    }





}
