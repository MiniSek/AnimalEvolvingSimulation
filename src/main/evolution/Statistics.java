package evolution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Statistics {
    public int numberOfDay;
    public int numberOfAnimals;
    public int numberOfChildrenForLivingAnimals;
    public long numberOfDeadAnimals;
    public long numberOfLivedDaysInSummaryForDeadAnimals;
    public int numberOfGrassesInJungle;
    public int numberOfGrassesInSavanna;
    public int numberOfGrassesInMap;
    public double averageEnergyPerLivingAnimal;
    public double averageLiveLongForDeadAnimal;
    public double averageNumberOfChildrenPerLivingAnimal;
    public String mostCommonGenotype;
    public int numberOfMostCommonGenotype;

    public long numberOfAnimalsInTotal;
    public long numberOfGrassesInTotal;
    public HashMap<String, Integer> numberOfEachGenotypeInTotal = new HashMap<>();
    public double averageEnergyPerLivingAnimalInTotal;
    public double averageLiveLongForDeadAnimalInTotal;
    public double averageNumberOfChildrenPerLivingAnimalInTotal;

    public int dayNumberWhenFirstAnimalDead;

    public Statistics(int numberOfAnimalsAtStart) {
        this.numberOfDay = 0;

        this.numberOfAnimals = numberOfAnimalsAtStart;
        this.numberOfChildrenForLivingAnimals = 0;

        this.numberOfDeadAnimals = 0;
        this.numberOfLivedDaysInSummaryForDeadAnimals = 0;

        this.numberOfGrassesInJungle = 0;
        this.numberOfGrassesInSavanna = 0;
        this.numberOfGrassesInMap = 0;

        this.averageEnergyPerLivingAnimal = 0;
        //-1 when there aren't any dead animal
        this.averageLiveLongForDeadAnimal = -1;
        this.averageNumberOfChildrenPerLivingAnimal = 0;

        this.mostCommonGenotype = "";
        this.numberOfMostCommonGenotype = 0;

        this.numberOfAnimalsInTotal = numberOfAnimalsAtStart;
        this.numberOfGrassesInTotal = 0;
        this.averageEnergyPerLivingAnimalInTotal = 0;
        //not -1 cause it is for summing
        this.averageLiveLongForDeadAnimalInTotal = 0;
        this.averageNumberOfChildrenPerLivingAnimalInTotal = 0;

        this.dayNumberWhenFirstAnimalDead = -1;
    }

    static class SortByGenotype implements Comparator<Animal> {
        public int compare(Animal first, Animal second) {
            return first.getGenotype().compareTo(second.getGenotype());
        }
    }


    public void updateStatistics(ArrayList<Animal> animals) {
        int energySum = 0;
        int childrenSum = 0;
        for(Animal animal : animals) {
            energySum += animal.getEnergy();
            childrenSum += animal.getNumberOfChildren();
            this.updateNumberOfEachGenotypeInTotal(animal);
        }

        this.numberOfDay += 1;
        this.numberOfGrassesInMap = this.numberOfGrassesInJungle + this.numberOfGrassesInSavanna;

        if(this.numberOfDeadAnimals > 0) {
            this.averageLiveLongForDeadAnimal = Math.round(100.0 * this.numberOfLivedDaysInSummaryForDeadAnimals / this.numberOfDeadAnimals) / 100.0;
            if(this.dayNumberWhenFirstAnimalDead == -1)
                this.dayNumberWhenFirstAnimalDead = this.numberOfDay;
        }
        else
            this.averageLiveLongForDeadAnimal = -1;

        if(this.numberOfAnimals > 0) {
            this.averageEnergyPerLivingAnimal = Math.round(100.0*energySum/this.numberOfAnimals)/100.0;
            this.averageNumberOfChildrenPerLivingAnimal = Math.round(100.0*childrenSum/this.numberOfAnimals)/100.0;
        }
        else {
            this.averageEnergyPerLivingAnimal = 0;
            this.averageNumberOfChildrenPerLivingAnimal = 0;
        }

        Collections.sort(animals, new SortByGenotype());
        this.mostCommonGenotype(animals);

        this.numberOfAnimalsInTotal += this.numberOfAnimals;
        this.numberOfGrassesInTotal += this.numberOfGrassesInMap;
        this.averageEnergyPerLivingAnimalInTotal += this.averageEnergyPerLivingAnimal;
        this.averageLiveLongForDeadAnimalInTotal += this.averageLiveLongForDeadAnimal;
        this.averageNumberOfChildrenPerLivingAnimalInTotal += this.averageNumberOfChildrenPerLivingAnimal;
    }

    public void updateStatisticsAtStart(ArrayList<Animal> animals, int animalsStartEnergy) {
        this.averageEnergyPerLivingAnimal = animalsStartEnergy;

        Collections.sort(animals, new SortByGenotype());
        this.mostCommonGenotype(animals);

        this.averageEnergyPerLivingAnimalInTotal = animalsStartEnergy;

        for(Animal animal : animals)
            this.updateNumberOfEachGenotypeInTotal(animal);
    }

    //method for setting most common genotype and number of animals with it
    //ArrayList<animal> have to be sorted for this method
    private void mostCommonGenotype(ArrayList<Animal> animalsSortedByGenotype) {
        String previous = "";
        int bestCount = 0;
        int count = 1;
        String bestString = "";
        for(Animal animal : animalsSortedByGenotype) {
            if(animal.getGenotype().equals(previous))
                count++;
            else
                count = 1;

            if(count > bestCount) {
                bestCount = count;
                bestString = animal.getGenotype();
            }
            previous = animal.getGenotype();
        }

        this.mostCommonGenotype = bestString;
        this.numberOfMostCommonGenotype = bestCount;
    }

    private void updateNumberOfEachGenotypeInTotal(Animal animal) {
        Integer numberOfGenotypes = this.numberOfEachGenotypeInTotal.get(animal.getGenotype());
        if(numberOfGenotypes != null)
            this.numberOfEachGenotypeInTotal.put(animal.getGenotype(),
                    this.numberOfEachGenotypeInTotal.get(animal.getGenotype()) + 1);
        else
            this.numberOfEachGenotypeInTotal.put(animal.getGenotype(), 1);
    }

    //stats are saved to file which name is data and time when simulation was closed
    public void saveStatsToFile() throws FileSystemAlreadyExistsException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH_mm_ss");
        String filePath = "output_stats\\" + LocalDateTime.now().format(formatter) + ".txt";
        File myObj = new File(filePath);
        try {
            if(myObj.createNewFile()) {
                FileWriter writer = new FileWriter(filePath);
                writer.write("Number of days: " + this.numberOfDay + "\n");
                writer.write("Average number of animals at map: " +
                        Math.round((100.0*this.numberOfAnimalsInTotal)/this.numberOfDay)/100.0 + "\n");
                writer.write("Average number of grasses at map: " +
                        Math.round((100.0*this.numberOfGrassesInTotal)/this.numberOfDay)/100.0 + "\n");

                int bestCount = 0;
                String bestString = "";
                for(String genotype : this.numberOfEachGenotypeInTotal.keySet()) {
                    if(this.numberOfEachGenotypeInTotal.get(genotype) > bestCount) {
                        bestCount = this.numberOfEachGenotypeInTotal.get(genotype);
                        bestString = genotype;
                    }
                }
                writer.write("Most common genotype in simulation: " + bestString + "\n");
                writer.write("Number of most common genotype in simulation: " + bestCount + "\n");

                writer.write("Average of average energy per living animal in simulation: " +
                        Math.round((100.0*this.averageEnergyPerLivingAnimalInTotal)/this.numberOfDay)/100.0 + "\n");
                writer.write("Average of average of live long for dead animals in simulation: " +
                        Math.round((100.0*this.averageLiveLongForDeadAnimalInTotal)/(this.numberOfDay -
                                this.dayNumberWhenFirstAnimalDead))/100.0 + "\n");
                writer.write("Average of average of number of children per living animal in simulation: " +
                        Math.round((100.0*this.averageNumberOfChildrenPerLivingAnimalInTotal)/this.numberOfDay)/100.0 + "\n");

                writer.close();
            } else {
                throw new FileSystemAlreadyExistsException("can't save stats to file, file " + filePath + " already exist");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
