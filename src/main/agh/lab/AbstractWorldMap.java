package agh.lab;

import java.util.ArrayList;

public abstract class AbstractWorldMap implements IWorldMap{
    protected MapVisualizer visualizer;
    protected Vector2d leftLowerCorner;
    protected Vector2d rightUpperCorner;
    protected Vector2d leftLowerCornerToDraw;
    protected Vector2d rightUpperCornerToDraw;
    protected ArrayList<Animal> animals = new ArrayList<>();

    public String toString() {
        this.setDrawFrame();
        return this.visualizer.draw(this.leftLowerCornerToDraw, this.rightUpperCornerToDraw);
    }

    //zapytać jak to jest z modyfikatorem dostępu w przypadku klas abstrakcyjnych
    protected abstract void setDrawFrame();

    public boolean canMoveTo(Vector2d position) {
        return (!this.isOccupied(position) && position.precedes(this.rightUpperCorner) && position.follows(this.leftLowerCorner));
    }

    public boolean place(Animal animal) {
        if(!this.canMoveTo(animal.getPosition()))
            return false;
        this.animals.add(animal);
        return true;
    }

    //true jeżeli jest tam jakikolwiek objekt, wpw false, musi tak być ze względu na klasę mapVisualizer
    //pamiętać: wywołuje metodę objectAt(position) w implementacji typu obiektu na rzecz którego została wywołana
    public boolean isOccupied(Vector2d position) {
        return this.objectAt(position) != null;
    }

    public Object objectAt(Vector2d position) {
        for(Animal animal: animals){
            if(position.equals(animal.getPosition()))
                return animal;
        }
        return null;
    }
}
