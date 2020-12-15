package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RenderPanel extends JPanel implements MouseListener {
    private IMediate mediator;
    private RectangularBiomesMap map;

    private int squareSide;
    private int panelWidth;
    private int panelHeight;
    private int rowsNumber;
    private int columnsNumber;

    private int animalsStartEnergy;

    public ArrayList<Animal> animalsToHighlight;

    public Animal selectedAnimal;

    public RenderPanel(IMediate mediator, RectangularBiomesMap map, int width, int height, int animalsStartEnergy) {
        this.mediator = mediator;
        this.map = map;

        this.rowsNumber = width;
        this.columnsNumber = height;
        this.animalsStartEnergy = animalsStartEnergy;

        this.selectedAnimal = null;

        this.squareSide = Math.min(800/this.rowsNumber, 760/this.columnsNumber);

        this.panelWidth = this.squareSide * this.rowsNumber + 4;
        this.panelHeight = this.squareSide * this.columnsNumber + 4;

        this.addMouseListener(this);
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
                        g.setColor(this.giveColor((Animal) object));
                        g.fillRect(2 + i * this.squareSide, 2 + j * this.squareSide, this.squareSide, this.squareSide);
                    }
                    else {
                        g.setColor(new Color(0,100,0));
                        g.fillRect(2 + i*this.squareSide, 2 + j*this.squareSide, this.squareSide, this.squareSide);
                    }
                }
            }
        }
        if(this.animalsToHighlight != null)
            for(Animal animal : this.animalsToHighlight) {
                Vector2d position = new Vector2d(animal.getPosition().x, animal.getPosition().y);
                g.setColor(Color.blue);
                g.fillRect(2 + position.x * this.squareSide, 2 + position.y * this.squareSide, this.squareSide, this.squareSide);
            }

        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.panelWidth, 2);
        g.fillRect(0,0, 2, this.panelHeight);
        g.fillRect(this.panelWidth - 2, 0, 2, this.panelHeight);
        g.fillRect(0, this.panelHeight - 2, this.panelWidth, 2);
    }

    @Override public void mouseClicked(MouseEvent e) {
        Vector2d position = new Vector2d((e.getX() - 2)/this.squareSide, (e.getY() - 2)/this.squareSide);
        if(this.map.isOccupied(position)) {
            Object object = this.map.objectAt(position);
            if (object instanceof  Animal) {
                this.selectedAnimal = (Animal)object;
                this.mediator.notifyMediator(this, "animal selected");
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {

    }

    @Override public void mouseReleased(MouseEvent e) {

    }

    @Override public void mouseEntered(MouseEvent e) {

    }

    @Override public void mouseExited(MouseEvent e) {

    }

    private Color giveColor(Animal animal) {
        int energy = animal.getEnergy();
        int startEnergy = this.animalsStartEnergy;

        for(int i = 0 ; i < 20; i++) {
            if(energy < (i+1) * 0.1 * startEnergy)
                return new Color(255, 93+7*i, 20+10*i);
        }
        return new Color(255, 233, 220);
    }
}
