package evolution;

import javax.swing.*;
import java.awt.*;

public class SimulationWindow extends JPanel implements IMediate{
    private JFrame frame;

    private SimulationEngine engine;

    private RenderPanel renderPanel;
    private StatisticsPanel statisticsPanel;
    private ButtonsPanel buttonsPanel;
    private FollowedAnimalPanel followedAnimalPanel;

    private int windowWidth = 1400;
    private int windowHeight = 760;

    public SimulationWindow(SimulationEngine engine, RectangularBiomesMap map, int width, int height, int animalsStartEnergy) {
        this.engine = engine;
        this.renderPanel = new RenderPanel(this, map, width, height, animalsStartEnergy);
        this.renderPanel.setSize(new Dimension(1, 1));

        this.statisticsPanel = new StatisticsPanel(map);
        this.statisticsPanel.setLocation(0,0);
        this.statisticsPanel.setSize(600, 400);

        this.buttonsPanel = new ButtonsPanel(this, map);
        this.buttonsPanel.setLocation(0, 400);
        this.buttonsPanel.setSize(600, 60);

        this.followedAnimalPanel = new FollowedAnimalPanel(this, map);
        this.followedAnimalPanel.setLocation(0, 460);
        this.followedAnimalPanel.setSize(600, 300);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLayout(null);
        this.setLayout(null);

        this.add(this.renderPanel);
        this.add(this.statisticsPanel);
        this.add(this.buttonsPanel);
        this.add(this.followedAnimalPanel);

        frame.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(this.windowWidth, this.windowHeight);
    }

    public void update() {
        this.renderPanel.repaint();
        this.statisticsPanel.updateStatistics();
    }

    public void notifyMediator(Object sender, String event) {
        if(sender instanceof FollowedAnimalPanel && event == "highlight clicked") {
            if(this.engine.isTimerStopped()) {
                this.renderPanel.animalsToHighlight = this.engine.giveAnimalsWithMostCommonGenotype();
                this.renderPanel.repaint();
            }
        }

        if(sender instanceof  ButtonsPanel) {
            if(event == "stop clicked") {
                this.engine.stopTimer();
            }
            else if(event == "start clicked") {
                this.renderPanel.selectedAnimal = null;
                this.renderPanel.animalsToHighlight = null;
                this.followedAnimalPanel.showSelectedAnimal("");
                this.engine.run();
            }
        }

        if(sender instanceof RenderPanel && event == "animal selected") {
            if(this.engine.isTimerStopped()) {
                this.followedAnimalPanel.showSelectedAnimal(this.renderPanel.selectedAnimal.getGenotype());
            }
        }
    }
}
