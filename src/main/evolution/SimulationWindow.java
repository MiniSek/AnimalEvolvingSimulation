package evolution;

import javax.swing.*;
import java.awt.*;

public class SimulationWindow extends JPanel {
    private JFrame frame;
    private MapRender mapRender;
    private MapStatistics mapStatistics;

    private int windowWidth = 1400;
    private int windowHeight = 760;

    public SimulationWindow(RectangularBiomesMap map, int width, int height) {
        this.mapRender = new MapRender(map, width, height);
        this.mapRender.setSize(new Dimension(1, 1));

        this.mapStatistics = new MapStatistics(map);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setLayout(null);
        this.setLayout(null);

        this.add(this.mapRender);
        this.add(this.mapStatistics);

        frame.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(this.windowWidth, this.windowHeight);
    }

    public void update() {
        this.mapRender.repaint();
        this.mapStatistics.updateStatistics();
    }
}
