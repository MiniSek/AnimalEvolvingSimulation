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
            throw new IllegalArgumentException("Number of grasses cannot be negative");
        this.numberOfGrasses = numberOfGrasses;
        this.lengthOfGrassedSquare = Math.toIntExact(Math.round(Math.sqrt(numberOfGrasses*10)));
        this.leftLowerCornerOfGrasses = new Vector2d(0,0);
        this.rightUpperCornerOfGrasses = new Vector2d(this.lengthOfGrassedSquare, this.lengthOfGrassedSquare);
        this.leftLowerCornerToDraw = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.rightUpperCornerToDraw = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.distributeGrasses();
    }

    private void distributeGrasses()  throws IllegalArgumentException {
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

    private boolean placeGrass(Vector2d location) throws IllegalArgumentException {
        if(canGrassBePlaced(location)) {
            Grass grass = new Grass(location);
            this.grasses.put(location, grass);
            this.mapBoundary.addObjectToMapBoundary(grass);
            return true;
        }
        return false;
    }

    //abstract method implementation
    public void updateDrawFrame() {
        this.leftLowerCornerToDraw = this.mapBoundary.getLeftLowerCornerToDraw();
        this.rightUpperCornerToDraw = this.mapBoundary.getRightUpperCornerToDraw();
    }

    @Override public Object objectAt(Vector2d position) {
        Object object = super.objectAt(position);
        if(object instanceof Animal)
            return object;
        return this.grasses.get(position);
    }
}
