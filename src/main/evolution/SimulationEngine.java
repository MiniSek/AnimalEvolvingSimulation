package evolution;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SimulationEngine implements ActionListener, IEngine, IAnimalsBehaviourOnMapObserver {
    private final RectangularBiomesMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final ArrayList<Animal> animalsToDelete = new ArrayList<>();

    private final ArrayList<Animal> animalsToHighlight = new ArrayList<>();

    public Animal animalSelected;
    public int numberOfChildrenWhenPicked;
    public int dayWhenPicked;
    public int dayWhenDead;

    private final int animalMoveEnergy;
    private final int grassEnergy;
    private final int animalStartEnergy;

    private final Timer timer;
    private boolean stopTime;

    private final SimulationWindow simulationWindow;

    public SimulationEngine(int width, int height, int animalStartEnergy, int animalMoveEnergy, int grassEnergy,
                            double jungleRatio, int numberOfAnimalsAtStart) {
        this.map = new RectangularBiomesMap(width, height, jungleRatio, numberOfAnimalsAtStart);
        this.map.addObserver(this);

        this.animalMoveEnergy = animalMoveEnergy;
        this.grassEnergy = grassEnergy;
        this.animalStartEnergy = animalStartEnergy;

        this.distributeAnimals(width, height, numberOfAnimalsAtStart, animalStartEnergy);

        this.simulationWindow = new SimulationWindow(this, this.map, width, height, animalStartEnergy);

        this.timer = new Timer(100, this);
        this.stopTime = false;

        this.map.statistics.updateStatisticsAtStart(animalStartEnergy, this.animals);
        this.simulationWindow.update();
    }

    @Override public void actionPerformed(ActionEvent e) {
        this.map.deleteAnimalsWithZeroEnergy();
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
        return this.animalsToHighlight;
    }

    public void clearAnimalsToHighlight() {
        this.animalsToHighlight.clear();
    }

    public void setAnimalsToHighlight() {
        for (Animal animal : this.animals)
            if (animal.getGenotype().equals(this.map.statistics.mostCommonGenotype))
                this.animalsToHighlight.add(animal);
    }

    public void animalPicked() {
        this.dayWhenPicked = this.map.statistics.numberOfDay;
        this.numberOfChildrenWhenPicked = this.animalSelected.getNumberOfChildren();
        this.animalSelected.turnOnMarker();
    }

    public void animalSelectedDied() {
        this.dayWhenDead = this.map.statistics.numberOfDay;
        this.simulationWindow.update();
        this.animalSelected = null;
        for(Animal animal : this.animals)
            animal.turnOffMarker();
    }

    public void animalSelectedUnselected() {
        this.animalSelected = null;
        for(Animal animal : this.animals)
            animal.turnOffMarker();
    }

    public int countDescendants() {
        int numberOfDescendants = -1;
        for(Animal animal : this.animals)
            if(animal.marked())
                numberOfDescendants++;
        return Math.max(numberOfDescendants, 0);
    }


    public void saveStatsToFile() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String filePath = "output_stats\\" + LocalDateTime.now().format(formatter) + ".txt";
        File myObj = new File(filePath);
        try {
            if(myObj.createNewFile()) {
                FileWriter writer = new FileWriter(filePath);
                writer.write("Number of days: " + this.map.statistics.numberOfDay + "\n");
                writer.write("Average number of animals at map: " +
                        Math.round((100.0*this.map.statistics.numberOfAnimalsInTotal)/this.map.statistics.numberOfDay)/100.0 + "\n");
                writer.write("Average number of grasses at map: " +
                        Math.round((100.0*this.map.statistics.numberOfGrassesInTotal)/this.map.statistics.numberOfDay)/100.0 + "\n");

                int bestCount = 0;
                String bestString = "";
                for(String genotype : this.map.statistics.numberOfEachGenotypeInTotal.keySet()) {
                    if(this.map.statistics.numberOfEachGenotypeInTotal.get(genotype) > bestCount) {
                        bestCount = this.map.statistics.numberOfEachGenotypeInTotal.get(genotype);
                        bestString = genotype;
                    }
                }
                writer.write("Most common genotype in simulation: " + bestString + "\n");
                writer.write("Number of most common genotype in simulation: " + bestCount + "\n");

                writer.write("Average of average energy per living animal in simulation: " +
                        Math.round((100.0*this.map.statistics.averageEnergyPerLivingAnimalInTotal)/this.map.statistics.numberOfDay)/100.0 + "\n");
                writer.write("Average of average of live long for dead animals in simulation: " +
                        Math.round((100.0*this.map.statistics.averageLiveLongForDeadAnimalInTotal)/(this.map.statistics.numberOfDay -
                                this.map.statistics.dayNumberWhenFirstAnimalDead))/100.0 + "\n");
                writer.write("Average of average of number of children per living animal in simulation: " +
                        Math.round((100.0*this.map.statistics.averageNumberOfChildrenPerLivingAnimalInTotal)/this.map.statistics.numberOfDay)/100.0 + "\n");

                writer.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteDeadAnimals() {
        for(Animal animal : this.animalsToDelete) {
            this.animals.remove(animal);
            if(animal.equals(this.animalSelected))
                this.animalSelectedDied();
        }
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
