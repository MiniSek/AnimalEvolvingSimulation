package evolution;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsPanel extends JPanel implements ActionListener {
    private IMediate mediator;
    private RectangularBiomesMap map;

    private JButton stopButton;
    private JButton startButton;

    public ButtonsPanel(IMediate mediator, RectangularBiomesMap map) {
        this.mediator = mediator;
        this.map = map;

        this.setLayout(null);

        this.stopButton = new JButton("STOP");
        this.startButton = new JButton("START");

        this.stopButton.setSize(200, 40);
        this.stopButton.setLocation(50, 10);
        this.stopButton.addActionListener(this);

        this.startButton.setSize(200, 40);
        this.startButton.setLocation(350, 10);
        this.startButton.addActionListener(this);

        this.add(this.stopButton);
        this.add(this.startButton);
    }

    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.stopButton)
            this.mediator.notifyMediator(this, "stop clicked");

        if(e.getSource() == this.startButton)
            this.mediator.notifyMediator(this, "start clicked");
    }
}
