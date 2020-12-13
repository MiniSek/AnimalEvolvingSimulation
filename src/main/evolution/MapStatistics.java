package evolution;

import javax.swing.*;

public class MapStatistics extends JPanel {
    private RectangularBiomesMap map;
    private int panelWidth;
    private int panelHeight;

    private JLabel[] statisticsLabel;

    public MapStatistics(RectangularBiomesMap map) {
        this.map = map;

        this.panelWidth = 600;
        this.panelHeight = 500;
        this.setSize(this.panelWidth, this.panelHeight);
        this.setLocation(0,0);

        this.setLayout(null);

        this.statisticsLabel = new JLabel[16];
        for(int i = 0; i < 16; i++) {
            this.statisticsLabel[i] = new JLabel();
            this.add(this.statisticsLabel[i]);
        }

        this.statisticsLabel[0].setBounds(10, 10, 300, 30);
        this.statisticsLabel[0].setText("Day: ");

        this.statisticsLabel[1].setBounds(10, 50, 300, 30);
        this.statisticsLabel[1].setText("Animals number: ");

        this.statisticsLabel[2].setBounds(10, 90, 300, 30);
        this.statisticsLabel[2].setText("Grasses in jungle: ");

        this.statisticsLabel[3].setBounds(10, 130, 300, 30);
        this.statisticsLabel[3].setText("Grasses in savanna: ");

        this.statisticsLabel[4].setBounds(10, 170, 300, 30);
        this.statisticsLabel[4].setText("Average live long for dead animal: ");

        this.statisticsLabel[5].setBounds(10, 210, 300, 30);
        this.statisticsLabel[5].setText("Most common genotype: ");

        this.statisticsLabel[6].setBounds(10, 250, 300, 30);
        this.statisticsLabel[6].setText("Average energy per living animal: ");

        this.statisticsLabel[7].setBounds(10, 290, 300, 30);
        this.statisticsLabel[7].setText("Average number of children per living animal: ");
    }

    public void updateStatistics() {
        this.statisticsLabel[8].setBounds(310, 10, 100, 30);
        this.statisticsLabel[8].setText("day");

        this.statisticsLabel[9].setBounds(310, 50, 100, 30);
        this.statisticsLabel[9].setText(String.valueOf(this.map.statistics.numberOfAnimals));

        this.statisticsLabel[10].setBounds(310, 90, 100, 30);
        this.statisticsLabel[10].setText(String.valueOf(this.map.statistics.numberOfGrassesInJungle));

        this.statisticsLabel[11].setBounds(310, 130, 100, 30);
        this.statisticsLabel[11].setText(String.valueOf(this.map.statistics.numberOfGrassesInSavanna));

        this.statisticsLabel[12].setBounds(310, 170, 100, 30);
        this.statisticsLabel[12].setText(String.valueOf(this.map.statistics.averageliveLongForDeadAnimal));

        this.statisticsLabel[13].setBounds(310, 210, 100, 30);
        this.statisticsLabel[13].setText(this.map.statistics.mostCommonGenotype);

        this.statisticsLabel[14].setBounds(310, 250, 100, 30);
        this.statisticsLabel[14].setText(String.valueOf(this.map.statistics.averageEnergyPerLivingAnimal));

        this.statisticsLabel[15].setBounds(310, 290, 100, 30);
        this.statisticsLabel[15].setText(String.valueOf(this.map.statistics.averageNumberOfChildrenPerLivingAnimal));
    }
}
