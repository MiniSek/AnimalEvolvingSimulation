package agh.lab;

public class SimulationEngine implements IEngine {
    private MoveDirection[] directions;
    private IWorldMap map;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] locations) {
        this.directions = directions;
        this.map = map;
        for(Vector2d vector : locations)
            this.map.place(new Animal(map, vector));
    }

    //wykonuje serie ruchow dla wszystkich zwierzat na mapie
    public void run(){
        int j=0;
        for(int i=0; i<this.directions.length; i++) {
            this.map.getAnimalAtIndex(j).move(this.directions[i]);
            j++;
            if(j>=this.map.getNumberOfAnimals())
                j=0;
        }
    }
}
