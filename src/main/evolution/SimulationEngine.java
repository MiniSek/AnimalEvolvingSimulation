package evolution;

import java.util.*;

public class SimulationEngine implements IEngine, IAnimalsBehaviourOnMapObserver {
    private final RectangularBiomesMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Animal> animalsToDelete = new ArrayList<>();

    private final int animalMoveEnergy;
    private final int grassEnergy;
    private final int animalStartEnergy;

    public SimulationEngine(int width, int height, int animalStartEnergy, int animalMoveEnergy, int grassEnergy,
                            double jungleRatio, int numberOfAnimalsAtStart) {
        this.map = new RectangularBiomesMap(width, height, jungleRatio, numberOfAnimalsAtStart);
        this.map.addObserver(this);

        this.animalMoveEnergy = animalMoveEnergy;
        this.grassEnergy = grassEnergy;
        this.animalStartEnergy = animalStartEnergy;

        this.distributeAnimals(width, height, numberOfAnimalsAtStart, animalStartEnergy);
    }

    public void run(){
        for(int i=0; i<1000000; i++) {       //one iteration = one day
            this.map.deleteAnimalsWithZeroEnergy();
            this.deleteDeadAnimals();

            for(Animal animal : this.animals) {
                animal.turn();
                animal.move(this.animalMoveEnergy);
            }

            this.map.animalsEat(this.grassEnergy);

            this.map.animalsReproduce(this.animalStartEnergy);

            this.map.growGrasses();

            System.out.println(this.map.toString());
            System.out.print("After day: ");
            System.out.println(i+1);
            System.out.println("Number of animals: " + this.map.statistics.numberOfAnimals);
            System.out.println("Number of grass in jungle: " + this.map.statistics.numberOfGrassesInJungle);
            System.out.println("Number of grass in savanna: " + this.map.statistics.numberOfGrassesInSavanna);
            System.out.println("Number of dead animals: " + this.map.statistics.numberOfDeadAnimals);
            System.out.println("Number of days in summary for dead animals: " + this.map.statistics.numberOfLivedDaysInSummaryForDeadAnimals);
            if(this.map.statistics.numberOfDeadAnimals > 0)
                System.out.println("Average live long for dead animal: " + this.map.statistics.numberOfLivedDaysInSummaryForDeadAnimals/this.map.statistics.numberOfDeadAnimals);
            else
                System.out.println("Average live long for dead animal: 0");

            int energySum = 0;
            int childrenSum = 0;
            ArrayList<String> animalsGenesNumbersToSort = new ArrayList<>();
            for(Animal animal : this.animals) {
                energySum += animal.getEnergy();
                childrenSum += animal.getLivedDays();
                animalsGenesNumbersToSort.add(animal.getGenotype().numberOfEachGeneStr);
            }
            Collections.sort(animalsGenesNumbersToSort);
            System.out.println("Most common genotype: " + this.mostCommon(animalsGenesNumbersToSort));

            if(this.map.statistics.numberOfAnimals > 0) {
                System.out.println("Average energy per living animal: " + energySum / this.map.statistics.numberOfAnimals);
                System.out.println("Average number of children per living animal: " + childrenSum / this.map.statistics.numberOfAnimals);
            }
            else {
                System.out.println("Average energy per living animal: 0");
                System.out.println("Average number of children per living animal: 0");
            }

            if(this.map.statistics.numberOfAnimals < 3)
                break;

        }
    }

    public String mostCommon(ArrayList<String> tablica) {
        String previous = "";
        int bestCount = 0;
        int count = 1;
        String bestString = "";
        for(int i = 0; i < tablica.size(); i++) {
            if(tablica.get(i).equals(previous))
                count++;
            else
                count = 1;

            if(count > bestCount) {
                bestCount = count;
                bestString = tablica.get(i);
            }
            previous = tablica.get(i);
        }
        return bestString;
    }

    private void deleteDeadAnimals() {
        for(Animal animal : this.animalsToDelete)
            this.animals.remove(animal);
        this.animalsToDelete.clear();
    }

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

            byte[] numberOfGenes = {0,0,0,0,0,0,0,0};
            String numberOfGenesStr = "";
            for(int numberOfGene = 0; numberOfGene < 32; numberOfGene++)
                numberOfGenes[genotype.genes[numberOfGene]]++;
            for(int i = 0; i < 8; i++)
                numberOfGenesStr = numberOfGenesStr + String.valueOf(numberOfGenes[i]);
            genotype.numberOfEachGeneStr = numberOfGenesStr;

            MapDirection direction = MapDirection.values()[generator.nextInt(8)];

            Animal animal = new Animal(this.map, direction, possibleLocation, animalStartEnergy, genotype);

            if(!this.map.isOccupied(animal.getPosition())) {
                this.map.place(animal);
                currentNumberOfAnimals++;
            }
        }
    }

    public void animalCreated(Animal animal) {
        this.animals.add(animal);
    }

    public void animalDied(Animal animal) {
        this.animalsToDelete.add(animal);
    }
}
