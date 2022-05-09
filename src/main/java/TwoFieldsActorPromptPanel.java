import javax.swing.*;

// Written by Bjarke Bak Jensen s214957
public class TwoFieldsActorPromptPanel extends PromptPanel {

    private JTextField actorField = new JTextField(5);

    // Written by Bjarke Bak Jensen s214957
    public TwoFieldsActorPromptPanel(String firstField) {
        super(firstField);
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Your Initials"));
        this.add(actorField);
    }

    // Written by Bjarke Bak Jensen s214957
    public String getFirstField(){
        return super.getInput();
    }

    // Written by Bjarke Bak Jensen s214957
    public String getActorName(){
        return actorField.getText();
    }

    // Written by Bjarke Bak Jensen s214957
    public void clear(){
        super.clear();
        actorField.setText("");
    }
}
