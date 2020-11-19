package agh.lab;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {
    private MoveDirection[] directions;
    private IWorldMap map;
    private ArrayList<Animal> animals = new ArrayList<>();  // to pole może być finalne

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] locations) {
        this.directions = directions;
        this.map = map;
        for(Vector2d vector : locations) {
            Animal animal = new Animal(map, vector);
            if(this.map.place(animal)) {
                this.animals.add(animal);
            }
        }
    }

    //wykonuje serie ruchow dla wszystkich zwierzat na mapie
    public void run(){
        int j=0;
        for(int i=0; i<this.directions.length; i++) {
            this.animals.get(j).move(this.directions[i]);
            j++;
            if(j>=this.animals.size())
                j=0;
            System.out.println(map.toString());
        }
    }
}
