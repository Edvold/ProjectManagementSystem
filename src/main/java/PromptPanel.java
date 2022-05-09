import javax.swing.*;

// Written by Bjarke Bak Jensen s214957
public class PromptPanel extends JPanel {

    // Written by Bjarke Bak Jensen s214957
    private JTextField inputField = new JTextField(5);

    // Written by Bjarke Bak Jensen s214957
    public PromptPanel(String prompt){
        this.add(new JLabel(prompt + ":"));
        this.add(inputField);
    }

    // Written by Bjarke Bak Jensen s214957
    public String getInput(){
        return inputField.getText();
    }

    // Written by Bjarke Bak Jensen s214957
    public void clear(){
        inputField.setText("");
    }
}
