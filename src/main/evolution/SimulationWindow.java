package evolution;

import java.awt.*;
import javax.swing.*;

public class SimulationWindow extends JPanel {
    private JFrame frame;

    private JPanel fieldPanel;
    private JPanel dynamicFieldPanel;

    private JLabel[][] field;

    private int windowWidth = 1200;
    private int windowHeight = 760;

    private int fieldWidth = 590;
    private int fieldHeight = 740;

    private int numberOfRows;
    private int numberOfColumns;

    private int squareSide;

    private RectangularBiomesMap map;


    public SimulationWindow(int width, int height, RectangularBiomesMap map) {
        this.numberOfRows = width;
        this.numberOfColumns = height;
        this.map = map;

        this.squareSide = Math.min(this.fieldWidth/width, this.fieldHeight/height);

        this.fieldPanel = new JPanel();
        this.fieldPanel.setBounds(600, 10, this.fieldWidth, this.fieldHeight);
        this.fieldPanel.setLayout(null);

        this.dynamicFieldPanel = new JPanel();
        this.dynamicFieldPanel.setBounds((this.fieldWidth - this.squareSide * width)/2,(this.fieldHeight - this.squareSide * height)/2,
                this.squareSide * width, this.squareSide * height);
        this.dynamicFieldPanel.setLayout(null);
        this.fieldPanel.add(dynamicFieldPanel);


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setLocationByPlatform(true);
        this.setLayout(null);
        this.add(fieldPanel);

        this.field = new JLabel[width][height];
        for(int row = 0; row < width; row++) {
            for(int column = 0; column < height; column++) {
                this.field[row][column] = new JLabel();

                this.field[row][column].setOpaque(true);
                this.field[row][column].setBackground(new Color(40,26,13));
                this.field[row][column].setBorder(BorderFactory.createStrokeBorder(new BasicStroke(1)));

                this.field[row][column].setSize(this.squareSide, this.squareSide);
                this.field[row][column].setLocation(row * this.squareSide, column * this.squareSide);

                this.dynamicFieldPanel.add(this.field[row][column]);
            }
        }
        frame.setVisible(true);
    }

    @Override public Dimension getPreferredSize() {
        return new Dimension(this.windowWidth, this.windowHeight);
    }


    public void updateField() {
        for(int row = 0; row < this.numberOfRows; row++) {
            for(int column = 0; column < this.numberOfColumns; column++) {
                Vector2d currentPosition = new Vector2d(row, column);
                if (this.map.isOccupied(currentPosition)) {
                    Object object = this.map.objectAt(currentPosition);
                    if (object instanceof Animal) {
                        this.field[row][column].setBackground(Color.orange);
                    } else {
                        this.field[row][column].setBackground(Color.green);
                    }
                } else {
                    this.field[row][column].setBackground(new Color(40,26,13));
                }
            }
        }
    }

    public void close() {
        this.frame.dispose();
    }
}