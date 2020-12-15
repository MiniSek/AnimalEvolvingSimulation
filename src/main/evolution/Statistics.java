package evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Statistics {
    public int numberOfDay;
    public int numberOfAnimals;
    public int numberOfChildrenForLivingAnimals;
    public long numberOfDeadAnimals;
    public long numberOfLivedDaysInSummaryForDeadAnimals;
    public int numberOfGrassesInJungle;
    public int numberOfGrassesInSavanna;
    public int numberOfGrassesInTotal;
    public float averageLiveLongForDeadAnimal;
    public float averageEnergyPerLivingAnimal;
    public float averageNumberOfChildrenPerLivingAnimal;
    public String mostCommonGenotype;

    public Statistics(int numberOfAnimalsAtStart) {
        this.numberOfDay = 0;

        this.numberOfAnimals = numberOfAnimalsAtStart;
        this.numberOfChildrenForLivingAnimals = 0;

        this.numberOfDeadAnimals = 0;
        this.numberOfLivedDaysInSummaryForDeadAnimals = 0;

        this.numberOfGrassesInJungle = 0;
        this.numberOfGrassesInSavanna = 0;
        this.numberOfGrassesInTotal = 0;

        this.averageLiveLongForDeadAnimal = 0;
        this.averageEnergyPerLivingAnimal = 0;
        this.averageNumberOfChildrenPerLivingAnimal = 0;

        this.mostCommonGenotype = "";
    }

    public void updateStatistics(int energySum, int childrenSum, ArrayList<Animal> animalsGenesNumbersToSort) {
        this.numberOfDay += 1;
        this.numberOfGrassesInTotal = this.numberOfGrassesInJungle + this.numberOfGrassesInSavanna;
        if(this.numberOfDeadAnimals > 0)

            this.averageLiveLongForDeadAnimal = this.numberOfLivedDaysInSummaryForDeadAnimals/this.numberOfDeadAnimals;
        else
            this.averageLiveLongForDeadAnimal = 0;

        if(this.numberOfAnimals > 0) {
            this.averageEnergyPerLivingAnimal = energySum/this.numberOfAnimals;
            this.averageNumberOfChildrenPerLivingAnimal = childrenSum/this.numberOfAnimals;
        }
        else {
            this.averageEnergyPerLivingAnimal = 0;
            this.averageNumberOfChildrenPerLivingAnimal = 0;
        }

        Collections.sort(animalsGenesNumbersToSort, new SortByGenotype());
        this.mostCommonGenotype = this.mostCommonGenotype(animalsGenesNumbersToSort);
    }

    class SortByGenotype implements Comparator<Animal> {
        public int compare(Animal first, Animal second) {
            return first.getGenotype().compareTo(second.getGenotype());
        }
    }

    public String mostCommonGenotype(ArrayList<Animal> animalsSortedByGenotype) {
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
        return bestString;
    }
}
