package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FollowedAnimalPanel extends JPanel implements ActionListener {
    private IMediate mediator;
    private RectangularBiomesMap map;

    private JButton highlightAnimalsWithCommonGenotype;

    private JLabel selectedGenotypeStringJLabel;
    private JLabel selectedAnimalGenotypeLabel;

    public FollowedAnimalPanel(IMediate mediator, RectangularBiomesMap map) {
        this.mediator = mediator;
        this.map = map;

        this.highlightAnimalsWithCommonGenotype = new JButton("Highlight");
        this.highlightAnimalsWithCommonGenotype.setLocation(50, 240);
        this.highlightAnimalsWithCommonGenotype.setSize(200, 40);
        this.highlightAnimalsWithCommonGenotype.addActionListener(this);

        this.selectedGenotypeStringJLabel = new JLabel();
        this.selectedGenotypeStringJLabel.setLocation(50, 30);
        this.selectedGenotypeStringJLabel.setSize(500, 50);
        this.selectedGenotypeStringJLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        this.selectedGenotypeStringJLabel.setText("Genotype of currently selected animal: ");

        this.selectedAnimalGenotypeLabel = new JLabel();
        this.selectedAnimalGenotypeLabel.setLocation(50, 100);
        this.selectedAnimalGenotypeLabel.setSize(500, 50);
        this.selectedAnimalGenotypeLabel.setFont(new Font("Calibri", Font.PLAIN, 30));

        this.add(this.highlightAnimalsWithCommonGenotype);
        this.add(this.selectedAnimalGenotypeLabel);
        this.add(this.selectedGenotypeStringJLabel);

        this.setLayout(null);
    }

    @Override public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.highlightAnimalsWithCommonGenotype)
            this.mediator.notifyMediator(this, "highlight clicked");
    }

    public void showSelectedAnimal(String genotype) {
        this.selectedAnimalGenotypeLabel.setText(genotype);
    }
}
