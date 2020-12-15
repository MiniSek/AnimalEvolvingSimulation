package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataInputWindow extends JPanel implements ActionListener {
    private JFrame inputWindow;
    private JButton startSimulations;

    private JLabel mapWidthLabel;
    private JLabel mapHeightLabel;
    private JLabel jungleRatioLabel;
    private JLabel animalsNumberAtStartLabel;
    private JLabel animalsStartEnergyLabel;
    private JLabel animalsMoveEnergyLabel;
    private JLabel grassEnergyLabel;

    private int mapWidth, mapHeight, animalsAtStart, animalsStartEnergy, moveEnergy, grassEnergy;
    private double jungleRatio;

    private JTextField[] dataFields;

    public DataInputWindow() {
        this.startSimulations = new JButton("Start simulations");
        this.startSimulations.setBounds(100, 420, 300, 40);
        this.startSimulations.setFont(new Font("Calibri", Font.PLAIN, 20));
        this.startSimulations.setForeground(Color.BLACK);
        this.startSimulations.setVerticalTextPosition(JButton.CENTER);
        this.startSimulations.setFocusable(false);
        this.startSimulations.setBackground(Color.green);
        this.startSimulations.addActionListener(this);


        this.inputWindow = new JFrame();
        this.inputWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.inputWindow.getContentPane().add(this);
        this.inputWindow.pack();
        this.inputWindow.setLayout(null);
        this.inputWindow.setResizable(false);
        this.inputWindow.setLocationByPlatform(true);

        this.setLayout(null);
        this.add(this.startSimulations);

        this.setLabels();
        this.setTextFields();

        this.inputWindow.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override public void actionPerformed(ActionEvent e) {
        if(this.validateData()) {
            this.getData();
            IEngine engine = new SimulationEngine(this.mapWidth, this.mapHeight, this.animalsStartEnergy, this.moveEnergy, this.grassEnergy, this.jungleRatio, this.animalsAtStart);
            engine.run();
        }
    }

    private void getData() {
        this.mapWidth = Integer.parseInt(this.dataFields[0].getText());
        this.mapHeight = Integer.parseInt(this.dataFields[1].getText());
        this.jungleRatio = Double.parseDouble(this.dataFields[2].getText());
        this.animalsAtStart = Integer.parseInt(this.dataFields[3].getText());
        this.animalsStartEnergy = Integer.parseInt(this.dataFields[4].getText());
        this.moveEnergy = Integer.parseInt(this.dataFields[5].getText());
        this.grassEnergy = Integer.parseInt(this.dataFields[6].getText());
    }

    private boolean validateData() {
        for(int i = 0; i < 7; i++) {
            if(i != 2) {
                String data = this.dataFields[i].getText();
                if(data.charAt(0) == 48) {
                    this.dataFields[i].setBackground(Color.red);
                    return false;
                }
                for (int j = 0; j < data.length(); j++) {
                    if(data.charAt(j) < 48 || data.charAt(j) > 57) {
                        this.dataFields[i].setBackground(Color.red);
                        return false;
                    }
                }
                this.dataFields[i].setBackground(Color.white);
            }
            else {
                String data = this.dataFields[i].getText();
                if ((data.charAt(0) != 48 && data.charAt(0) != 49) || (data.length() > 1 && (data.charAt(1) != 46 && data.charAt(1) != 44))) {
                    this.dataFields[i].setBackground(Color.red);
                    return false;
                }
                for (int j = 2; j < data.length(); j++) {
                    if(data.charAt(j) < 48 || data.charAt(j) > 57) {
                        this.dataFields[i].setBackground(Color.red);
                        return false;
                    }
                }
                this.dataFields[i].setBackground(Color.white);
            }
        }
        return true;
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

    private void setTextFields() {
        this.dataFields = new JTextField[7];

        for(int i = 0; i < 7; i++) {
            this.dataFields[i] = new JTextField();
            this.dataFields[i].setBounds(370, 40 + i*50, 50, 30);
            this.dataFields[i].setText("");
            this.add(this.dataFields[i]);
        }
        this.dataFields[0].setText("50");
        this.dataFields[1].setText("50");
        this.dataFields[2].setText("0.3");
        this.dataFields[3].setText("60");
        this.dataFields[4].setText("50");
        this.dataFields[5].setText("1");
        this.dataFields[6].setText("40");
    }

}