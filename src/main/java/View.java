import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class View {

    private Controller controller = new Controller(this);
    protected List<String> projectFieldsList = new ArrayList<>();
    private final Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    protected final int screenWidth = rectangle.width;
    protected final int screenHeight = rectangle.height;
    protected JPanel[] screens;
    protected JFrame startFrame;
    protected JButton createProjectButton;
    protected JList projectFieldsJList;
    protected JScrollPane projectScrollPane;
    protected ProjectCreationPromptPanel projectCreationPromptPanel;
    protected List<String> activityFieldsList = new ArrayList<>();
    protected JButton createActivityButton;
    protected JButton changeStartDateButton;
    protected JList activityFieldsJList;
    protected JScrollPane activityView;
    protected ProjectDateChangePromptPanel projectDateChangePromptPanel;
    protected CreateActivityPromptPanel createActivityPromptPanel;

    //public static void main(String[] args) {

    public void startView() {

        //Creating panel array with panels for each screen in the program
        screens = new JPanel[]{new JPanel(null), new JPanel(null), new JPanel(null)};
        for (int i = 0; i < screens.length; i++) {
            screens[i].setBounds(0, 0, screenWidth, screenHeight);
            screens[i].setVisible(false);
            screens[i].setBackground(new Color(0x1F1B24));
        }
        screens[controller.currentScreen].setVisible(true);

        //creating frame
        startFrame = new JFrame();

        startFrame.setLayout(null);
        startFrame.setTitle("ProjectManagementSystem");
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setSize(screenWidth, screenHeight);

        //Button for creating project
        createProjectButton = new JButton();
        createProjectButton.setText("Create Project");
        createProjectButton.setSize(new Dimension(screenWidth / 4, screenHeight / 4));
        createProjectButton.setBounds(0, 0, screenWidth / 4, screenHeight / 4);
        createProjectButton.setVisible(true);
        createProjectButton.setBackground(new Color(0x6f6f6f));
        createProjectButton.setForeground(new Color(0x000000));
        screens[0].add(createProjectButton);

        //List of project fields displayed for user
        projectFieldsJList = new JList(projectFieldsList.toArray());
        projectFieldsJList.setFont(new Font("Courier New", 0, 30));

        //ScrollPane displaying project fields
        projectScrollPane = new JScrollPane(projectFieldsJList);
        projectScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        projectScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        projectScrollPane.setBounds(0, screenHeight / 4, screenWidth - 20, screenHeight / 2 + screenHeight / 4 - 50);
        projectScrollPane.setColumnHeaderView(new JLabel("Project Information"));
        screens[0].add(projectScrollPane);

        //promptPanel used for prompting for input when creating project
        projectCreationPromptPanel = new ProjectCreationPromptPanel();

        startFrame.add(screens[0]);


        //Activity screen

        //Button for creating activity
        createActivityButton = new JButton();
        createActivityButton.setText("Create Activity");
        createActivityButton.setSize(new Dimension(screenWidth / 4, screenHeight / 4));
        createActivityButton.setBounds(0, 0, screenWidth / 4, screenHeight / 4);
        createActivityButton.setVisible(true);
        createActivityButton.setBackground(new Color(0x6f6f6f));
        createActivityButton.setForeground(new Color(0x000000));
        screens[1].add(createActivityButton);

        //Button for changing start date
        changeStartDateButton = new JButton();
        changeStartDateButton.setText("change the start date of the project");
        changeStartDateButton.setSize(new Dimension(screenWidth / 4, screenHeight / 4));
        changeStartDateButton.setBounds(screenWidth / 4, 0, screenWidth / 4, screenHeight / 4);
        changeStartDateButton.setVisible(true);
        changeStartDateButton.setBackground(new Color(0x6f6f6f));
        changeStartDateButton.setForeground(new Color(0x000000));
        screens[1].add(changeStartDateButton);

        //List of fields to display for activities
        activityFieldsJList = new JList(activityFieldsList.toArray());
        activityFieldsJList.setFont(new Font("Courier New", 0, 30));

        //ScrollPane displaying activity fields
        activityView = new JScrollPane(activityFieldsJList);
        activityView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        activityView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        activityView.setBounds(0, screenHeight / 4, screenWidth - 20, screenHeight / 2 + screenHeight / 4 - 50);
        activityView.setColumnHeaderView(new JLabel("List of activities"));
        activityView.setVisible(true);
        screens[1].add(activityView);

        //PromptPanels for the buttons
        projectDateChangePromptPanel = new ProjectDateChangePromptPanel();
        createActivityPromptPanel = new CreateActivityPromptPanel();

        startFrame.add(screens[1]);

        startFrame.requestFocusInWindow();
        startFrame.setVisible(true);


        createProjectButton.addActionListener(e -> controller.CreateAProject());

        projectFieldsJList.addKeyListener((KeyPressedListener) e -> controller.changeToActivityScreen(e));

        changeStartDateButton.addActionListener(e -> controller.changeProjectStartDate());

        createActivityButton.addActionListener(e -> controller.createAnActivity());

        startFrame.addKeyListener((KeyPressedListener) e -> controller.goToPreviousScreen(e));
    }
}
