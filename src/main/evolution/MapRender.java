package evolution;

import javax.swing.*;
import java.awt.*;

public class MapRender extends JPanel {
    private RectangularBiomesMap map;
    private int squareSide;
    private int panelWidth;
    private int panelHeight;
    private int rowsNumber;
    private int columnsNumber;

    public MapRender(RectangularBiomesMap map, int width, int height) {
        this.map = map;
        this.rowsNumber = width;
        this.columnsNumber = height;

        this.squareSide = Math.min(800/this.rowsNumber, 760/this.columnsNumber);

        this.panelWidth = this.squareSide * this.rowsNumber + 4;
        this.panelHeight = this.squareSide * this.columnsNumber + 4;

        this.setLayout(null);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(this.panelWidth, this.panelHeight);
        this.setLocation(600 + (800 - this.panelWidth)/2,(760 - this.panelHeight)/2);

        g.setColor(new Color(170, 224, 103));
        g.fillRect(0, 0, this.panelWidth, this.panelHeight);

        g.setColor(new Color(50,205,50));
        g.fillRect(2 + this.map.getLowerLeftJungleVector().x * this.squareSide,
                2 + this.map.getLowerLeftJungleVector().y * this.squareSide, this.map.getjungleWidth() * this.squareSide,
                this.map.getjungleHeight() * this.squareSide);

        for(int i = 0; i < this.rowsNumber; i++) {
            for(int j = 0; j < this.columnsNumber; j++) {
                Vector2d position = new Vector2d(i, j);
                if(this.map.isOccupied(position)) {
                    Object object = this.map.objectAt(position);
                    if (object instanceof  Animal) {
                        g.setColor(Color.orange);
                        g.fillRect(2 + i*this.squareSide, 2 + j*this.squareSide, this.squareSide, this.squareSide);
                    }
                    else {
                        g.setColor(new Color(0,100,0));
                        g.fillRect(2 + i*this.squareSide, 2 + j*this.squareSide, this.squareSide, this.squareSide);
                    }
                }
            }
        }
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.panelWidth, 2);
        g.fillRect(0,0, 2, this.panelHeight);
        g.fillRect(this.panelWidth - 2, 0, 2, this.panelHeight);
        g.fillRect(0, this.panelHeight - 2, this.panelWidth, 2);
    }
}
