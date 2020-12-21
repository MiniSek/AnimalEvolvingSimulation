package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametersInformWindow extends JPanel implements ActionListener {
    private final JButton startSimulation;

    private int mapWidth, mapHeight, animalsAtStart, animalsStartEnergy, moveEnergy, grassEnergy;
    private double jungleRatio;

    private JLabel[] parametersLabels;

    public ParametersInformWindow(String width, String height, String jungleRatio, String numberOfAnimals, String startEnergy, String moveEnergy, String grassEnergy) {
        this.startSimulation = new JButton("Start simulations");
        this.startSimulation.setBounds(100, 420, 300, 40);
        this.startSimulation.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.startSimulation.setForeground(Color.BLACK);
        this.startSimulation.setVerticalTextPosition(JButton.CENTER);
        this.startSimulation.setFocusable(false);
        this.startSimulation.setBackground(Color.green);
        this.startSimulation.addActionListener(this);

        ImageIcon logoIcon = new ImageIcon("images\\lew.jpg");

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationByPlatform(true);
        frame.setIconImage(logoIcon.getImage());

        this.setLayout(null);
        this.add(this.startSimulation);

        this.setLabels(width, height, jungleRatio, numberOfAnimals, startEnergy, moveEnergy, grassEnergy);
        this.getData(width, height, jungleRatio, numberOfAnimals, startEnergy, moveEnergy, grassEnergy);

        frame.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    //start simulation can run many separate simulations
    @Override public void actionPerformed(ActionEvent e) {
        SimulationEngine engine = new SimulationEngine(this.mapWidth, this.mapHeight, this.animalsStartEnergy, this.moveEnergy, this.grassEnergy, this.jungleRatio, this.animalsAtStart);
        engine.run();
    }

    private void getData(String width, String height, String jungleRatio, String numberOfAnimals, String startEnergy, String moveEnergy, String grassEnergy) {
        this.mapWidth = Integer.parseInt(width);
        this.mapHeight = Integer.parseInt(height);
        this.jungleRatio = Double.parseDouble(jungleRatio);
        this.animalsAtStart = Integer.parseInt(numberOfAnimals);
        this.animalsStartEnergy = Integer.parseInt(startEnergy);
        this.moveEnergy = Integer.parseInt(moveEnergy);
        this.grassEnergy = Integer.parseInt(grassEnergy);
    }

    private void setLabels(String width, String height, String jungleRatio, String numberOfAnimals, String startEnergy, String moveEnergy, String grassEnergy) {
        this.parametersLabels = new JLabel[14];
        for(int i = 0 ; i < 14; i++) {
            this.parametersLabels[i] = new JLabel();
            this.parametersLabels[i].setFont(new Font("Calibri", Font.PLAIN, 20));
            this.add(this.parametersLabels[i]);
        }
        for(int i = 0; i < 7; i++)
            this.parametersLabels[i].setBounds(70,40 + 50*i, 300, 30);

        for(int i = 7; i < 14; i++) {
            this.parametersLabels[i].setBounds(370, 40 + (i-7)*50, 50, 30);
        }

        this.parametersLabels[0].setText("Map width: ");
        this.parametersLabels[1].setText("Map height: ");
        this.parametersLabels[2].setText("Jungle ratio: ");
        this.parametersLabels[3].setText("Number of animals at start: ");
        this.parametersLabels[4].setText("Animals start energy: ");
        this.parametersLabels[5].setText("Energy used by animal per day: ");
        this.parametersLabels[6].setText("Grass energy: ");

        this.parametersLabels[7].setText(width);
        this.parametersLabels[8].setText(height);
        this.parametersLabels[9].setText(jungleRatio);
        this.parametersLabels[10].setText(numberOfAnimals);
        this.parametersLabels[11].setText(startEnergy);
        this.parametersLabels[12].setText(moveEnergy);
        this.parametersLabels[13].setText(grassEnergy);
    }
}
