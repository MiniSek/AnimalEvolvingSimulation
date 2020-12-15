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

        this.statisticsLabel = new JLabel[16];
        for(int i = 0; i < 16; i++) {
            this.statisticsLabel[i] = new JLabel();
            this.add(this.statisticsLabel[i]);
            this.statisticsLabel[i].setFont(new Font("Calibri", Font.PLAIN, 20));
        }

        this.title = new JLabel();
        this.add(this.title);
        this.title.setLocation(0,0);
        this.title.setSize(600, 50);
        this.title.setText("Stats");
        this.title.setFont(new Font("Calibri", Font.PLAIN, 25));
        this.title.setHorizontalAlignment(JLabel.CENTER);

        this.statisticsLabel[0].setBounds(30, 60, this.stringLength, this.stringHeight);
        this.statisticsLabel[0].setText("Day: ");

        this.statisticsLabel[1].setBounds(30, 100, this.stringLength, this.stringHeight);
        this.statisticsLabel[1].setText("Animals number: ");

        this.statisticsLabel[2].setBounds(30, 140, this.stringLength, this.stringHeight);
        this.statisticsLabel[2].setText("Grasses in jungle: ");

        this.statisticsLabel[3].setBounds(30, 180, this.stringLength, this.stringHeight);
        this.statisticsLabel[3].setText("Grasses in savanna: ");

        this.statisticsLabel[4].setBounds(30, 220, this.stringLength, this.stringHeight);
        this.statisticsLabel[4].setText("Average live long for dead animal: ");

        this.statisticsLabel[5].setBounds(30, 260, this.stringLength, this.stringHeight);
        this.statisticsLabel[5].setText("Average energy per living animal: ");

        this.statisticsLabel[6].setBounds(30, 300, this.stringLength, this.stringHeight);
        this.statisticsLabel[6].setText("Average number of children per living animal: ");

        this.statisticsLabel[7].setBounds(30, 340, 210, this.stringHeight);
        this.statisticsLabel[7].setText("Most common genotype: ");

        this.statisticsLabel[8].setBounds(430, 60, 200, this.stringHeight);
        this.statisticsLabel[9].setBounds(430, 100, 200, this.stringHeight);
        this.statisticsLabel[10].setBounds(430, 140, 200, this.stringHeight);
        this.statisticsLabel[11].setBounds(430, 180, 200, this.stringHeight);
        this.statisticsLabel[12].setBounds(430, 220, 200, this.stringHeight);
        this.statisticsLabel[13].setBounds(430, 260, 200, this.stringHeight);
        this.statisticsLabel[14].setBounds(430, 300, 200, this.stringHeight);
        this.statisticsLabel[15].setBounds(240, 340, 390, this.stringHeight);
    }

    public void updateStatistics() {
        this.statisticsLabel[8].setText(String.valueOf(this.map.statistics.numberOfDay));

        this.statisticsLabel[9].setText(String.valueOf(this.map.statistics.numberOfAnimals));

        this.statisticsLabel[10].setText(String.valueOf(this.map.statistics.numberOfGrassesInJungle));

        this.statisticsLabel[11].setText(String.valueOf(this.map.statistics.numberOfGrassesInSavanna));

        this.statisticsLabel[12].setText(String.valueOf(this.map.statistics.averageLiveLongForDeadAnimal));

        this.statisticsLabel[13].setText(String.valueOf(this.map.statistics.averageEnergyPerLivingAnimal));

        this.statisticsLabel[14].setText(String.valueOf(this.map.statistics.averageNumberOfChildrenPerLivingAnimal));

        this.statisticsLabel[15].setText(this.map.statistics.mostCommonGenotype);
    }
}
