package agh.lab;

import java.util.ArrayList;

public class RectangularMap implements IWorldMap {
    private MapVisualizer visualizer;
    private Vector2d leftLowerCorner;
    private Vector2d rightUpperCorner;
    private ArrayList<Animal> animals = new ArrayList<>();

    public RectangularMap(int width, int height) {
        this.visualizer = new MapVisualizer(this);
        this.leftLowerCorner = new Vector2d(0,0);
        this.rightUpperCorner = new Vector2d(width, height);
    }

    public String toString() {
        return this.visualizer.draw(this.leftLowerCorner, this.rightUpperCorner);
    }

    //Czy komórka position nie jest zajmowana przez inny obiekt i czy nalezy do mapy
    public boolean canMoveTo(Vector2d position) {
        if(!this.isOccupied(position) && position.precedes(this.rightUpperCorner) && position.follows(this.leftLowerCorner))
            return true;
        return false;
    }

    //jesli zwierze moze byc ustawione na pozycji na ktorej sie zrespiło to dodaje je do zwierzat i zwraca true, wpw zwraca false
    //tylko do spawnu
    public boolean place(Animal animal) {
        if(!canMoveTo(animal.getPosition()))
            return false;
        this.animals.add(animal);
        return true;
    }

    //sprawdza czy na dana pozycja na mapie jest zajęta, nie zwraca uwagi czy pozycja o którą pytam należy do mapy
    public boolean isOccupied(Vector2d position) {
        for(Animal animal: animals){
            if(position.equals(animal.getPosition()))
                return true;
        }
        return false;
    }

    //vv te dwa gettery przydałoby się wyrzucić do innego interfejsu, by
    //inne zwierzęta nie miały dostępu do siebie nawzajem
    //getter potrzebny by dostać się do zwierząt z listy
    public Animal getAnimalAtIndex(int i) {
        return animals.get(i);
    }

    //getter potrzebny by dostać się do rozmiaru listy zwierząt
    public int getNumberOfAnimals() {
        return animals.size();
    }

    //zwraca obiekt z danej pozycji, gdy brak obiektu na danej pozycji zwraca nulla
    public Object objectAt(Vector2d position) {
        for(Animal animal: animals){
            if(position.equals(animal.getPosition()))
                return animal;
        }
        return null;
    }
}
