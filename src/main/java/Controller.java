import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    public static void main(String[] args) {

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

        JTextArea text = new JTextArea();
        text.setBounds(0,screenHeight/4,225,100);
        text.setText("line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n line\n");
        JScrollPane projectView = new JScrollPane(text);
        projectView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        projectView.setBounds(0,screenHeight/4, screenWidth-20, screenHeight/2+screenHeight/4-50);
        startFrame.add(projectView);

        JPanel project = new JPanel();
        project.setBackground(Color.BLUE);
        project.setBounds(0,screenHeight/4,225,225);

        JTextField text1 = new JTextField(5);
        JTextField text2 = new JTextField(5);
        project.add(text1);
        project.add(text2);






        startFrame.setVisible(true);


        createProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == createProjectButton){
                   String projectName = JOptionPane.showInputDialog(startFrame,"Insert project name",null);
                    try {
                        projectManager.getInstance().createProject(projectName);
                    } catch (DuplicateNameError ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }





}
