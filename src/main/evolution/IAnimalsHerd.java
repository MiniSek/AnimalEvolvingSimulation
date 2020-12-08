package evolution;

public interface IAnimalsHerd {
    void addToHerd(Animal animal);
    void removeFromHerd(Animal animal);
    void feedAnimalsInHerd(int grassEnergy);
    void breedAnimalsInHerd();
    Animal getAlphaOfHerd();
    int sizeOfHerd();
}
