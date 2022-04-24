import javax.swing.*;

public class ProjectDateChangePromptPanel extends PromptPanel {

    private String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private JComboBox monthField = new JComboBox(months);
    private JTextField dayField = new JTextField(5);


    public ProjectDateChangePromptPanel() {
        super("Year");
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Month"));
        this.add(monthField);
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Day"));
        this.add(dayField);
    }

    public int getYear(){
        return Integer.valueOf(super.getInput());
    }

    public int getMonth(){
        return monthField.getSelectedIndex()+1;
    }

    public int getDay(){
        return Integer.valueOf(dayField.getText());
    }

    public void clear(){
        super.clear();
        dayField.setText("");
        monthField.setSelectedIndex(0);
    }
}
