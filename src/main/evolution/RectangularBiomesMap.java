package evolution;

import java.util.*;

public class RectangularBiomesMap extends AbstractWorldMap{
    private final ArrayList<IAnimalsBehaviourOnMapObserver> observers = new ArrayList<>();

    private final Statistics statistics;
    private final MapCoordinates coordinates;

    private final Map<Vector2d, IAnimalsHerd> animals = new HashMap<>();
    private final ArrayList<Animal> animalsToIncubate = new ArrayList<>();

    private final Map<Vector2d, Grass> grasses = new LinkedHashMap<>();


    public RectangularBiomesMap(int width, int height, double jungleRatio, int numberOfAnimalsAtStart) {
        super();

        this.statistics = new Statistics(numberOfAnimalsAtStart);
        this.coordinates = new MapCoordinates(width, height, jungleRatio);

        this.leftLowerCornerToDraw = this.coordinates.leftLowerCorner;
        this.rightUpperCornerToDraw = this.coordinates.rightUpperCorner;
    }

    public void updateDrawFrame() {
        //it's unnecessary to update this, in constructor draw frame is set
    }

    //everytime animal can move on this map, am I right?
    @Override public boolean canMoveTo(Vector2d position) {
        return true;
    }

    //animal can spawn only at empty place <- current implemented
    //or animal can spawn on the grass?
    @Override public boolean place(Animal animal) {
        if(!this.isOccupied(animal.getPosition())) {
            IAnimalsHerd herdAtPosition;
            if(this.animals.get(animal.getPosition()) == null)
                herdAtPosition = new AnimalsHerd();
            else
                herdAtPosition = this.animals.get(animal.getPosition());
            herdAtPosition.addToHerd(animal);
            this.animals.put(animal.getPosition(), herdAtPosition);
            animal.addObserver(this);
            this.animalCreatedInformObservers(animal);
            return true;
        }
        return false;
    }

    //return first animal (should be with the highest energy) or grass or null
    //this method will be used to draw objects on the map
    @Override public Object objectAt(Vector2d position) {
        IAnimalsHerd herdAtPosition = this.animals.get(position);
        if(herdAtPosition == null) {
            return this.grasses.get(position);
        }
        return herdAtPosition.getAlphaOfHerd();
    }


    public Grass grassAtPosition(Vector2d location) {
        return grasses.get(location);
    }

    public boolean placeGrass(Vector2d location) {
        if(this.objectAt(location) == null) {
            Grass grass = new Grass(location);
            this.grasses.put(location, grass);
            return true;
        }
        return false;
    }

    //what if almost whole jungle is filled?
    //the whole block of code with grass can be replaced to class planter or sth like that
    public void growGrasses() {
        if(this.statistics.numberOfGrassesInJungle < this.coordinates.jungleWidth * this.coordinates.jungleHeight) {
            while(!this.placeGrass(this.coordinates.drawPositionInJungle())) {}
            this.statistics.numberOfGrassesInJungle += 1;
        }
        if(this.statistics.numberOfGrassesInSavanna < this.coordinates.width * this.coordinates.height -
                this.coordinates.jungleWidth * this.coordinates.jungleHeight) {
            while(!this.placeGrass(this.coordinates.drawPositionInSavanna())) {}
            this.statistics.numberOfGrassesInSavanna += 1;
        }
    }

    //maybe relocate this behaviour
    //use method entrySet()
    public void animalsEat(int grassEnergy) {
        for(Vector2d position : this.animals.keySet()) {
            if(grassAtPosition(position) != null) {
                this.animals.get(position).feedAnimalsInHerd(grassEnergy);
                this.grasses.remove(position);
                if(this.coordinates.isPositionInJungle(position))
                    this.statistics.numberOfGrassesInJungle -= 1;
                else
                    this.statistics.numberOfGrassesInSavanna -= 1;
            }
        }
    }

