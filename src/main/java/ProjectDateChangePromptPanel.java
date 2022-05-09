import javax.swing.*;

// Written by Mathias Edvold s214973
public class ProjectDateChangePromptPanel extends PromptPanel {

    private String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private JComboBox<String> monthField = new JComboBox<>(months);
    private JTextField dayField = new JTextField(5);

    // Written by Mathias Edvold s214973
    public ProjectDateChangePromptPanel() {
        super("Year");
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Month"));
        this.add(monthField);
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Day"));
        this.add(dayField);
    }

    // Written by Mathias Edvold s214973
    public int getYear(){
        return Integer.parseInt(super.getInput());
    }

    // Written by Mathias Edvold s214973
    public int getMonth(){
        return monthField.getSelectedIndex()+1;
    }

    // Written by Mathias Edvold s214973
    public int getDay(){
        return Integer.parseInt(dayField.getText());
    }

    // Written by Mathias Edvold s214973
    public void clear(){
        super.clear();
        dayField.setText("");
        monthField.setSelectedIndex(0);
    }
}
