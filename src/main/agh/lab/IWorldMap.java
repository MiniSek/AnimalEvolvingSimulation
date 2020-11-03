package agh.lab;

public interface IWorldMap {
    String toString();
    boolean canMoveTo(Vector2d position);
    boolean place(Animal animal);
    boolean isOccupied(Vector2d position);
    Animal getAnimalAtIndex(int i);
    int getNumberOfAnimals();
    Object objectAt(Vector2d position);
}
