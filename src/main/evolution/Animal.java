package evolution;

import java.util.ArrayList;
import java.util.Random;

public class Animal extends AbstractWorldMapElement implements Comparable<Animal> {
    private int energy;
    private int livedDays;
    private int numberOfChildren;
    private Genotype genotype;

    private MapDirection direction;
    private final IWorldMap map;
    private final ArrayList<IAnimalObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map, MapDirection direction, Vector2d initialPosition, int animalStartEnergy, Genotype genotype) {
        this.map = map;
        this.direction = direction;
        this.location = initialPosition;
        this.energy = animalStartEnergy;
        this.livedDays = 0;
        this.numberOfChildren = 0;
        this.genotype = genotype;
        this.genotype.sortGenes();
    }

    public int compareTo(Animal other) {
        if(this.energy > other.energy)
            return -1;
        else if(this.energy < other.energy)
            return 1;
        else
            return 0;
    }

    //it's not needed but can stay
    public String toString() {
        switch (this.direction) {
            case NORTH:
                return "8";
            case NORTHEAST:
                return "9";
            case EAST:
                return "6";
            case SOUTHEAST:
                return "3";
            case SOUTH:
                return "2";
            case SOUTHWEST:
                return "1";
            case WEST:
                return "4";
            case NORTHWEST:
                return "7";
            default:
                return null;
        }
    }

    public int getEnergy() {
        return this.energy;
    }

    public void eatGrass(int energyFromEatenGrass) {
        this.energy += energyFromEatenGrass;
    }

    public void turn(){
        int numberOfTurns = this.genotype.drawGene();
        for(int i = 0; i < numberOfTurns; i++)
            this.direction = this.direction.next();
    }

    //for animal map is endless so map should be careful and update location of animal if it is abroad
    public void move(int animalMoveEnergy) {
        Vector2d oldLocation = this.location;
        Vector2d possibleNewLocation = this.location.add(this.direction.toUnitVector());
        this.location = possibleNewLocation;
        this.energy -= animalMoveEnergy;
        this.livedDays += 1;
        this.positionChangedInformObservers(oldLocation);
    }

    public void tryToKill() {
        if(this.energy == 0)
            this.diedInformObservers(this);
    }

    //export to another class maybe?
    public void breed(Animal other) {
        Random generator = new Random();

        MapDirection childDirection = MapDirection.values()[generator.nextInt(8)];
        Vector2d positionToPlaceNewAnimal = this.map.findPlaceForChildAnimal(this.location);

        Animal childAnimal = new Animal(this.map, childDirection, positionToPlaceNewAnimal,
                this.giveEnergyToChildAnimal() + other.giveEnergyToChildAnimal(), this.genotype.inheritGenes(other.genotype));
        this.map.animalToPutAtMap(childAnimal);
    }

    private int giveEnergyToChildAnimal() {
        this.numberOfChildren += 1;
        int energyForChildAnimal = this.energy/4;
        this.energy -= energyForChildAnimal;
        return energyForChildAnimal;
    }

    public void addObserver(IAnimalObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IAnimalObserver observer) {
        for(IAnimalObserver observerFromList : this.observers)
            if(observerFromList.equals(observer))
                this.observers.remove(observer);
    }

    private void positionChangedInformObservers(Vector2d oldLocation) {
        for(IAnimalObserver observerFromList : this.observers)
            observerFromList.positionChanged(oldLocation, this);
    }

    private void diedInformObservers(Animal animal) {
        for(IAnimalObserver observerFromList : this.observers)
            observerFromList.died(this);
    }
}
