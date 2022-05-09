import javax.swing.*;
// Written by Bjarke Bak Jensen s214957
public class ProjectCreationPromptPanel extends PromptPanel {

    private String[] monthsNoDate = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "No Date"};
    private JTextField yearField = new JTextField(5);
    private JComboBox<String> monthField = new JComboBox<>(monthsNoDate);
    private JTextField dayField = new JTextField(5);
    // Written by Bjarke Bak Jensen s214957
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
    // Written by Bjarke Bak Jensen s214957
    public String getTheName(){
        return super.getInput();
    }

    // Written by Bjarke Bak Jensen s214957
    public int getYear(){
        return Integer.parseInt(yearField.getText());
    }

    // Written by Bjarke Bak Jensen s214957
    public int getMonth(){
        return monthField.getSelectedIndex()+1;
    }

    // Written by Bjarke Bak Jensen s214957
    public int getDay(){
        return Integer.parseInt(dayField.getText());
    }

    // Written by Bjarke Bak Jensen s214957
    public boolean noDate(){
        return yearField.getText().equals("") & monthField.getSelectedIndex() == monthsNoDate.length-1 & dayField.getText().equals("");
    }

    // Written by Bjarke Bak Jensen s214957
    public void clear(){
        super.clear();
        yearField.setText("");
        dayField.setText("");
        monthField.setSelectedIndex(0);
    }
}
