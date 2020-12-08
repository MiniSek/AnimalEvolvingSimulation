package evolution;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public byte[] genes;

    public Genotype(){
        this.genes = new byte[32];
    }

    //should this methods be here, especially drawGene
    public void sortGenes() {
        Arrays.sort(this.genes);
    }

    public byte drawGene() {
        Random generator = new Random();
        return this.genes[generator.nextInt(32)];
    }

    public Genotype inheritGenes(Genotype other) {
        Genotype childGenotype = new Genotype();
        Random generator = new Random();

        int firstIndex = generator.nextInt(31);
        int secondIndex = generator.nextInt(31);
        while(firstIndex == secondIndex)
            secondIndex = generator.nextInt(31);

        for(int i = 0; i <= firstIndex; i++)
            childGenotype.genes[i] = this.genes[i];

        if(generator.nextBoolean()) {
            for(int i = firstIndex + 1; i <= secondIndex; i++)
                childGenotype.genes[i] = this.genes[i];
            for(int i = secondIndex + 1; i < 32; i++)
                childGenotype.genes[i] = other.genes[i];
        }
        else {
            for(int i = firstIndex + 1; i <= secondIndex; i++)
                childGenotype.genes[i] = other.genes[i];
            for(int i = secondIndex + 1; i < 32; i++)
                childGenotype.genes[i] = this.genes[i];
        }

        childGenotype.sortGenes();

        int[] numberOfEachGeneInChildGenotype = new int[8];
        for(int i = 0; i < 32; i++)
            numberOfEachGeneInChildGenotype[childGenotype.genes[i]] += 1;
        for(int i = 0; i < 8; i++)
            if(numberOfEachGeneInChildGenotype[i] == 0) {
                int k = generator.nextInt(32);
                while(numberOfEachGeneInChildGenotype[k] < 2)
                    k=generator.nextInt(32);
                childGenotype.genes[k] = (byte)i;
            }

        return childGenotype;
    }
}
