package evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class RenderMapPanel extends JPanel implements MouseListener {
    private final IMediate mediator;
    private final RectangularBiomesMap map;

    private boolean animalsToHighlightVisible;
    private ArrayList<Animal> animalsToHighlight = new ArrayList<>();
    private boolean selectedAnimalVisible;
    private Animal selectedAnimal;

    private final int squareSide;
    private final int panelWidth;
    private final int panelHeight;
    private final int rowsNumber;
    private final int columnsNumber;

    private final int animalsStartEnergy;

    public RenderMapPanel(IMediate mediator, RectangularBiomesMap map, int width, int height, int animalsStartEnergy) {
        this.mediator = mediator;
        this.map = map;

        this.animalsToHighlightVisible = false;
        this.selectedAnimalVisible = false;
        this.selectedAnimal = null;

        this.rowsNumber = width;
        this.columnsNumber = height;
        this.animalsStartEnergy = animalsStartEnergy;

        this.squareSide = Math.min(796/this.rowsNumber, 756/this.columnsNumber);

        this.panelWidth = this.squareSide * this.rowsNumber + 4;
        this.panelHeight = this.squareSide * this.columnsNumber + 4;

        this.addMouseListener(this);
        this.setLayout(null);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(this.panelWidth, this.panelHeight);
        this.setLocation(600 + (800 - this.panelWidth)/2,(760 - this.panelHeight)/2);

        //color whole map
        g.setColor(new Color(170, 224, 103));
        g.fillRect(0, 0, this.panelWidth, this.panelHeight);

        //color jungle
        g.setColor(new Color(50,205,50));
        g.fillRect(2 + this.map.getLowerLeftJungleVector().x * this.squareSide,
                2 + this.map.getLowerLeftJungleVector().y * this.squareSide, this.map.getJungleWidth() * this.squareSide,
                this.map.getJungleHeight() * this.squareSide);

        //color animals and grass
        for(int i = 0; i < this.rowsNumber; i++) {
            for(int j = 0; j < this.columnsNumber; j++) {
                Vector2d position = new Vector2d(i, j);
                if(this.map.isOccupied(position)) {
                    Object object = this.map.objectAt(position);
                    if(object instanceof Animal) {
                        g.setColor(this.getColor((Animal) object));
                        g.fillRect(2 + i * this.squareSide, 2 + j * this.squareSide, this.squareSide, this.squareSide);
                    }
                    else {
                        g.setColor(new Color(0,100,0));
                        g.fillRect(2 + i*this.squareSide, 2 + j*this.squareSide, this.squareSide, this.squareSide);
                    }
                }
            }
        }

        //color highlighted animals
        if(this.animalsToHighlightVisible)
            for(Animal animal : animalsToHighlight) {
                Vector2d position = new Vector2d(animal.getPosition().x, animal.getPosition().y);
                g.setColor(Color.blue);
                g.fillRect(2 + position.x * this.squareSide, 2 + position.y * this.squareSide, this.squareSide, this.squareSide);
            }

        //color selected animal
        if(this.selectedAnimalVisible) {
            g.setColor(Color.blue);
            g.fillRect(2 + this.selectedAnimal.getPosition().x * this.squareSide, 2 + this.selectedAnimal.getPosition().y * this.squareSide, this.squareSide, this.squareSide);
        }

        //color borders
        g.setColor(Color.BLACK);
        g.fillRect(0,0, this.panelWidth, 2);
        g.fillRect(0,0, 2, this.panelHeight);
        g.fillRect(this.panelWidth - 2, 0, 2, this.panelHeight);
        g.fillRect(0, this.panelHeight - 2, this.panelWidth, 2);
    }

    public Animal getSelectedAnimal() {
        return this.selectedAnimal;
    }

    public void setAnimalsToHighlight(ArrayList<Animal> animalsToHighlight) {
        this.animalsToHighlight = animalsToHighlight;
    }

    public void hideAnimalsToHighlight() {
        this.animalsToHighlightVisible = false;
    }

    public void showAnimalsToHighlight() {
        this.animalsToHighlightVisible = true;
    }

    public void hideSelectedAnimal() {
        this.selectedAnimalVisible = false;
    }

    public void showSelectedAnimal() {
        this.selectedAnimalVisible = true;
    }

    @Override public void mouseClicked(MouseEvent e) {
        Vector2d position = new Vector2d((e.getX() - 2)/this.squareSide, (e.getY() - 2)/this.squareSide);
        if(this.map.isOccupied(position)) {
            Object object = this.map.objectAt(position);
            if (object instanceof  Animal) {
                this.selectedAnimal = (Animal)object;
                this.mediator.notifyMediator(this, "animal selected");
                this.repaint();
            }
        }
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    private Color getColor(Animal animal) {
        int energy = animal.getEnergy();
        for(int i = 0 ; i < 20; i++) {
            if(energy < (i+1) * 0.1 * this.animalsStartEnergy)
                return new Color(255, 93+7*i, 20+10*i);
        }
        return new Color(255, 233, 220);
    }
}
