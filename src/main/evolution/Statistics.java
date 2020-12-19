package evolution;

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

    public void updateStatisticsAtStart(int animalsStartEnergy, ArrayList<Animal> animals) {
        this.averageEnergyPerLivingAnimal = animalsStartEnergy;

        Collections.sort(animals, new SortByGenotype());
        this.mostCommonGenotype(animals);

        this.averageEnergyPerLivingAnimalInTotal = animalsStartEnergy;

        for(Animal animal : animals)
            this.updateNumberOfEachGenotypeInTotal(animal);
    }

    class SortByGenotype implements Comparator<Animal> {
        public int compare(Animal first, Animal second) {
            return first.getGenotype().compareTo(second.getGenotype());
        }
    }

   private void mostCommonGenotype(ArrayList<Animal> animalsSortedByGenotype) {
        String previous = "";
        int bestCount = 0;
        int count = 1;
        String bestString = "";
        for(int i = 0; i < animalsSortedByGenotype.size(); i++) {
            if(animalsSortedByGenotype.get(i).getGenotype().equals(previous))
                count++;
            else
                count = 1;

            if(count > bestCount) {
                bestCount = count;
                bestString = animalsSortedByGenotype.get(i).getGenotype();
            }
            previous = animalsSortedByGenotype.get(i).getGenotype();
        }

        //check it
        //change most common genotype only if is not as numerous as current one
        if(bestCount != this.numberOfMostCommonGenotype) {
            this.mostCommonGenotype = bestString;
            this.numberOfMostCommonGenotype = bestCount;
        }
    }

    private void updateNumberOfEachGenotypeInTotal(Animal animal) {
        Integer numberOfGenotypes = this.numberOfEachGenotypeInTotal.get(animal.getGenotype());
        if(numberOfGenotypes != null)
            this.numberOfEachGenotypeInTotal.put(animal.getGenotype(),
                    this.numberOfEachGenotypeInTotal.get(animal.getGenotype()) + 1);
        else
            this.numberOfEachGenotypeInTotal.put(animal.getGenotype(), 1);
    }
}
