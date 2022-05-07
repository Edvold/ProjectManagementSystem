import javax.swing.*;

public class CreateActivityPromptPanel extends PromptPanel {

    private String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private JTextField startYearField = new JTextField(5);
    private JComboBox<String> startMonthField = new JComboBox<>(months);
    private JTextField startDayField = new JTextField(5);
    private JTextField endYearField = new JTextField(5);
    private JComboBox<String> endMonthField = new JComboBox<>(months);
    private JTextField endDayField = new JTextField(5);
    private JTextField budgetedTimeField = new JTextField(5);
    private JTextField employeeField = new JTextField(5);
    
    public CreateActivityPromptPanel(){
        super("Name");
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();
        JPanel row3 = new JPanel();

        row1.add(new JLabel("Start Year"));
        row1.add(startYearField);
        row1.add(Box.createHorizontalStrut(20));
        row1.add(new JLabel("Start Month"));
        row1.add(startMonthField);
        row1.add(Box.createHorizontalStrut(20));
        row1.add(new JLabel("Start Day"));
        row1.add(startDayField);

        row2.add(new JLabel("End Year"));
        row2.add(endYearField);
        row2.add(Box.createHorizontalStrut(20));
        row2.add(new JLabel("End Month"));
        row2.add(endMonthField);
        row2.add(Box.createHorizontalStrut(20));
        row2.add(new JLabel("End Day"));
        row2.add(endDayField);

        row3.add(new JLabel("Budgeted Time"));
        row3.add(budgetedTimeField);
        row3.add(Box.createHorizontalStrut(20));
        row3.add(new JLabel("Your Initials"));
        row3.add(employeeField);

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(row1);
        this.add(row2);
        this.add(row3);
    }

    public String getTheName(){
        return super.getInput();
    }

    public int getStartYear(){
        return Integer.parseInt(startYearField.getText());
    }

    public int getStartMonth(){
        return startMonthField.getSelectedIndex()+1;
    }

    public int getStartDay(){
        return Integer.parseInt(startDayField.getText());
    }

    public int getEndYear(){
        return Integer.parseInt(endYearField.getText());
    }

    public int getEndMonth(){
        return endMonthField.getSelectedIndex()+1;
    }

    public int getEndDay(){
        return Integer.parseInt(endDayField.getText());
    }

    public double getExpectedTime(){
        return Double.parseDouble(budgetedTimeField.getText());
    }

    public String getEmployeeName(){
        return employeeField.getText();
    }

    public void clear(){
        super.clear();
        startYearField.setText("");
        startMonthField.setSelectedIndex(0);
        startDayField.setText("");
        endYearField.setText("");
        endMonthField.setSelectedIndex(0);
        endDayField.setText("");
        budgetedTimeField.setText("");
        employeeField.setText("");
    }

    
}