    //maybe relocate this behaviour
    //use method entrySet()
    public void animalsReproduce(int animalStartEnergy) {
        for(Vector2d position : this.animals.keySet()) {
            if(this.animals.get(position).sizeOfHerd() > 1 &&
                    this.animals.get(position).getAlphaOfHerd().getEnergy() >= 0.5 * animalStartEnergy)
                this.animals.get(position).breedAnimalsInHerd();
        }
        for(Animal animal : this.animalsToIncubate)
            this.place(animal);
        this.statistics.numberOfAnimals += this.animalsToIncubate.size();
        this.animalsToIncubate.clear();
    }

    public void animalToPutAtMap(Animal animal) {
        this.animalsToIncubate.add(animal);
    }

    //can make new animal outside of the map, change it
    public Vector2d findPlaceForChildAnimal(Vector2d parentsPosition) {
        Random generator = new Random();
        ArrayList<Vector2d> possibleLocations = new ArrayList<>();
        for(MapDirection direction : MapDirection.values()) {
            if (!this.isOccupied(parentsPosition.add(direction.toUnitVector())))
                possibleLocations.add(parentsPosition.add(direction.toUnitVector()));
        }
        if(possibleLocations.size() == 0)
            return parentsPosition.add(MapDirection.values()[generator.nextInt(8)].toUnitVector());
        else {
            return possibleLocations.get(generator.nextInt(possibleLocations.size()));
        }
    }

    //method which is called by animal to inform map about animal death
    public void died(Animal animal) {
        this.animals.get(animal.getPosition()).removeFromHerd(animal);
        if(this.animals.get(animal.getPosition()).sizeOfHerd() == 0)
            this.animals.remove(animal.getPosition());
        this.statistics.numberOfDeadAnimals += 1;
        this.animalDiedInformObservers(animal);
    }

    //method which is called by animal to inform map about position change
    public void positionChanged(Vector2d oldPosition, Animal animal) {
        this.bringAnimalOnMap(animal);

        IAnimalsHerd herdAtOldPosition = this.animals.get(oldPosition);
        herdAtOldPosition.removeFromHerd(animal);
        if(herdAtOldPosition.sizeOfHerd() == 0)
            this.animals.remove(oldPosition);
        else {
            this.animals.put(oldPosition, herdAtOldPosition);
        }

        IAnimalsHerd herdAtNewPosition = this.animals.get(animal.getPosition());
        if(herdAtNewPosition == null)
            herdAtNewPosition = new AnimalsHerd();
        herdAtNewPosition.addToHerd(animal);
        this.animals.put(animal.getPosition(), herdAtNewPosition);
    }

    //simplified it!
    private void bringAnimalOnMap(Animal animal) {
        if(!animal.getPosition().precedes(this.coordinates.rightUpperCorner)) {
            animal.location = new Vector2d(animal.getPosition().x % (this.coordinates.rightUpperCorner.x + 1),
                    animal.getPosition().y % (this.coordinates.rightUpperCorner.y + 1));
        }
        if(!animal.getPosition().follows(this.coordinates.leftLowerCorner)) {
            animal.location = new Vector2d((animal.getPosition().x + this.coordinates.rightUpperCorner.x + 1) % (this.coordinates.rightUpperCorner.x + 1),
                    (animal.getPosition().y + this.coordinates.rightUpperCorner.y + 1) % (this.coordinates.rightUpperCorner.y + 1));
        }
    }

    public void addObserver(IAnimalsBehaviourOnMapObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IAnimalsBehaviourOnMapObserver observer) {
        for(IAnimalsBehaviourOnMapObserver observerFromList : this.observers)
            if(observerFromList.equals(observer))
                this.observers.remove(observer);
    }

    public void animalCreatedInformObservers(Animal animal) {
        for(IAnimalsBehaviourOnMapObserver observerFromList : this.observers)
            observerFromList.animalCreated(animal);
    }

    public void animalDiedInformObservers(Animal animal) {
        for(IAnimalsBehaviourOnMapObserver observerFromList : this.observers)
            observerFromList.animalDied(animal);
    }
}
