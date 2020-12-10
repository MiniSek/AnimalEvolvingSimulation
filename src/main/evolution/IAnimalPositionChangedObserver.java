package evolution;

public interface IAnimalPositionChangedObserver {
    void positionChanged(Vector2d oldPosition, Animal animal);
}
