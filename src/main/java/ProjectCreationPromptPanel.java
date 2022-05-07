import javax.swing.*;

public class ProjectCreationPromptPanel extends PromptPanel {

    //just make date in here?

    private String[] monthsNoDate = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "No Date"};
    private JTextField yearField = new JTextField(5);
    private JComboBox<String> monthField = new JComboBox<>(monthsNoDate);
    private JTextField dayField = new JTextField(5);

    public ProjectCreationPromptPanel(){
        super("Name");
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Year"));
        this.add(yearField);
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Month"));
        this.add(monthField);
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Day"));
        this.add(dayField);
    }

    public String getTheName(){
        return super.getInput();
    }

    public int getYear(){
        return Integer.parseInt(yearField.getText());
    }

    public int getMonth(){
        return monthField.getSelectedIndex()+1;
    }

    public int getDay(){
        return Integer.parseInt(dayField.getText());
    }

    public boolean noDate(){
        return yearField.getText().equals("") & monthField.getSelectedIndex() == monthsNoDate.length-1 & dayField.getText().equals("");
    }

    public void clear(){
        super.clear();
        yearField.setText("");
        dayField.setText("");
        monthField.setSelectedIndex(0);
    }
}
