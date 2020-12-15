package evolution;

import java.util.Arrays;
import java.util.Random;

public class Genotype {
    public byte[] genes;
    public String genesString;

    public Genotype(){
        this.genes = new byte[32];
        this.genesString = "";
    }

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

        //the genotype is divided into parts with indexes [0, firstIndex], [firstIndex+1, secondIndex], [secondIndex+1, 31]
        int firstIndex = generator.nextInt(31);
        int secondIndex = generator.nextInt(31);

        //firstIndex has to be smaller than secondIndex
        while(firstIndex == secondIndex)
            secondIndex = generator.nextInt(31);
        if(secondIndex < firstIndex) {
            int indexForSwapping = firstIndex;
            firstIndex = secondIndex;
            secondIndex = indexForSwapping;
        }

        //genes with indexes [0, firstIndex] are from this Genotype
        for(int i = 0; i <= firstIndex; i++)
            childGenotype.genes[i] = this.genes[i];

        //genes with indexes [firstIndex+1, secondIndex] are from this Genotype and genes with indexes [secondIndex+1, 31]
        //are from other Genotype
        //or vide versa
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

        byte[] numberOfEachGeneInChildGenotype = {0,0,0,0, 0,0,0,0};
        for(int i = 0; i < 32; i++)
            numberOfEachGeneInChildGenotype[childGenotype.genes[i]] += 1;

        for(byte i = 0; i < 8; i++)
            if(numberOfEachGeneInChildGenotype[i] == 0) {
                int possibleIndexToAddMissingGene = generator.nextInt(32);
                //missing gene cannot be added at position where is gene which is only one in genotype
                while(numberOfEachGeneInChildGenotype[childGenotype.genes[possibleIndexToAddMissingGene]] < 2)
                    possibleIndexToAddMissingGene=generator.nextInt(32);
                numberOfEachGeneInChildGenotype[childGenotype.genes[possibleIndexToAddMissingGene]]--;
                numberOfEachGeneInChildGenotype[i]++;
                childGenotype.genes[possibleIndexToAddMissingGene] = i;
            }

        return childGenotype;
    }

    public void rewriteGenesToString() {
        for(int i = 0; i < 32; i++)
            this.genesString += String.valueOf(this.genes[i]);
    }
}
