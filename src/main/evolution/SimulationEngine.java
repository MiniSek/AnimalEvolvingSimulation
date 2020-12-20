package evolution;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SimulationEngine implements ActionListener, IAnimalsBehaviourOnMapObserver {
    private final RectangularBiomesMap map;

    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Animal> animalsToDelete = new ArrayList<>();

    private final int animalMoveEnergy;
    private final int grassEnergy;
    private final int animalStartEnergy;

    private final SimulationWindow simulationWindow;

    private final Timer timer;
    private boolean stopTime;

    /*
    add some exceptions
    most common genotype in statistics is wrong probably
    */

    public SimulationEngine(int width, int height, int animalStartEnergy, int animalMoveEnergy, int grassEnergy,
                            double jungleRatio, int numberOfAnimalsAtStart) {
        this.map = new RectangularBiomesMap(width, height, jungleRatio, numberOfAnimalsAtStart);
        this.map.addObserver(this);

        this.animalMoveEnergy = animalMoveEnergy;
        this.grassEnergy = grassEnergy;
        this.animalStartEnergy = animalStartEnergy;

        this.simulationWindow = new SimulationWindow(this, this.map, width, height, animalStartEnergy);

        this.distributeAnimals(width, height, numberOfAnimalsAtStart, animalStartEnergy);

        this.timer = new Timer(100, this);
        this.stopTime = false;

        this.map.statistics.updateStatisticsAtStart(this.animals, animalStartEnergy);
        this.simulationWindow.update();
    }

    @Override public void actionPerformed(ActionEvent e) {
        this.map.deleteAnimalsWithSmallAmountOfEnergy(this.animalMoveEnergy);
        this.deleteDeadAnimals();

        for (Animal animal : this.animals) {
            animal.turn();
            animal.move(this.animalMoveEnergy);
        }

        this.map.animalsEat(this.grassEnergy);

        this.map.animalsReproduce(this.animalStartEnergy);

        this.map.growGrasses();

        this.map.statistics.updateStatistics(this.animals);

        this.simulationWindow.update();

        if(this.stopTime) {
            this.timer.stop();
            this.stopTime = false;
        }
    }

    public void run() {
        this.timer.start();
    }

    public void stopTimer() {
        this.stopTime = true;
    }

    public boolean isTimerStopped() {
        return !this.timer.isRunning();
    }

    public void setDelay(int delay) {
        this.timer.setDelay(delay);
    }


    public ArrayList<Animal> getAnimalsToHighlight() {
        ArrayList<Animal> animalsToHighlight = new ArrayList<>();
        for(Animal animal : this.animals)
            if(animal.getGenotype().equals(this.map.statistics.mostCommonGenotype))
                animalsToHighlight.add(animal);
        return animalsToHighlight;
    }

    public void turnOffMarkers() {
        for(Animal animal : this.animals)
            animal.turnOffMarker();
    }

    public int getDay() {
        return this.map.statistics.numberOfDay;
    }

    public void saveStatsToFile() {
        this.map.statistics.saveStatsToFile();
    }

    private void deleteDeadAnimals() {
        for(Animal animal : this.animalsToDelete)
            this.animals.remove(animal);
        this.animalsToDelete.clear();
    }

    private void distributeAnimals(int mapWidth, int mapHeight, int numberOfAnimalsAtStart, int animalStartEnergy) {
        Random generator = new Random();
        for(int currentNumberOfAnimals = 0; currentNumberOfAnimals < numberOfAnimalsAtStart; currentNumberOfAnimals++) {
            Vector2d possibleLocation = new Vector2d(generator.nextInt(mapWidth), generator.nextInt(mapHeight));
            while(this.map.isOccupied(possibleLocation)) {
                possibleLocation = new Vector2d(generator.nextInt(mapWidth), generator.nextInt(mapHeight));
            }

            Genotype genotype = new Genotype();
            for(int numberOfGene = 0; numberOfGene < 32; numberOfGene += 1)
                genotype.genes[numberOfGene] = (byte)generator.nextInt(8);

            genotype.repairGenotype();

            MapDirection direction = MapDirection.values()[generator.nextInt(8)];

            Animal animal = new Animal(this.map, direction, possibleLocation, animalStartEnergy, genotype);

            this.map.place(animal);
        }
    }

    public void animalCreated(Animal animal) {
        this.animals.add(animal);
    }

    public void animalDied(Animal animal) {
        this.animalsToDelete.add(animal);
    }
}
