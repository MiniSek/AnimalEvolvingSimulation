package evolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.abs;

public class AnimalsHerd implements IAnimalsHerd{
    private final ArrayList<Animal> animalsAtPosition = new ArrayList<>();
    private int numberOfStrongest;
    private int alphaEnergy;

    public AnimalsHerd() {
        this.numberOfStrongest = 0;
        this.alphaEnergy = 0;
    }

    public void addToHerd(Animal animal) {
        if(this.sizeOfHerd() == 0 || animal.getEnergy() > this.alphaEnergy) {
            this.alphaEnergy = animal.getEnergy();
            this.numberOfStrongest = 1;
        }
        else if(animal.getEnergy() == this.alphaEnergy)
            this.numberOfStrongest += 1;

        this.animalsAtPosition.add(abs(Collections.binarySearch(this.animalsAtPosition, animal)+1), animal);
    }

    public void removeFromHerd(Animal animal) {
        if(animal.getEnergy() == this.alphaEnergy)
            this.numberOfStrongest --;
        this.animalsAtPosition.remove(animal);
    }

    public void feedAnimalsInHerd(int grassEnergy) {
        int grassPerAnimal = grassEnergy / this.numberOfStrongest;
        for(int indexOfAnimal = 0; indexOfAnimal < this.numberOfStrongest; indexOfAnimal++)
            this.animalsAtPosition.get(indexOfAnimal).eatGrass(grassPerAnimal);
    }

    public void breedAnimalsInHerd() {
        int[] indexesOfParents = this.drawTwoRandomStrongestAnimals();
        this.animalsAtPosition.get(indexesOfParents[0]).breed(this.animalsAtPosition.get(indexesOfParents[1]));
    }

    private int[] drawTwoRandomStrongestAnimals() {
        Random generator = new Random();
        int[] indexesOfParents = new int[2];
        if(numberOfStrongest > 2) {
            indexesOfParents[0] = generator.nextInt(this.numberOfStrongest);
            indexesOfParents[1] = generator.nextInt(this.numberOfStrongest);
            while (indexesOfParents[0] == indexesOfParents[1])
                indexesOfParents[1] = generator.nextInt(this.numberOfStrongest);
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

    public int sizeOfHerd() {
        return this.animalsAtPosition.size();
    }
}
