package evolution;

public interface IWorldMap {
    boolean canMoveTo(Vector2d position);
    void place(Animal animal);
    boolean isOccupied(Vector2d position);
    Object objectAt(Vector2d position);
    Vector2d findPlaceForChildAnimal(Vector2d parentPosition);
    void animalToAddToMap(Animal animal);
}
