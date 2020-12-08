package evolution;

public class World {
    public static void main(String[] args) {
        try {
            int width = 30, height = 30, animalStartEnergy = 20, moveEnergy = 1, plantEnergy = 3;
            double jungleRatio = 0.3;
            int numberOfAnimals = 10;
            IEngine engine = new SimulationEngine(width, height, animalStartEnergy, moveEnergy, plantEnergy, jungleRatio, numberOfAnimals);
            engine.run();
        }
        catch(IllegalArgumentException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }
}

