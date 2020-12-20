package evolution;

public interface IAnimalsHerd {
    void addToHerd(Animal animal);
    void removeFromHerd(Animal animal);
    void removeAnimalsWithSmallAmountOfEnergy(int animalsMoveEnergy);
    void feedInHerd(int grassEnergy);
    void breedInHerd();
    Animal getAlphaOfHerd();
    Animal getBetaOfHerd();
    int sizeOfHerd();
}
