package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SimulationWindow extends JPanel implements IMediate, WindowListener {
    private final JFrame frame;

    private final SimulationEngine engine;

    private final RenderMapPanel renderMapPanel;
    private final StatisticsPanel statisticsPanel;
    private final FollowedAnimalStatsPanel followedAnimalStatsPanel;
    private final ButtonsPanel buttonsPanel;

    private final int windowWidth = 1400;
    private final int windowHeight = 760;

    public SimulationWindow(SimulationEngine engine, RectangularBiomesMap map, int width, int height, int animalsStartEnergy) {
        this.engine = engine;

        this.renderMapPanel = new RenderMapPanel(this, map, width, height, animalsStartEnergy);
        this.renderMapPanel.setSize(new Dimension(1, 1));

        this.buttonsPanel = new ButtonsPanel(this);
        this.buttonsPanel.setLocation(0, 660);
        this.buttonsPanel.setSize(600, 100);

        this.followedAnimalStatsPanel = new FollowedAnimalStatsPanel(this);
        this.followedAnimalStatsPanel.setLocation(0, 400);
        this.followedAnimalStatsPanel.setSize(600, 260);
        map.addObserver(this.followedAnimalStatsPanel);

        this.statisticsPanel = new StatisticsPanel(map);
        this.statisticsPanel.setLocation(0,0);
        this.statisticsPanel.setSize(600, 400);

        ImageIcon logoIcon = new ImageIcon("images\\lew.jpg");

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLayout(null);
        frame.addWindowListener(this);
        frame.setIconImage(logoIcon.getImage());

        this.setLayout(null);

        this.add(this.renderMapPanel);
        this.add(this.statisticsPanel);
        this.add(this.buttonsPanel);
        this.add(this.followedAnimalStatsPanel);

        frame.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(this.windowWidth, this.windowHeight);
    }

    public void update() {
        this.renderMapPanel.repaint();
        this.statisticsPanel.updateStatistics();
        this.followedAnimalStatsPanel.updateAnimalStats(false);
    }

    public void notifyMediator(Object sender, String event) {
        if(sender instanceof ButtonsPanel) {
            if(event.equals("stop clicked")) {
                this.engine.stopTimer();
            }
            else if(event.equals("start clicked")) {
                this.renderMapPanel.hideAnimalsToHighlight();
                this.engine.run();
            }
            else if(event.equals("highlight clicked")) {
                if(this.engine.isTimerStopped()) {
                    this.renderMapPanel.hideSelectedAnimal();
                    this.renderMapPanel.showAnimalsToHighlight();

                    this.renderMapPanel.setAnimalsToHighlight(this.engine.getAnimalsToHighlight());
                    this.followedAnimalStatsPanel.animalPicked(null);
                    this.engine.turnOffMarkers(); //turnOff always after unselecting animal (when selected markers are turned off)

                    this.renderMapPanel.repaint();
                }
            }
            else if(event.equals("unselect clicked")) {
                this.renderMapPanel.hideSelectedAnimal();

                this.followedAnimalStatsPanel.animalPicked(null);
                this.engine.turnOffMarkers();

                this.renderMapPanel.repaint();
            }
            else if(event.equals("set delay")) {
                this.engine.setDelay(this.buttonsPanel.getDelay());
            }
        }
        else if(sender instanceof RenderMapPanel) {
            if(event.equals("animal selected")) {
                this.renderMapPanel.hideAnimalsToHighlight();
                this.renderMapPanel.showSelectedAnimal();

                this.engine.turnOffMarkers();
                this.followedAnimalStatsPanel.setDayWhenAnimalPicked(this.engine.getDay());
                this.followedAnimalStatsPanel.animalPicked(this.renderMapPanel.getSelectedAnimal());
            }
        }
        else if(sender instanceof FollowedAnimalStatsPanel) {
            if(event.equals("selected animal died")) {
                this.renderMapPanel.hideSelectedAnimal();
                this.renderMapPanel.repaint();
                this.followedAnimalStatsPanel.setDayWhenAnimalDied(this.engine.getDay());
            }
        }
    }

    @Override public void windowClosing(WindowEvent e) {
        this.engine.stopTimer();
        this.engine.saveStatsToFile();
    }
    @Override public void windowOpened(WindowEvent e) {}
    @Override public void windowClosed(WindowEvent e) {}
    @Override public void windowIconified(WindowEvent e) {}
    @Override public void windowDeiconified(WindowEvent e) {}
    @Override public void windowActivated(WindowEvent e) {}
    @Override public void windowDeactivated(WindowEvent e) {}
}
