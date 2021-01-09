package evolution;

public interface IAnimalsHerd { // czy ten interfejs jest potrzebny?
    void addToHerd(Animal animal);
    void removeFromHerd(Animal animal);
    void removeAnimalsWithZeroAndSubZeroEnergy();   // długa nazwa, nie wystarczy removeDeadAnimals?
    void feedInHerd(int grassEnergy);
    void breedInHerd();
    Animal getAlphaOfHerd();
    Animal getBetaOfHerd();
    int sizeOfHerd();
}
