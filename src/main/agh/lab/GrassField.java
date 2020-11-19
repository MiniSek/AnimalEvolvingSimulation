package agh.lab;

import java.util.ArrayList;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private int numberOfGrasses;
    private int lengthOfGrassedSquare;
    private Vector2d leftLowerCornerOfGrasses;
    private Vector2d rightUpperCornerOfGrasses;
    private ArrayList<Grass> grasses = new ArrayList<>();   // to pole może być finalne

    public GrassField(int numberOfGrasses) {
        if (numberOfGrasses < 0)
            throw new IllegalArgumentException("Liczba traw nie moze byc ujemna");
        this.visualizer = new MapVisualizer(this);  // czemu mapa abstrakcyjna tego nie stworzyła?
        this.numberOfGrasses = numberOfGrasses;
        this.lengthOfGrassedSquare = Math.toIntExact(Math.round(Math.sqrt(numberOfGrasses * 10)));
        this.leftLowerCornerOfGrasses = new Vector2d(0, 0);
        this.rightUpperCornerOfGrasses = new Vector2d(this.lengthOfGrassedSquare, this.lengthOfGrassedSquare);
        this.leftLowerCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.rightUpperCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.distributeGrasses();
    }

    private void distributeGrasses() {
        int currentNumberOfGrasses = 0;
        Random generator = new Random();
        while (currentNumberOfGrasses < this.numberOfGrasses) {
            Vector2d possibleLocation = new Vector2d(generator.nextInt(this.lengthOfGrassedSquare + 1),
                    generator.nextInt(this.lengthOfGrassedSquare + 1));

            if (!this.placeGrass(possibleLocation)) {
                currentNumberOfGrasses--;
            }
            currentNumberOfGrasses++;   // nie lepiej inkrementację zrobić warunkowo, niż inkrementować zawsze i warunkowo dekrementować?
        }
    }

    private boolean canGrassBePlaced(Vector2d location) {
        return this.objectAt(location) == null;
    }

    private boolean placeGrass(Vector2d location) {
        if (canGrassBePlaced(location)) {
            Grass grass = new Grass(location);
            this.grasses.add(grass);
            return true;
        }
        return false;
    }

    //implementacja metody abstrakcyjnej
    public void setDrawFrame() {
        this.leftLowerCornerToDraw = this.rightUpperCorner;
        this.rightUpperCornerToDraw = this.leftLowerCorner;
        for (Grass grass : this.grasses) {
            this.leftLowerCornerToDraw = this.leftLowerCornerToDraw.lowerLeft(grass.getPosition());
            this.rightUpperCornerToDraw = this.rightUpperCornerToDraw.upperRight(grass.getPosition());
        }
        for (Animal animal : this.animals) {
            this.leftLowerCornerToDraw = this.leftLowerCornerToDraw.lowerLeft(animal.getPosition());
            this.rightUpperCornerToDraw = this.rightUpperCornerToDraw.upperRight(animal.getPosition());
        }
    }

    //wynik zwracany przez metodę isOccupied(position) wymusza konieczność takiego nadpisania
    @Override
    public boolean canMoveTo(Vector2d position) {
        return (!(this.objectAt(position) instanceof Animal) && position.precedes(this.rightUpperCorner) && position.follows(this.leftLowerCorner));
    }

    //jesli jest tam trawa i zwierze, zwraca zwierze
    @Override
    public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if (object instanceof Animal)
            return object;

        for (Grass grass : this.grasses) {
            if (position.equals(grass.getPosition()))
                return grass;
        }
        return null;
    }
}
