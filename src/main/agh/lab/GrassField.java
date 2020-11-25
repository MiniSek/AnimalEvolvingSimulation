package agh.lab;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class GrassField extends AbstractWorldMap {
    private int numberOfGrasses;
    private int lengthOfGrassedSquare;
    private Vector2d leftLowerCornerOfGrasses;
    private Vector2d rightUpperCornerOfGrasses;
    private final Map<Vector2d, Grass> grasses = new LinkedHashMap<>();

    public GrassField(int numberOfGrasses) throws IllegalArgumentException {
        super();
        if(numberOfGrasses < 0)
            throw new IllegalArgumentException("Liczba traw nie moze byc ujemna");
        this.numberOfGrasses = numberOfGrasses;
        this.lengthOfGrassedSquare = Math.toIntExact(Math.round(Math.sqrt(numberOfGrasses*10)));
        this.leftLowerCornerOfGrasses = new Vector2d(0,0);
        this.rightUpperCornerOfGrasses = new Vector2d(this.lengthOfGrassedSquare, this.lengthOfGrassedSquare);
        this.leftLowerCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.rightUpperCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.distributeGrasses();
    }

    private void distributeGrasses() {
        int currentNumberOfGrasses = 0;
        Random generator = new Random();
        while(currentNumberOfGrasses < this.numberOfGrasses) {
            Vector2d possibleLocation = new Vector2d(generator.nextInt(this.lengthOfGrassedSquare + 1),
                    generator.nextInt(this.lengthOfGrassedSquare + 1));

            if(this.placeGrass(possibleLocation))
                currentNumberOfGrasses++;
        }
    }

    private boolean canGrassBePlaced(Vector2d location) {
        return this.objectAt(location) == null;
    }

    private boolean placeGrass(Vector2d location) {
        if(canGrassBePlaced(location)) {
            Grass grass = new Grass(location);
            this.grasses.put(location, grass);
            return true;
        }
        return false;
    }

    //implementacja metody abstrakcyjnej
    public void updateDrawFrame() {
        this.leftLowerCornerToDraw = this.rightUpperCorner;
        this.rightUpperCornerToDraw = this.leftLowerCorner;
        for(Map.Entry<Vector2d, Grass>  entry : this.grasses.entrySet()) {
            this.leftLowerCornerToDraw = this.leftLowerCornerToDraw.lowerLeft(entry.getKey());
            this.rightUpperCornerToDraw = this.rightUpperCornerToDraw.upperRight(entry.getKey());
        }
        for(Map.Entry<Vector2d, Animal>  entry : this.animals.entrySet()) {
            this.leftLowerCornerToDraw = this.leftLowerCornerToDraw.lowerLeft(entry.getKey());
            this.rightUpperCornerToDraw = this.rightUpperCornerToDraw.upperRight(entry.getKey());
        }
    }

    //wynik zwracany przez metodę isOccupied(position) wymusza konieczność takiego nadpisania
    @Override public boolean canMoveTo(Vector2d position) {
        return (!(this.objectAt(position) instanceof Animal) && position.precedes(this.rightUpperCorner) && position.follows(this.leftLowerCorner));    // czy jest sens sprawdzać, czy int nie jest większy od maksymalnej możliwej wartości int'a?
    }

    //jesli jest tam trawa i zwierze, zwraca zwierze
    @Override public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if(object instanceof Animal)
            return object;
        return this.grasses.get(position);
    }
}
