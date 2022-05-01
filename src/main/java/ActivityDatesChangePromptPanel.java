import javax.swing.*;

public class ActivityDatesChangePromptPanel extends PromptPanel{

    private String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private JTextField startYearField = new JTextField(5);
    private JComboBox startMonthField = new JComboBox(months);
    private JTextField startDayField = new JTextField(5);
    private JTextField endYearField = new JTextField(5);
    private JComboBox endMonthField = new JComboBox(months);
    private JTextField endDayField = new JTextField(5);

    public ActivityDatesChangePromptPanel() {
        super("Actor Initials");
        JPanel row1 = new JPanel();
        JPanel row2 = new JPanel();

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

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(row1);
        this.add(row2);
    }

    public String getActorName(){
        return super.getInput();
    }

    public int getStartYear(){
        return Integer.valueOf(startYearField.getText());
    }

    public int getStartMonth(){
        return startMonthField.getSelectedIndex()+1;
    }

    public int getStartDay(){
        return Integer.valueOf(startDayField.getText());
    }

    public int getEndYear(){
        return Integer.valueOf(endYearField.getText());
    }

    public int getEndMonth(){
        return endMonthField.getSelectedIndex()+1;
    }

    public int getEndDay(){
        return Integer.valueOf(endDayField.getText());
    }

    public void clear(){
        super.clear();
        startYearField.setText("");
        startMonthField.setSelectedIndex(0);
        startDayField.setText("");
        endYearField.setText("");
        endMonthField.setSelectedIndex(0);
        endDayField.setText("");
    }
}
