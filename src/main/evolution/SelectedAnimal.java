package evolution;

public class SelectedAnimal implements IAnimalsBehaviourOnMapObserver{
    public Animal animal;
    public int numberOfChildrenWhenPicked;
    public int dayWhenPicked;
    public int dayWhenDead;
    public int numberOfDescendants;

    private Statistics statistics;
    private SimulationEngine engine;
    private SimulationWindow window;
    private RectangularBiomesMap map;

    public SelectedAnimal(Statistics statistics, SimulationEngine engine, SimulationWindow window, RectangularBiomesMap map) {
        this.animal = null;
        this.numberOfChildrenWhenPicked = 0;
        this.dayWhenPicked = 0;
        this.dayWhenDead = 0;
        this.numberOfDescendants = 0;

        this.statistics = statistics;
        this.engine = engine;
        this.window = window;
        this.map = map;

        this.map.addObserver(this);
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public void animalPicked() {
        this.dayWhenPicked = this.statistics.numberOfDay;
        this.numberOfChildrenWhenPicked = this.animal.getNumberOfChildren();
        this.animal.turnOnMarker();
    }

    public void animalSelectedDied() {
        this.dayWhenDead = this.statistics.numberOfDay;
        this.window.update();
        this.animal = null;
        this.engine.turnOffMarkers();
        this.numberOfDescendants = 0;
    }

    public void animalSelectedUnselected() {
        this.animal = null;
        this.engine.turnOffMarkers();
        this.numberOfDescendants = 0;
    }

    public boolean isNull() {
        return this.animal == null;
    }

    @Override public void animalCreated(Animal animal) {
        if(animal.marked())
            this.numberOfDescendants++;
    }

    @Override public void animalDied(Animal animal) {
        if(animal.equals(this.animal))
            this.animalSelectedDied();
    }
}
