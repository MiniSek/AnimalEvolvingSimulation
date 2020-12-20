package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SimulationWindow extends JPanel implements IMediate, WindowListener {
    private final JFrame frame;
    private final SimulationEngine engine;
    private SelectedAnimal selectedAnimal;

    private final RenderMapPanel renderMapPanel;
    private final StatisticsPanel statisticsPanel;
    private final FollowedAnimalStatsPanel followedAnimalStatsPanel;
    private final ButtonsPanel buttonsPanel;

    private final int windowWidth = 1400;
    private final int windowHeight = 760;

    public SimulationWindow(SimulationEngine engine, RectangularBiomesMap map, int width, int height, int animalsStartEnergy, SelectedAnimal selectedAnimal) {
        this.engine = engine;
        this.selectedAnimal = selectedAnimal;

        this.renderMapPanel = new RenderMapPanel(this, engine, map, selectedAnimal, width, height, animalsStartEnergy);
        this.renderMapPanel.setSize(new Dimension(1, 1));

        this.buttonsPanel = new ButtonsPanel(this, engine);
        this.buttonsPanel.setLocation(0, 660);
        this.buttonsPanel.setSize(600, 100);

        this.followedAnimalStatsPanel = new FollowedAnimalStatsPanel(engine, selectedAnimal);
        this.followedAnimalStatsPanel.setLocation(0, 400);
        this.followedAnimalStatsPanel.setSize(600, 260);

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
//        if(this.engine.animalSelected != null)
//            this.followedAnimalStatsPanel.updateAnimalStats();
        if(!this.selectedAnimal.isNull())
            this.followedAnimalStatsPanel.updateAnimalStats();
    }

    public void notifyMediator(Object sender, String event) {
        if(sender instanceof ButtonsPanel) {
            if(event.equals("stop clicked")) {
                this.engine.stopTimer();
            }
            else if(event.equals("start clicked")) {
                this.engine.clearAnimalsToHighlight();
                this.engine.run();
            }
            else if(event.equals("highlight clicked")) {
                if(this.engine.isTimerStopped()) {
//                    this.engine.animalSelectedUnselected();
                    this.selectedAnimal.animalSelectedUnselected();
                    this.followedAnimalStatsPanel.setVoidAnimalStats();

                    this.engine.setAnimalsToHighlight();
                    this.renderMapPanel.repaint();
                }
            }
            else if(event.equals("unselect clicked")) {
//                this.engine.animalSelectedUnselected();
                this.selectedAnimal.animalSelectedUnselected();
                this.followedAnimalStatsPanel.setVoidAnimalStats();
                this.renderMapPanel.repaint();
            }
        }
        else if(sender instanceof RenderMapPanel) {
            if(event.equals("animal will be selected")) {
                this.engine.clearAnimalsToHighlight();
//                this.engine.animalSelectedUnselected();
                this.selectedAnimal.animalSelectedUnselected();
            }
            else if(event.equals("animal selected")) {
//                this.engine.animalPicked();
                this.selectedAnimal.animalPicked();
                this.followedAnimalStatsPanel.updateAnimalStats();
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
