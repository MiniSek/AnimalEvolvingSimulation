package agh.lab;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    private final MapVisualizer visualizer; // modyfikator prywatny działa chyba tak, że każdy obiekt klasy dziedziczącej
    // nie ma swojego visualizera tylko korzysta z prywatnego tej klasy, bo też tylko toString w tej klasie korzysta z tego pola
    protected Vector2d leftLowerCornerToDraw;
    protected Vector2d rightUpperCornerToDraw;
    protected final Map<Vector2d, Animal> animals = new LinkedHashMap<>();

    protected AbstractWorldMap() {
        this.visualizer = new MapVisualizer(this);
        this.leftLowerCornerToDraw = new Vector2d(0,0);
        this.rightUpperCornerToDraw = new Vector2d(0,0);
    }

    public String toString() {
        this.updateDrawFrame();
        return this.visualizer.draw(this.leftLowerCornerToDraw, this.rightUpperCornerToDraw);
    }

    protected abstract void updateDrawFrame();

    public boolean canMoveTo(Vector2d position) {
        return !(this.objectAt(position) instanceof Animal);
    }

    public boolean place(Animal animal) throws IllegalArgumentException {
        if(!this.canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException("animal cannot be added at position " + animal.getPosition());
        animal.addObserver(this);
        this.animals.put(animal.getPosition(), animal);
        return true;
    }

    //true jeżeli jest tam jakikolwiek obiekt, wpw false, musi tak być ze względu na klasę mapVisualizer
    //pamiętać: wywołuje metodę objectAt(position) w implementacji typu obiektu na rzecz którego została wywołana
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        return this.animals.get(position);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animalToRelocate = this.animals.get(oldPosition);
        if(animalToRelocate.getPosition().equals(newPosition)) {
            this.animals.remove(oldPosition);
            this.animals.put(newPosition, animalToRelocate);
        }
    }
}
