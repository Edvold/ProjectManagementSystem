import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class View {

    private Controller controller = new Controller(this);
    private final Rectangle rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    protected final int screenWidth = rectangle.width;
    protected final int screenHeight = rectangle.height;
    protected JPanel[] screens;
    protected JFrame startFrame;
    protected JButton createProjectButton;
    protected JScrollPane projectScrollPane;
    protected ProjectCreationPromptPanel projectCreationPromptPanel;
    protected DefaultTableModel tableModel;
    protected JTable projectFieldsJTable;
    protected JButton createActivityButton;
    protected JButton changeStartDateButton;
    protected JButton addProjectLeaderButton;
    protected JButton showReportButton;
    protected JButton goToPreviousScreenButton1;
    protected JScrollPane activityScrollPane;
    protected DefaultTableModel activityTableModel;
    protected JTable activityFieldsJTable;
    protected ProjectDateChangePromptPanel projectDateChangePromptPanel;
    protected CreateActivityPromptPanel createActivityPromptPanel;
    protected PromptPanel addAProjectLeaderPromptPanel;
    protected PromptPanel showReportPromptPanel;
    protected JButton addEmployeeButton;
    protected JButton changeActivityDatesButton;
    protected JButton changeBudgetedTimeButton;
    protected JButton registerHoursButton;
    protected JButton getAvailableEmployeesButton;
    protected JButton goToPreviousScreenButton2;
    protected TwoFieldsActorPromptPanel addEmployeePromptPanel;
    protected TwoFieldsActorPromptPanel changeBudgetedTimePromptPanel;
    protected ActivityDatesChangePromptPanel activityDatesChangePromptPanel;
    protected TwoFieldsActorPromptPanel registerHoursPromptPanel;
    protected JList<String> reportFieldsJList;
    protected JScrollPane reportScrollPane;
    protected JList<String> availableEmployeesJList;
    protected JScrollPane availableEmployeesScrollPane;

    public void startView() {

        //Creating panel array with panels for each screen in the program
        screens = new JPanel[]{new JPanel(null), new JPanel(null), new JPanel(null)};
        for (JPanel screen : screens) {
            screen.setBounds(0, 0, screenWidth, screenHeight);
            screen.setVisible(false);
            screen.setBackground(new Color(0x1F1B24));
        }
        screens[controller.currentScreen].setVisible(true);

        //creating frame
        startFrame = new JFrame();

        startFrame.setLayout(null);
        startFrame.setTitle("ProjectManagementSystem");
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        startFrame.setSize(screenWidth, screenHeight);

        //Project Creation Screen

        //Button for creating project
        createProjectButton = new JButton();
        createProjectButton.setText("Create Project");
        createProjectButton.setSize(new Dimension(screenWidth / 4, screenHeight / 4));
        createProjectButton.setBounds(0, 0, screenWidth / 4, screenHeight / 4);
        createProjectButton.setVisible(true);
        createProjectButton.setBackground(new Color(0x6f6f6f));
        createProjectButton.setForeground(new Color(0x000000));
        screens[0].add(createProjectButton);

        //initializing tableModel
        String[] projectColumns = new String[]{"Project Name", "Start Date", "Project Number", "Project Leader"};
        tableModel = new DefaultTableModel(projectColumns,0);

        projectFieldsJTable = new JTable(tableModel);
        projectFieldsJTable.setDefaultEditor(Object.class,null);
        projectFieldsJTable.setFont(new Font("Courier New", Font.PLAIN, 30));
        projectFieldsJTable.setRowHeight(projectFieldsJTable.getRowHeight()+18);

        //ScrollPane displaying project fields
        projectScrollPane = new JScrollPane(projectFieldsJTable);
        projectScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        projectScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        projectScrollPane.setBounds(0, screenHeight / 4, screenWidth - 14, screenHeight / 2 + screenHeight / 4 - 50);
        screens[0].add(projectScrollPane);

        //promptPanel used for prompting for input when creating project
        projectCreationPromptPanel = new ProjectCreationPromptPanel();

        startFrame.add(screens[0]);


        //Project Screen

        //Button for creating activity
        createActivityButton = new JButton();
        createActivityButton.setText("Create Activity");
        createActivityButton.setSize(new Dimension(screenWidth / 5, screenHeight / 4));
        createActivityButton.setBounds(0, 0, screenWidth / 5, screenHeight / 4);
        createActivityButton.setVisible(true);
        createActivityButton.setBackground(new Color(0x6f6f6f));
        createActivityButton.setForeground(new Color(0x000000));
        screens[1].add(createActivityButton);

        //Button for changing start date
        changeStartDateButton = new JButton();
        changeStartDateButton.setText("change the start date of the project");
        changeStartDateButton.setSize(new Dimension(screenWidth / 5, screenHeight / 4));
        changeStartDateButton.setBounds(screenWidth / 5, 0, screenWidth / 5, screenHeight / 4);
        changeStartDateButton.setVisible(true);
        changeStartDateButton.setBackground(new Color(0x6f6f6f));
        changeStartDateButton.setForeground(new Color(0x000000));
        screens[1].add(changeStartDateButton);

        //Button for adding projectLeader
        addProjectLeaderButton = new JButton();
        addProjectLeaderButton.setText("Add/Change Project Leader");
        addProjectLeaderButton.setSize(new Dimension(screenWidth / 5, screenHeight / 4));
        addProjectLeaderButton.setBounds(2*screenWidth / 5, 0, screenWidth / 5, screenHeight / 4);
        addProjectLeaderButton.setVisible(true);
        addProjectLeaderButton.setBackground(new Color(0x6f6f6f));
        addProjectLeaderButton.setForeground(new Color(0x000000));
        screens[1].add(addProjectLeaderButton);

        showReportButton = new JButton();
        showReportButton.setText("Show Report for Project");
        showReportButton.setSize(new Dimension(screenWidth / 5, screenHeight / 4));
        showReportButton.setBounds(3*screenWidth / 5, 0, screenWidth / 5, screenHeight / 4);
        showReportButton.setVisible(true);
        showReportButton.setBackground(new Color(0x6f6f6f));
        showReportButton.setForeground(new Color(0x000000));
        screens[1].add(showReportButton);

        goToPreviousScreenButton1 = new JButton();
        goToPreviousScreenButton1.setText("Go to Previous Screen");
        goToPreviousScreenButton1.setSize(new Dimension(screenWidth / 5, screenHeight / 4));
        goToPreviousScreenButton1.setBounds(4*screenWidth / 5, 0, screenWidth / 5, screenHeight / 4);
        goToPreviousScreenButton1.setVisible(true);
        goToPreviousScreenButton1.setBackground(new Color(0x6f6f6f));
        goToPreviousScreenButton1.setForeground(new Color(0x000000));
        screens[1].add(goToPreviousScreenButton1);

        //initializing tableModel for activity
        String[] activityColumns = new String[]{"Activity Name", "Start Date", "End Date", "Budgeted Time"};
        activityTableModel = new DefaultTableModel(activityColumns,0);

        activityFieldsJTable = new JTable(activityTableModel);
        activityFieldsJTable.setDefaultEditor(Object.class,null);
        activityFieldsJTable.setFont(new Font("Courier New", Font.PLAIN, 30));
        activityFieldsJTable.setRowHeight(activityFieldsJTable.getRowHeight()+18);

        //ScrollPane displaying activity fields
        activityScrollPane = new JScrollPane(activityFieldsJTable);
        activityScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        activityScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        activityScrollPane.setBounds(0, screenHeight / 4, screenWidth - 14, screenHeight / 2 + screenHeight / 4 - 50);
        activityScrollPane.setColumnHeaderView(new JLabel("List of activities"));
        activityScrollPane.setVisible(true);
        screens[1].add(activityScrollPane);

        //PromptPanels for the buttons
        projectDateChangePromptPanel = new ProjectDateChangePromptPanel();
        createActivityPromptPanel = new CreateActivityPromptPanel();
        addAProjectLeaderPromptPanel = new PromptPanel("New Project Leader's Initials");
        showReportPromptPanel = new PromptPanel("Your Initials");

        //TextArea to show report
        reportFieldsJList = new JList();
        reportScrollPane = new JScrollPane(reportFieldsJList);
        reportScrollPane.setColumnHeaderView(new JLabel("Report Information"));
        reportScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        reportScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        startFrame.add(screens[1]);

        //Activity Screen

        //Button to add Employee to Activity
        addEmployeeButton = new JButton();
        addEmployeeButton.setText("Add Employee");
        addEmployeeButton.setSize(new Dimension(screenWidth / 6, screenHeight / 4));
        addEmployeeButton.setBounds(0, 0, screenWidth / 6, screenHeight / 4);
        addEmployeeButton.setVisible(true);
        addEmployeeButton.setBackground(new Color(0x6f6f6f));
        addEmployeeButton.setForeground(new Color(0x000000));
        screens[2].add(addEmployeeButton);

        //Button to change Dates for Activity
        changeActivityDatesButton = new JButton();
        changeActivityDatesButton.setText("Change Dates");
        changeActivityDatesButton.setSize(new Dimension(screenWidth / 6, screenHeight / 4));
        changeActivityDatesButton.setBounds(screenWidth/6, 0, screenWidth / 6, screenHeight / 4);
        changeActivityDatesButton.setVisible(true);
        changeActivityDatesButton.setBackground(new Color(0x6f6f6f));
        changeActivityDatesButton.setForeground(new Color(0x000000));
        screens[2].add(changeActivityDatesButton);

        //Button to change BudgetedTime for Activity
        changeBudgetedTimeButton = new JButton();
        changeBudgetedTimeButton.setText("Change Budgeted Time");
        changeBudgetedTimeButton.setSize(new Dimension(screenWidth / 6, screenHeight / 4));
        changeBudgetedTimeButton.setBounds(screenWidth/3, 0, screenWidth / 6, screenHeight / 4);
        changeBudgetedTimeButton.setVisible(true);
        changeBudgetedTimeButton.setBackground(new Color(0x6f6f6f));
        changeBudgetedTimeButton.setForeground(new Color(0x000000));
        screens[2].add(changeBudgetedTimeButton);

        //Button to register hours for activity
        registerHoursButton = new JButton();
        registerHoursButton.setText("Register Hours");
        registerHoursButton.setSize(new Dimension(screenWidth / 6, screenHeight / 4));
        registerHoursButton.setBounds(screenWidth/2, 0, screenWidth / 6, screenHeight / 4);
        registerHoursButton.setVisible(true);
        registerHoursButton.setBackground(new Color(0x6f6f6f));
        registerHoursButton.setForeground(new Color(0x000000));
        screens[2].add(registerHoursButton);

        //Button to see available employees for activity
        getAvailableEmployeesButton = new JButton();
        getAvailableEmployeesButton.setText("Get Available Employees");
        getAvailableEmployeesButton.setSize(new Dimension(screenWidth / 6, screenHeight / 4));
        getAvailableEmployeesButton.setBounds(2*screenWidth/3, 0, screenWidth / 6, screenHeight / 4);
        getAvailableEmployeesButton.setVisible(true);
        getAvailableEmployeesButton.setBackground(new Color(0x6f6f6f));
        getAvailableEmployeesButton.setForeground(new Color(0x000000));
        screens[2].add(getAvailableEmployeesButton);

        goToPreviousScreenButton2 = new JButton();
        goToPreviousScreenButton2.setText("Go to Previous Screen");
        goToPreviousScreenButton2.setSize(new Dimension(screenWidth / 6, screenHeight / 4));
        goToPreviousScreenButton2.setBounds(5*screenWidth / 6, 0, screenWidth / 6, screenHeight / 4);
        goToPreviousScreenButton2.setVisible(true);
        goToPreviousScreenButton2.setBackground(new Color(0x6f6f6f));
        goToPreviousScreenButton2.setForeground(new Color(0x000000));
        screens[2].add(goToPreviousScreenButton2);

        //PromptPanels for the buttons
        addEmployeePromptPanel = new TwoFieldsActorPromptPanel("Employee Initials");
        changeBudgetedTimePromptPanel = new TwoFieldsActorPromptPanel("New Budgeted Time");
        activityDatesChangePromptPanel = new ActivityDatesChangePromptPanel();
        registerHoursPromptPanel = new TwoFieldsActorPromptPanel("Hours to register");

        //Components for pop-up window to show available employees
        availableEmployeesJList = new JList<>();
        availableEmployeesScrollPane = new JScrollPane(availableEmployeesJList);
        availableEmployeesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        availableEmployeesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        availableEmployeesScrollPane.setColumnHeaderView(new JLabel("Available Employees"));

        startFrame.add(screens[2]);

        startFrame.setVisible(true);


        createProjectButton.addActionListener(e -> controller.CreateAProject());

        projectFieldsJTable.addMouseListener((MouseClickedListener) e -> controller.changeToProjectScreen(e));

        changeStartDateButton.addActionListener(e -> controller.changeProjectStartDate());

        createActivityButton.addActionListener(e -> controller.createAnActivity());

        addProjectLeaderButton.addActionListener(e -> controller.addAProjectLeader());

        activityFieldsJTable.addMouseListener((MouseClickedListener) e -> controller.changeToActivityScreen(e));

        addEmployeeButton.addActionListener(e -> controller.addAnEmployee());

        changeActivityDatesButton.addActionListener(e -> controller.changeActivityDates());

        changeBudgetedTimeButton.addActionListener(e -> controller.changeBudgetedTime());

        registerHoursButton.addActionListener(e -> controller.registerHoursForActivity());

        getAvailableEmployeesButton.addActionListener(e -> controller.getAvailableEmployees());

        goToPreviousScreenButton1.addActionListener(e -> controller.goToPreviousScreen());

        goToPreviousScreenButton2.addActionListener(e -> controller.goToPreviousScreen());

        showReportButton.addActionListener(e -> controller.showReport());
    }
}
