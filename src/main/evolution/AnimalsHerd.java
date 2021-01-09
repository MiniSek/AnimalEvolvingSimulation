package evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;

public class AnimalsHerd implements IAnimalsHerd{
    private final RectangularBiomesMap map;

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

    //herd informs map about deleting animal
    public void removeAnimalsWithZeroAndSubZeroEnergy() {   // długa nazwa
        for(int i = this.sizeOfHerd()-1; i >= 0 && this.animalsAtPosition.get(i).getEnergy() <= 0; i--) {
            this.map.animalWillBeDeleted(this.animalsAtPosition.get(i));
            this.removeFromHerd(this.animalsAtPosition.get(i));
        }
    }

    public void feedInHerd(int grassEnergy) {
        int numberOfStrongestAnimal = this.countStrongest();

        int grassPerAnimal = grassEnergy/numberOfStrongestAnimal;
        for(int indexOfAnimal = 0; indexOfAnimal < numberOfStrongestAnimal; indexOfAnimal++)
            this.animalsAtPosition.get(indexOfAnimal).eatGrass(grassPerAnimal);
    }

    public void breedInHerd() {
        int[] indexesOfParents = this.drawTwoRandomStrongestAnimals();
        this.animalsAtPosition.get(indexesOfParents[0]).breed(this.animalsAtPosition.get(indexesOfParents[1]));
    }

    private int[] drawTwoRandomStrongestAnimals() {
        Random generator = new Random();
        int[] indexesOfParents = new int[2];
        int numberOfStrongestAnimals = this.countStrongest();

        if(numberOfStrongestAnimals > 2) {
            indexesOfParents[0] = generator.nextInt(numberOfStrongestAnimals);
            indexesOfParents[1] = generator.nextInt(numberOfStrongestAnimals);
            while(indexesOfParents[0] == indexesOfParents[1])   // do-while
                indexesOfParents[1] = generator.nextInt(numberOfStrongestAnimals);
        }
        else {
            indexesOfParents[0]=generator.nextInt(2); //parents should always be choose randomly; its for genes inheritance
            if(indexesOfParents[0] == 0)
                indexesOfParents[1]=1;  // a jeśli zwierzęta o indeksach 1, 2 i 3 mają taką samą energię, ale mniejszą niż alpha?
            else
                indexesOfParents[1]=0;
        }
        return indexesOfParents;
    }

    private int countStrongest() {
        int numberOfStrongestAnimals = 0;
        while(numberOfStrongestAnimals < this.sizeOfHerd() &&
                this.animalsAtPosition.get(numberOfStrongestAnimals).getEnergy() == this.getAlphaOfHerd().getEnergy()) // czy alpha się może zmienić między obrotami pętli?
            numberOfStrongestAnimals++;
        return numberOfStrongestAnimals;
    }

    //first in the list or null
    public Animal getAlphaOfHerd() {
        if(this.sizeOfHerd() > 0)
            return this.animalsAtPosition.get(0);
        return null;
    }

    //second in the list or null
    public Animal getBetaOfHerd() {
        if(this.sizeOfHerd() > 1)
            return this.animalsAtPosition.get(1);
        return null;
    }

    public int sizeOfHerd() {
        return this.animalsAtPosition.size();
    }
}
