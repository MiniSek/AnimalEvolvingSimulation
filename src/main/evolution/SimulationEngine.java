package evolution;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SimulationEngine implements ActionListener, IEngine, IAnimalsBehaviourOnMapObserver {
    private final RectangularBiomesMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Animal> animalsToDelete = new ArrayList<>();

    private final int animalMoveEnergy;
    private final int grassEnergy;
    private final int animalStartEnergy;

    private final int width;
    private final int height;

    private Timer timer;

    private SimulationWindow window;

    public SimulationEngine(int width, int height, int animalStartEnergy, int animalMoveEnergy, int grassEnergy,
                            double jungleRatio, int numberOfAnimalsAtStart) {
        this.width = width;
        this.height = height;

        this.map = new RectangularBiomesMap(width, height, jungleRatio, numberOfAnimalsAtStart);
        this.map.addObserver(this);

        this.animalMoveEnergy = animalMoveEnergy;
        this.grassEnergy = grassEnergy;
        this.animalStartEnergy = animalStartEnergy;

        this.distributeAnimals(width, height, numberOfAnimalsAtStart, animalStartEnergy);

        this.window = new SimulationWindow(this, this.map, width, height, animalStartEnergy);

        this.timer = new Timer(100, this);
    }

    @Override public void actionPerformed(ActionEvent e) {
        this.window.update();

        this.map.deleteAnimalsWithZeroEnergy();
        this.deleteDeadAnimals();

        for (Animal animal : this.animals) {
            animal.turn();
            animal.move(this.animalMoveEnergy);
        }

        this.map.animalsEat(this.grassEnergy);

        this.map.animalsReproduce(this.animalStartEnergy);

        this.map.growGrasses();

        this.helpToUpdateStatistics();
    }

    public void run() {
        this.timer.start();
    }

    public void stopTimer() {
        this.timer.stop();
    }

    public boolean isTimerStopped() {
        return !this.timer.isRunning();
    }

    public ArrayList<Animal> giveAnimalsWithMostCommonGenotype() {
        ArrayList<Animal> animalsWithMostCommonGenotype = new ArrayList<>();
        for(Animal animal : this.animals) {
            if(animal.getGenotype().equals(this.map.statistics.mostCommonGenotype))
                animalsWithMostCommonGenotype.add(animal);
        }
        return animalsWithMostCommonGenotype;
    }

    private void helpToUpdateStatistics() {
        int energySum = 0;
        int childrenSum = 0;
        ArrayList<Animal> animalsGenesNumbersToSort = new ArrayList<>();
        for(Animal animal : this.animals) {
            energySum += animal.getEnergy();
            childrenSum += animal.getLivedDays();
            animalsGenesNumbersToSort.add(animal);
        }

        this.map.statistics.updateStatistics(energySum, childrenSum, animalsGenesNumbersToSort);
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
            for(int numberOfGene = 0; numberOfGene < 32; numberOfGene += 1)
                genotype.genes[numberOfGene] = (byte)generator.nextInt(8);

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
