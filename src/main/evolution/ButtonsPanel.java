package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel implements ActionListener {
    private IMediate mediator;
    private SimulationEngine engine;

    private JButton stopButton;
    private JButton startButton;
    private JButton highlightMostCommonGenesButton;
    private JButton unselectAnimalButton;
    private JButton setDelayButton;

    private JTextField delayTextField;
    private JLabel delayLabel;

    public ButtonsPanel(IMediate mediator, SimulationEngine engine) {
        this.mediator = mediator;
        this.engine = engine;

        this.setLayout(null);

        this.stopButton = new JButton("STOP");
        this.stopButton.setSize(110, 40);
        this.stopButton.setLocation(50, 0);
        this.stopButton.addActionListener(this);

        this.startButton = new JButton("START");
        this.startButton.setSize(110, 40);
        this.startButton.setLocation(180, 0);
        this.startButton.addActionListener(this);

        this.highlightMostCommonGenesButton = new JButton("HIGHLIGHT");
        this.highlightMostCommonGenesButton.setLocation(310, 0);
        this.highlightMostCommonGenesButton.setSize(110, 40);
        this.highlightMostCommonGenesButton.addActionListener(this);

        this.unselectAnimalButton = new JButton("UNSELECT");
        this.unselectAnimalButton.setLocation(440, 0);
        this.unselectAnimalButton.setSize(110, 40);
        this.unselectAnimalButton.addActionListener(this);

        this.delayLabel = new JLabel("Set timer delay [ms]: ");
        this.delayLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.delayLabel.setLocation(50, 50);
        this.delayLabel.setSize(180, 40);

        this.delayTextField = new JTextField("");
        this.delayTextField.setLocation(240, 50);
        this.delayTextField.setSize(160,40);

        this.setDelayButton = new JButton("SET DELAY");
        this.setDelayButton.setLocation(410, 50);
        this.setDelayButton.setSize(140, 40);
        this.setDelayButton.addActionListener(this);

        this.add(this.stopButton);
        this.add(this.startButton);
        this.add(this.highlightMostCommonGenesButton);
        this.add(this.unselectAnimalButton);
        this.add(this.delayLabel);
        this.add(this.delayTextField);
        this.add(this.setDelayButton);
    }

    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.stopButton)
            this.mediator.notifyMediator(this, "stop clicked");

        if(e.getSource() == this.startButton)
            this.mediator.notifyMediator(this, "start clicked");

        if(e.getSource() == this.highlightMostCommonGenesButton)
            this.mediator.notifyMediator(this, "highlight clicked");

        if(e.getSource() == this.unselectAnimalButton) {
            this.mediator.notifyMediator(this, "unselect clicked");
        }

        if(e.getSource() == this.setDelayButton) {
            if(this.checkValidity()) {
                this.engine.setDelay(Integer.parseInt(this.delayTextField.getText()));
                this.delayTextField.setText("");
            }
        }
    }

    private boolean checkValidity() {
        String delayString = this.delayTextField.getText();
        if(delayString.length() < 1 || delayString.charAt(0) < 49 || delayString.charAt(0) > 57)
            return false;
        for(int i = 1; i < delayString.length(); i++) {
            if(delayString.charAt(i) < 48 || delayString.charAt(i) > 57)
                return false;
        }
        return true;
    }
}
