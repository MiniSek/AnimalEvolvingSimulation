package evolution;

import java.util.*;

public class SimulationEngine implements IEngine, IAnimalsBehaviourOnMapObserver {
    //raw map, without interface
    private final RectangularBiomesMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Animal> animalsToBury = new ArrayList<>();
    private final int animalMoveEnergy;
    private final int grassEnergy;
    private final int animalStartEnergy;

    public SimulationEngine(int width, int height, int animalStartEnergy, int animalMoveEnergy, int grassEnergy,
                            double jungleRatio, int numberOfAnimalsAtStart) {
        this.animalMoveEnergy = animalMoveEnergy;
        this.grassEnergy = grassEnergy;
        this.animalStartEnergy = animalStartEnergy;
        this.map = new RectangularBiomesMap(width, height, jungleRatio, numberOfAnimalsAtStart);
        this.map.addObserver(this);
        this.distributeAnimals(width, height, numberOfAnimalsAtStart, animalStartEnergy);
    }

    public void run(){
        for(int i=0; i<50; i++) {       //one iteration = one day
            //to delete dead animals I create array of animals which are dead and remove them together
            //think about better solution
            for(Animal animal : this.animals)
                animal.tryToKill();
            this.buryDeadAnimals();

            for(Animal animal : this.animals) {
                animal.turn();
                animal.move(this.animalMoveEnergy);
            }

            this.map.animalsEat(this.grassEnergy);

            this.map.animalsReproduce(this.animalStartEnergy);

            this.map.growGrasses();

            System.out.println(this.map.toString());
        }
    }

    private void buryDeadAnimals() {
        for(Animal animal : this.animalsToBury)
            this.animals.remove(animal);
        this.animalsToBury.clear();
    }

    //every map should randomize animals positions separately?
    private void distributeAnimals(int mapWidth, int mapHeight, int numberOfAnimals, int animalStartEnergy) {
        int currentNumberOfAnimals = 0;
        Random generator = new Random();
        while(currentNumberOfAnimals < numberOfAnimals) {
            Vector2d possibleLocation = new Vector2d(generator.nextInt(mapWidth),
                    generator.nextInt(mapHeight));
            Genotype genotype = new Genotype();
            for(int numberOfGene = 0; numberOfGene < 32; numberOfGene += 1) {
                genotype.genes[numberOfGene] = (byte)generator.nextInt(8);
            }
            MapDirection direction = MapDirection.values()[generator.nextInt(8)];

            Animal animal = new Animal(this.map, direction, possibleLocation, animalStartEnergy, genotype);

            if(this.map.place(animal))
                currentNumberOfAnimals++;
        }
    }

    public void animalCreated(Animal animal) {
        this.animals.add(animal);
    }

    public void animalDied(Animal animal) {
        this.animalsToBury.add(animal);
    }
}
