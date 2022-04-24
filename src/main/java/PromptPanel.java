import javax.swing.*;

public class PromptPanel extends JPanel {

    private JTextField inputField = new JTextField(5);

    public PromptPanel(String prompt){
        this.add(new JLabel(prompt + ":"));
        this.add(inputField);
    }

    public String getInput(){
        return inputField.getText();
    }

    public void clear(){
        inputField.setText("");
    }
}
