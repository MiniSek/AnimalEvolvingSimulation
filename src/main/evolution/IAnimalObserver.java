package evolution;

public interface IAnimalObserver {
    void positionChanged(Vector2d oldPosition, Animal animal);
    void died(Animal animal);
}
