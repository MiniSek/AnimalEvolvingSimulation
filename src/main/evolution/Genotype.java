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
        System.arraycopy(this.genes, 0, childGenotype.genes, 0, firstIndex+1);

        //genes with indexes [firstIndex+1, secondIndex] are from this Genotype and genes with indexes [secondIndex+1, 31]
        //are from other Genotype
        //or vice versa
        if(generator.nextBoolean()) {
            System.arraycopy(this.genes, firstIndex+1, childGenotype.genes, firstIndex+1, secondIndex - firstIndex);
            System.arraycopy(other.genes, secondIndex+1, childGenotype.genes, secondIndex+1, 31 - secondIndex);
        }
        else {
            System.arraycopy(other.genes, firstIndex+1, childGenotype.genes, firstIndex+1, secondIndex - firstIndex);
            System.arraycopy(this.genes, secondIndex+1, childGenotype.genes, secondIndex+1, 31 - secondIndex);
        }

        childGenotype.sortGenes();
        childGenotype.repairGenotype();

        return childGenotype;
    }

    public void repairGenotype() {
        Random generator = new Random();
        byte[] numberOfEachGeneInChildGenotype = {0,0,0,0, 0,0,0,0};
        for(int i = 0; i < 32; i++)
            numberOfEachGeneInChildGenotype[this.genes[i]] += 1;

        for(byte i = 0; i < 8; i++)
            if(numberOfEachGeneInChildGenotype[i] == 0) {
                int possibleIndexToAddMissingGene = generator.nextInt(32);
                //missing gene cannot be added at position where is gene which is only one in genotype
                while(numberOfEachGeneInChildGenotype[this.genes[possibleIndexToAddMissingGene]] <= 1)
                    possibleIndexToAddMissingGene = generator.nextInt(32);

                //update number of each gene in genotype
                numberOfEachGeneInChildGenotype[this.genes[possibleIndexToAddMissingGene]]--;
                numberOfEachGeneInChildGenotype[i]++;

                this.genes[possibleIndexToAddMissingGene] = i;
            }
    }

    public void rewriteGenesToString() {
        for(int i = 0; i < 32; i++)
            this.genesString += String.valueOf(this.genes[i]);
    }
}
