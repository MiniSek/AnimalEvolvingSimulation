package evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;

public class AnimalsHerd implements IAnimalsHerd{
    private RectangularBiomesMap map;

    private final ArrayList<Animal> animalsAtPosition = new ArrayList<>();

    public AnimalsHerd(RectangularBiomesMap map) {
        this.map = map;
    }

    public void addToHerd(Animal animal) {
        this.animalsAtPosition.add(abs(Collections.binarySearch(this.animalsAtPosition, animal)+1), animal);
    }

    public void removeFromHerd(Animal animal) {
        this.animalsAtPosition.remove(animal);
    }

    public void removeAnimalsWithSmallAmountOfEnergy(int animalMoveEnergy) {
        for(int i = this.sizeOfHerd()-1; i >= 0 && this.animalsAtPosition.get(i).getEnergy() < animalMoveEnergy; i--) {
            this.map.animalWasDeleted(this.animalsAtPosition.get(i));
            this.removeFromHerd(this.animalsAtPosition.get(i));
        }
    }

    public void feedInHerd(int grassEnergy) {
        int numberOfStrongestAnimals = 0;
        while(numberOfStrongestAnimals < this.sizeOfHerd() && this.animalsAtPosition.get(numberOfStrongestAnimals).getEnergy() == this.getAlphaOfHerd().getEnergy())
            numberOfStrongestAnimals++;
        int grassPerAnimal = grassEnergy/numberOfStrongestAnimals;
        for(int indexOfAnimal = 0; indexOfAnimal < numberOfStrongestAnimals; indexOfAnimal++)
            this.animalsAtPosition.get(indexOfAnimal).eatGrass(grassPerAnimal);
    }

    public void breedInHerd() {
        int[] indexesOfParents = this.drawTwoRandomStrongestAnimals();
        this.animalsAtPosition.get(indexesOfParents[0]).breed(this.animalsAtPosition.get(indexesOfParents[1]));
    }

    private int[] drawTwoRandomStrongestAnimals() {
        Random generator = new Random();
        int[] indexesOfParents = new int[2];
        int numberOfStrongestAnimals = 0;
        while(numberOfStrongestAnimals < this.sizeOfHerd() && this.animalsAtPosition.get(numberOfStrongestAnimals).getEnergy() == this.getAlphaOfHerd().getEnergy())
            numberOfStrongestAnimals++;
        if(numberOfStrongestAnimals > 2) {
            indexesOfParents[0] = generator.nextInt(numberOfStrongestAnimals);
            indexesOfParents[1] = generator.nextInt(numberOfStrongestAnimals);
            while (indexesOfParents[0] == indexesOfParents[1])
                indexesOfParents[1] = generator.nextInt(numberOfStrongestAnimals);
        }
        else {
            indexesOfParents[0]=0;
            indexesOfParents[1]=1;
        }
        return indexesOfParents;
    }

    public Animal getAlphaOfHerd() {
        if(this.sizeOfHerd() > 0)
            return this.animalsAtPosition.get(0);
        return null;
    }

    public Animal getBetaOfHerd() {
        if(this.sizeOfHerd() > 1)
            return this.animalsAtPosition.get(1);
        return null;
    }

    public int sizeOfHerd() {
        return this.animalsAtPosition.size();
    }
}
