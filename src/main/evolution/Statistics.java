package evolution;

import java.util.ArrayList;
import java.util.Collections;

public class Statistics {
    public int numberOfDay;
    public int numberOfAnimals;
    public int numberOfChildrenForLivingAnimals;
    public long numberOfDeadAnimals;
    public long numberOfLivedDaysInSummaryForDeadAnimals;
    public int numberOfGrassesInJungle;
    public int numberOfGrassesInSavanna;
    public int numberOfGrassesInTotal;
    public float averageliveLongForDeadAnimal;
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

        this.averageliveLongForDeadAnimal = 0;
        this.averageEnergyPerLivingAnimal = 0;
        this.averageNumberOfChildrenPerLivingAnimal = 0;

        this.mostCommonGenotype = "";
    }

    public void updateStatistics(int energySum, int childrenSum, ArrayList<String> animalsGenesNumbersToSort) {
        this.numberOfDay += 1;
        this.numberOfGrassesInTotal = this.numberOfGrassesInJungle + this.numberOfGrassesInSavanna;
        if(this.numberOfDeadAnimals > 0)

            this.averageliveLongForDeadAnimal = this.numberOfLivedDaysInSummaryForDeadAnimals/this.numberOfDeadAnimals;
        else
            this.averageliveLongForDeadAnimal = 0;

        if(this.numberOfAnimals > 0) {
            this.averageEnergyPerLivingAnimal = energySum/this.numberOfAnimals;
            this.averageNumberOfChildrenPerLivingAnimal = childrenSum/this.numberOfAnimals;
        }
        else {
            this.averageEnergyPerLivingAnimal = 0;
            this.averageNumberOfChildrenPerLivingAnimal = 0;
        }

        Collections.sort(animalsGenesNumbersToSort);
        this.mostCommonGenotype = this.mostCommon(animalsGenesNumbersToSort);
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

}
