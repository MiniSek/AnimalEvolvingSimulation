package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataInputWindow extends JPanel implements ActionListener {
    private final JButton startSimulation;

    private JLabel mapWidthLabel;
    private JLabel mapHeightLabel;
    private JLabel jungleRatioLabel;
    private JLabel animalsNumberAtStartLabel;
    private JLabel animalsStartEnergyLabel;
    private JLabel animalsMoveEnergyLabel;
    private JLabel grassEnergyLabel;

    private int mapWidth, mapHeight, animalsAtStart, animalsStartEnergy, moveEnergy, grassEnergy;
    private double jungleRatio;

    private JTextField[] dataTextFields;

    public DataInputWindow(String width, String height, String jungleRatio, String numberOfAnimals, String startEnergy, String moveEnergy, String grassEnergy) {
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

        this.setLabels();
        this.setTextFields(width, height, jungleRatio, numberOfAnimals, startEnergy, moveEnergy, grassEnergy);

        frame.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override public void actionPerformed(ActionEvent e) {
        if(this.validateData()) {
            this.getData();
            SimulationEngine engine = new SimulationEngine(this.mapWidth, this.mapHeight, this.animalsStartEnergy, this.moveEnergy, this.grassEnergy, this.jungleRatio, this.animalsAtStart);
            engine.run();
        }
    }

    private boolean validateData() {
        for(int i = 0; i < 7; i++) {
            if(i != 2) {
                String data = this.dataTextFields[i].getText();
                if(data.charAt(0) == 48) {
                    this.dataTextFields[i].setBackground(Color.red);
                    return false;
                }
                for (int j = 0; j < data.length(); j++) {
                    if(data.charAt(j) < 48 || data.charAt(j) > 57) {
                        this.dataTextFields[i].setBackground(Color.red);
                        return false;
                    }
                }
                this.dataTextFields[i].setBackground(Color.white);
            }
            else {
                String data = this.dataTextFields[i].getText();
                if ((data.charAt(0) != 48 && data.charAt(0) != 49) || (data.length() > 1 && (data.charAt(1) != 46 && data.charAt(1) != 44))) {
                    this.dataTextFields[i].setBackground(Color.red);
                    return false;
                }
                for (int j = 2; j < data.length(); j++) {
                    if(data.charAt(j) < 48 || data.charAt(j) > 57) {
                        this.dataTextFields[i].setBackground(Color.red);
                        return false;
                    }
                }
                this.dataTextFields[i].setBackground(Color.white);
            }
        }
        if(Integer.parseInt(this.dataTextFields[0].getText()) * Integer.parseInt(this.dataTextFields[1].getText()) <
                Integer.parseInt(this.dataTextFields[3].getText())){
            this.dataTextFields[3].setBackground(Color.red);
            return false;
        }
        return true;
    }

    private void getData() {
        this.mapWidth = Integer.parseInt(this.dataTextFields[0].getText());
        this.mapHeight = Integer.parseInt(this.dataTextFields[1].getText());
        this.jungleRatio = Double.parseDouble(this.dataTextFields[2].getText());
        this.animalsAtStart = Integer.parseInt(this.dataTextFields[3].getText());
        this.animalsStartEnergy = Integer.parseInt(this.dataTextFields[4].getText());
        this.moveEnergy = Integer.parseInt(this.dataTextFields[5].getText());
        this.grassEnergy = Integer.parseInt(this.dataTextFields[6].getText());
    }

    private void setLabels() {
        this.mapWidthLabel = new JLabel("Map width: ");
        this.mapWidthLabel.setBounds(70,40, 300, 30);
        this.mapWidthLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.mapWidthLabel);

        this.mapHeightLabel = new JLabel("Map height: ");
        this.mapHeightLabel.setBounds(70,90, 300, 30);
        this.mapHeightLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.mapHeightLabel);

        this.jungleRatioLabel = new JLabel("Jungle ratio: ");
        this.jungleRatioLabel.setBounds(70,140, 300, 30);
        this.jungleRatioLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.jungleRatioLabel);

        this.animalsNumberAtStartLabel = new JLabel("Number of animals at start: ");
        this.animalsNumberAtStartLabel.setBounds(70,190, 300, 30);
        this.animalsNumberAtStartLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.animalsNumberAtStartLabel);

        this.animalsStartEnergyLabel = new JLabel("Animals start energy: ");
        this.animalsStartEnergyLabel.setBounds(70,240, 300, 30);
        this.animalsStartEnergyLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.animalsStartEnergyLabel);

        this.animalsMoveEnergyLabel = new JLabel("Energy used by animal per day: ");
        this.animalsMoveEnergyLabel.setBounds(70,290, 300, 30);
        this.animalsMoveEnergyLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.animalsMoveEnergyLabel);

        this.grassEnergyLabel = new JLabel("Grass energy: ");
        this.grassEnergyLabel.setBounds(70,340, 300, 30);
        this.grassEnergyLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.add(this.grassEnergyLabel);
    }

    private void setTextFields(String width, String height, String jungleRatio, String numberOfAnimals, String startEnergy, String moveEnergy, String grassEnergy) {
        this.dataTextFields = new JTextField[7];

        for(int i = 0; i < 7; i++) {
            this.dataTextFields[i] = new JTextField();
            this.dataTextFields[i].setBounds(370, 40 + i*50, 50, 30);
            this.add(this.dataTextFields[i]);
        }

        this.dataTextFields[0].setText(width);
        this.dataTextFields[1].setText(height);
        this.dataTextFields[2].setText(jungleRatio);
        this.dataTextFields[3].setText(numberOfAnimals);
        this.dataTextFields[4].setText(startEnergy);
        this.dataTextFields[5].setText(moveEnergy);
        this.dataTextFields[6].setText(grassEnergy);
    }
}
