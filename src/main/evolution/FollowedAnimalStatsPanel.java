package evolution;

import javax.swing.*;
import java.awt.*;

public class FollowedAnimalStatsPanel extends JPanel {
    private SimulationEngine engine;
    private SelectedAnimal selectedAnimal;

    private JLabel panelTitleLabel;
    private JLabel[] statisticsLabels;

    public FollowedAnimalStatsPanel(SimulationEngine engine, SelectedAnimal selectedAnimal) {
        this.engine = engine;
        this.selectedAnimal = selectedAnimal;

        this.setLabels();
        this.setVoidAnimalStats();

        this.setLayout(null);
    }

    private void setLabels() {
        this.panelTitleLabel = new JLabel("Selected animal stats");
        this.panelTitleLabel.setFont(new Font("Calibri", Font.PLAIN, 25));
        this.panelTitleLabel.setLocation(0, 0);
        this.panelTitleLabel.setSize(600, 30);
        this.panelTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(this.panelTitleLabel);

        this.statisticsLabels = new JLabel[14];
        for(int i=0; i<14; i++) {
            this.statisticsLabels[i] = new JLabel();
            this.add(this.statisticsLabels[i]);
            this.statisticsLabels[i].setFont(new Font("Calibri", Font.PLAIN, 18));
        }

        this.statisticsLabels[0].setLocation(30, 40);
        this.statisticsLabels[0].setSize(200, 50);
        this.statisticsLabels[0].setText("Energy: ");

        this.statisticsLabels[1].setLocation(30, 90);
        this.statisticsLabels[1].setSize(200, 50);
        this.statisticsLabels[1].setText("Lived days in total: ");

        this.statisticsLabels[2].setLocation(30, 140);
        this.statisticsLabels[2].setSize(200, 50);
        this.statisticsLabels[2].setText("<html>Number of children<br/>in total:<html> ");

        this.statisticsLabels[3].setLocation(30, 190);
        this.statisticsLabels[3].setSize(100, 50);
        this.statisticsLabels[3].setText("Genotype: ");

        this.statisticsLabels[4].setLocation(350, 40);
        this.statisticsLabels[4].setSize(200, 50);
        this.statisticsLabels[4].setText("Picked day: ");

        this.statisticsLabels[5].setLocation(350, 90);
        this.statisticsLabels[5].setSize(200, 50);
        this.statisticsLabels[5].setText("<html>Number of children<br/>since picked:</html>");

        this.statisticsLabels[6].setLocation(350, 140);
        this.statisticsLabels[6].setSize(200, 50);
        this.statisticsLabels[6].setText("<html>Number of descendants<br/>since picked:</html>");


        this.statisticsLabels[7].setLocation(230, 40);
        this.statisticsLabels[7].setSize(100, 50);

        this.statisticsLabels[8].setLocation(230, 90);
        this.statisticsLabels[8].setSize(100, 50);

        this.statisticsLabels[9].setLocation(230, 140);
        this.statisticsLabels[9].setSize(100, 50);

        this.statisticsLabels[10].setLocation(130, 190);
        this.statisticsLabels[10].setSize(400, 40);

        this.statisticsLabels[11].setLocation(550, 40);
        this.statisticsLabels[11].setSize(50, 50);

        this.statisticsLabels[12].setLocation(550, 90);
        this.statisticsLabels[12].setSize(50, 50);

        this.statisticsLabels[13].setLocation(550, 140);
        this.statisticsLabels[13].setSize(50, 50);
    }

    public void updateAnimalStats() {
//        if(this.engine.animalSelected.getEnergy() == 0) {
//            this.statisticsLabels[0].setText("Dead day: ");
//            this.statisticsLabels[7].setText(String.valueOf(this.engine.dayWhenDead));
//        }
//        else {
//            this.statisticsLabels[0].setText("Energy: ");
//            this.statisticsLabels[7].setText(String.valueOf(this.engine.animalSelected.getEnergy()));
//        }
//
//        this.statisticsLabels[8].setText(String.valueOf(this.engine.animalSelected.getLivedDays()));
//        this.statisticsLabels[9].setText(String.valueOf(this.engine.animalSelected.getNumberOfChildren()));
//        this.statisticsLabels[10].setText(String.valueOf(this.engine.animalSelected.getGenotype()));
//
//        this.statisticsLabels[11].setText(String.valueOf(this.engine.dayWhenPicked));
//        this.statisticsLabels[12].setText(String.valueOf(this.engine.animalSelected.getNumberOfChildren() - this.engine.numberOfChildrenWhenPicked));
//        this.statisticsLabels[13].setText(String.valueOf(this.engine.numberOfDescendants));
        if(this.selectedAnimal.animal.getEnergy() == 0) {
            this.statisticsLabels[0].setText("Dead day: ");
            this.statisticsLabels[7].setText(String.valueOf(this.selectedAnimal.dayWhenDead));
        }
        else {
            this.statisticsLabels[0].setText("Energy: ");
            this.statisticsLabels[7].setText(String.valueOf(this.selectedAnimal.animal.getEnergy()));
        }

        this.statisticsLabels[8].setText(String.valueOf(this.selectedAnimal.animal.getLivedDays()));
        this.statisticsLabels[9].setText(String.valueOf(this.selectedAnimal.animal.getNumberOfChildren()));
        this.statisticsLabels[10].setText(String.valueOf(this.selectedAnimal.animal.getGenotype()));

        this.statisticsLabels[11].setText(String.valueOf(this.selectedAnimal.dayWhenPicked));
        this.statisticsLabels[12].setText(String.valueOf(this.selectedAnimal.animal.getNumberOfChildren() - this.selectedAnimal.numberOfChildrenWhenPicked));
        this.statisticsLabels[13].setText(String.valueOf(this.selectedAnimal.numberOfDescendants));
    }

    public void setVoidAnimalStats() {
        for(int i = 7; i < 14; i++)
            this.statisticsLabels[i].setText("-");
    }
}

