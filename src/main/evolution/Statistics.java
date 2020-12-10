package evolution;

public class Statistics {
    public int numberOfAnimals;
    public int numberOfChildrenForLivingAnimals;
    public long numberOfDeadAnimals;
    public long numberOfLivedDaysInSummaryForDeadAnimals;
    public int numberOfGrassesInJungle;
    public int numberOfGrassesInSavanna;

    public Statistics(int numberOfAnimalsAtStart) {
        this.numberOfAnimals = numberOfAnimalsAtStart;
        this.numberOfChildrenForLivingAnimals = 0;
        this.numberOfDeadAnimals = 0;
        this.numberOfLivedDaysInSummaryForDeadAnimals = 0;
        this.numberOfGrassesInJungle = 0;
        this.numberOfGrassesInSavanna = 0;
    }
}
