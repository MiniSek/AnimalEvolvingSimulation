package evolution;

public class World {
    public static void main(String[] args) {
        try {
            int width = 30, height = 30, animalStartEnergy = 20, moveEnergy = 1, plantEnergy = 20;
            double jungleRatio = 0.3;
            int numberOfAnimals = 30;
            IEngine engine;
            for(int i=0; i<100; i++) {
                engine = new SimulationEngine(width, height, animalStartEnergy, moveEnergy, plantEnergy, jungleRatio, numberOfAnimals);
                engine.run();
                System.out.print("Simulation number: ");
                System.out.println(i+1);
                Thread.sleep(5000);
            }
        }
        catch(InterruptedException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }
}

