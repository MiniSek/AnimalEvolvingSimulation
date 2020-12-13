package evolution;

import java.util.*;

public class RectangularBiomesMap extends AbstractWorldMap{
    private final ArrayList<IAnimalsBehaviourOnMapObserver> observers = new ArrayList<>();

    public final Statistics statistics;
    private final MapCoordinates coordinates;

    private final Map<Vector2d, IAnimalsHerd> animals = new HashMap<>();
    private final ArrayList<Animal> animalsToAdd = new ArrayList<>();
    private final ArrayList<Vector2d> positionOfHerdsToDelete = new ArrayList<>();

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

    //animal is added always
    @Override public void place(Animal animal) {
        IAnimalsHerd herdAtPosition;
        if(this.animals.get(animal.getPosition()) == null)
            herdAtPosition = new AnimalsHerd(this);
        else
            herdAtPosition = this.animals.get(animal.getPosition());
        herdAtPosition.addToHerd(animal);
        this.animals.put(animal.getPosition(), herdAtPosition);
        animal.addObserver(this);
        this.animalCreatedInformObservers(animal);
    }

    @Override public Object objectAt(Vector2d position) {
        IAnimalsHerd herdAtPosition = this.animals.get(position);
        if(herdAtPosition == null) {
            return this.grasses.get(position);
        }
        return herdAtPosition.getAlphaOfHerd();
    }

    public Vector2d getLowerLeftJungleVector() {
        return this.coordinates.leftLowerCornerOfJungle;
    }

    public int getjungleWidth() {
        return this.coordinates.jungleWidth;
    }

    public int getjungleHeight() {
        return this.coordinates.jungleHeight;
    }

    public boolean placeGrass(Vector2d location) {
        //grass cannot grow at position where is grass or animal
        if(!this.isOccupied(location)) {
            Grass grass = new Grass(location);
            this.grasses.put(location, grass);
            return true;
        }
        return false;
    }

    //what if almost whole jungle is filled?
    public void growGrasses() {
        int numberOfHerdsInJungle = 0;
        for(Vector2d position : this.animals.keySet())
            if(this.coordinates.isPositionInJungle(position))
                numberOfHerdsInJungle += 1;

        if(this.statistics.numberOfGrassesInJungle + numberOfHerdsInJungle < this.coordinates.jungleWidth * this.coordinates.jungleHeight) {
            while(!this.placeGrass(this.coordinates.drawPositionInJungle())) {}
            this.statistics.numberOfGrassesInJungle += 1;
        }
        if(this.statistics.numberOfGrassesInSavanna + this.animals.size() - numberOfHerdsInJungle <
                this.coordinates.width * this.coordinates.height - this.coordinates.jungleWidth * this.coordinates.jungleHeight) {
            while(!this.placeGrass(this.coordinates.drawPositionInSavanna())) {}
            this.statistics.numberOfGrassesInSavanna += 1;
        }
    }

    public void animalsEat(int grassEnergy) {
        for(Vector2d position : this.animals.keySet()) {
            if(this.grasses.get(position) != null) {
                this.animals.get(position).feedInHerd(grassEnergy);
                this.grasses.remove(position);
                if(this.coordinates.isPositionInJungle(position))
                    this.statistics.numberOfGrassesInJungle -= 1;
                else
                    this.statistics.numberOfGrassesInSavanna -= 1;
            }
        }
    }

    public void animalsReproduce(int animalStartEnergy) {
        for(Vector2d position : this.animals.keySet()) {
            if(this.animals.get(position).sizeOfHerd() > 1 &&
                    this.animals.get(position).getBetaOfHerd().getEnergy() >= 0.5 * animalStartEnergy)
                this.animals.get(position).breedInHerd();
        }

        for(Animal animal : this.animalsToAdd)
            this.place(animal);

        this.statistics.numberOfAnimals += this.animalsToAdd.size();
        this.animalsToAdd.clear();
    }

    public void animalToAddToMap(Animal animal) {
        this.animalsToAdd.add(animal);
    }
    
    public Vector2d findPlaceForChildAnimal(Vector2d parentsPosition) {
        Random generator = new Random();
        ArrayList<Vector2d> possiblePositionForChild = new ArrayList<>();

        for(MapDirection direction : MapDirection.values()) {
            Vector2d possiblePosition = this.coordinates.setCorrespondingPositionIfCurrentIsOutsideOfMap(parentsPosition.add(direction.toUnitVector()));
            if (!this.isOccupied(possiblePosition))
                possiblePositionForChild.add(possiblePosition);
        }

        if(possiblePositionForChild.size() == 0)
            return this.coordinates.setCorrespondingPositionIfCurrentIsOutsideOfMap(parentsPosition.add(MapDirection.values()[generator.nextInt(8)].toUnitVector()));
        else {
            return possiblePositionForChild.get(generator.nextInt(possiblePositionForChild.size()));
        }
    }

    //method which is called by animal to inform map about animal death
    public void deleteAnimalsWithZeroEnergy() {
        for(Vector2d position : this.animals.keySet()) {
            this.animals.get(position).removeAnimalsWithZeroEnergy();
            if(this.animals.get(position).sizeOfHerd() == 0)
                this.positionOfHerdsToDelete.add(position);
        }
        for(Vector2d position : this.positionOfHerdsToDelete)
            this.animals.remove(position);
        this.positionOfHerdsToDelete.clear();
    }

    public void animalWasDeleted(Animal animal) {
        this.statistics.numberOfDeadAnimals += 1;
        this.statistics.numberOfAnimals -= 1;
        this.statistics.numberOfLivedDaysInSummaryForDeadAnimals += animal.getLivedDays();
        this.animalDiedInformObservers(animal);
    }

    //method which is called by animal to inform map about position change
    public void positionChanged(Vector2d oldPosition, Animal animal) {
        animal.location = this.coordinates.setCorrespondingPositionIfCurrentIsOutsideOfMap(animal.getPosition());

        IAnimalsHerd herdAtOldPosition = this.animals.get(oldPosition);
        herdAtOldPosition.removeFromHerd(animal);
        if(herdAtOldPosition.sizeOfHerd() == 0)
            this.animals.remove(oldPosition);
        else {
            this.animals.put(oldPosition, herdAtOldPosition);
        }

        IAnimalsHerd herdAtNewPosition = this.animals.get(animal.getPosition());
        if(herdAtNewPosition == null)
            herdAtNewPosition = new AnimalsHerd(this);
        herdAtNewPosition.addToHerd(animal);
        this.animals.put(animal.getPosition(), herdAtNewPosition);
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
