package evolution;

public class Statistics {
    public int numberOfAnimals;
    public int numberOfDeadAnimals;
    public int numberOfGrassesInJungle;
    public int numberOfGrassesInSavanna;

    public Statistics(int numberOfAnimalsAtStart) {
        this.numberOfAnimals = numberOfAnimalsAtStart;
        this.numberOfDeadAnimals = 0;
        this.numberOfGrassesInJungle = 0;
        this.numberOfGrassesInSavanna = 0;
    }
}
