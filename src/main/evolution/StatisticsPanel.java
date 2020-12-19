package evolution;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private RectangularBiomesMap map;

    private JLabel title;
    private JLabel[] statisticsLabel;

    private int stringLength;
    private int stringHeight;

    public StatisticsPanel(RectangularBiomesMap map) {
        this.map = map;

        this.stringLength = 400;
        this.stringHeight = 30;

        this.setLayout(null);

        this.statisticsLabel = new JLabel[18];
        for(int i = 0; i < 18; i++) {
            this.statisticsLabel[i] = new JLabel();
            this.add(this.statisticsLabel[i]);
            this.statisticsLabel[i].setFont(new Font("Calibri", Font.PLAIN, 20));
        }

        this.title = new JLabel();
        this.add(this.title);
        this.title.setLocation(0,0);
        this.title.setSize(600, 50);
        this.title.setText("Animal stats");
        this.title.setFont(new Font("Calibri", Font.PLAIN, 25));
        this.title.setHorizontalAlignment(JLabel.CENTER);

        this.statisticsLabel[0].setBounds(30, 60, this.stringLength, this.stringHeight);
        this.statisticsLabel[0].setText("After day: ");

        this.statisticsLabel[1].setBounds(30, 95, this.stringLength, this.stringHeight);
        this.statisticsLabel[1].setText("Animals number: ");

        this.statisticsLabel[2].setBounds(30, 130, this.stringLength, this.stringHeight);
        this.statisticsLabel[2].setText("Grasses in jungle: ");

        this.statisticsLabel[3].setBounds(30, 165, this.stringLength, this.stringHeight);
        this.statisticsLabel[3].setText("Grasses in savanna: ");

        this.statisticsLabel[4].setBounds(30, 200, this.stringLength, this.stringHeight);
        this.statisticsLabel[4].setText("Average live long for dead animal: ");

        this.statisticsLabel[5].setBounds(30, 235, this.stringLength, this.stringHeight);
        this.statisticsLabel[5].setText("Average energy per living animal: ");

        this.statisticsLabel[6].setBounds(30, 270, this.stringLength, this.stringHeight);
        this.statisticsLabel[6].setText("Average number of children per living animal: ");

        this.statisticsLabel[7].setBounds(30, 305, 210, this.stringHeight);
        this.statisticsLabel[7].setText("Most common genotype: ");

        this.statisticsLabel[8].setBounds(30, 340, 350, this.stringHeight);
        this.statisticsLabel[8].setText("Number of most common genotype: ");

        this.statisticsLabel[9].setBounds(430, 60, 200, this.stringHeight);
        this.statisticsLabel[10].setBounds(430, 95, 200, this.stringHeight);
        this.statisticsLabel[11].setBounds(430, 130, 200, this.stringHeight);
        this.statisticsLabel[12].setBounds(430, 165, 200, this.stringHeight);
        this.statisticsLabel[13].setBounds(430, 200, 200, this.stringHeight);
        this.statisticsLabel[14].setBounds(430, 235, 200, this.stringHeight);
        this.statisticsLabel[15].setBounds(430, 270, 200, this.stringHeight);
        this.statisticsLabel[16].setBounds(240, 305, 390, this.stringHeight);
        this.statisticsLabel[17].setBounds(430, 340, 200, this.stringHeight);
    }

    public void updateStatistics() {
        this.statisticsLabel[9].setText(String.valueOf(this.map.statistics.numberOfDay));

        this.statisticsLabel[10].setText(String.valueOf(this.map.statistics.numberOfAnimals));

        this.statisticsLabel[11].setText(String.valueOf(this.map.statistics.numberOfGrassesInJungle));

        this.statisticsLabel[12].setText(String.valueOf(this.map.statistics.numberOfGrassesInSavanna));

        if(this.map.statistics.averageLiveLongForDeadAnimal > -1)
            this.statisticsLabel[13].setText(String.valueOf(this.map.statistics.averageLiveLongForDeadAnimal));
        else
            this.statisticsLabel[13].setText("-");

        this.statisticsLabel[14].setText(String.valueOf(this.map.statistics.averageEnergyPerLivingAnimal));

        this.statisticsLabel[15].setText(String.valueOf(this.map.statistics.averageNumberOfChildrenPerLivingAnimal));

        this.statisticsLabel[16].setText(this.map.statistics.mostCommonGenotype);

        this.statisticsLabel[17].setText(String.valueOf(this.map.statistics.numberOfMostCommonGenotype));
    }
}
