import javax.swing.*;

public class TwoFieldsActorPromptPanel extends PromptPanel {

    private JTextField actorField = new JTextField(5);

    public TwoFieldsActorPromptPanel(String firstField) {
        super(firstField);
        this.add(Box.createHorizontalStrut(20));
        this.add(new JLabel("Your Initials"));
        this.add(actorField);
    }

    public String getFirstField(){
        return super.getInput();
    }

    public String getActorName(){
        return actorField.getText();
    }

    public void clear(){
        super.clear();
        actorField.setText("");
    }
}
