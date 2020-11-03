package agh.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RectangularMapTest {
    @Test public void isOccupiedTest() {
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map, new Vector2d(1, 0)));
        map.place(new Animal(map, new Vector2d(7, 4)));
        map.place(new Animal(map, new Vector2d(3, 0)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(1, 0)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(3, 0)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(2, 2)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(7, 5)));
        Assertions.assertFalse(map.isOccupied(new Vector2d(11, 6)));
    }

    @Test public void placeTest() {
        IWorldMap map = new RectangularMap(10, 5);
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(0, 1))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(5, 5))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(10, 6))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(0, 1))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(5, 5))));
    }

    @Test public void canMoveToTest() {
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map, new Vector2d(2, 5)));
        map.place(new Animal(map, new Vector2d(8, 2)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(0,0)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(2,5)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(8,2)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(11,5)));
    }

    @Test public void objectAtTest() {
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal = new Animal(map, new Vector2d(0, 0));
        map.place(animal);
        Assertions.assertEquals(null, map.objectAt(new Vector2d(1,1)));
        Assertions.assertEquals(animal, map.objectAt(new Vector2d(0,0)));
    }

    @Test public void getAnimalAtIndexTest() {
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal(map, new Vector2d(5,5));
        map.place(animal1);
        Animal animal2 = new Animal(map, new Vector2d(1,3));
        map.place(animal2);
        Animal animal3 = new Animal(map, new Vector2d(9,0));
        map.place(animal3);
        Assertions.assertEquals(animal1, map.getAnimalAtIndex(0));
        Assertions.assertEquals(animal3, map.getAnimalAtIndex(2));
    }

    @Test public void getNumberOfAnimals() {
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map, new Vector2d(4, 2)));
        map.place(new Animal(map, new Vector2d(8, 3)));
        Assertions.assertEquals(2, map.getNumberOfAnimals());
    }

    @Test public void integrationTest() {
        IWorldMap map = new RectangularMap(10, 10);
        Animal animal1 = new Animal(map, new Vector2d(0,0));
        Animal animal2 = new Animal(map, new Vector2d(1,2));
        Animal animal3 = new Animal(map, new Vector2d(1,2));
        map.place(animal1);
        map.place(animal2);
        Assertions.assertFalse(map.place(animal3));
        Assertions.assertEquals(2, map.getNumberOfAnimals());

        map.getAnimalAtIndex(0).move(MoveDirection.LEFT);
        map.getAnimalAtIndex(0).move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(0,0), map.getAnimalAtIndex(0).getPosition());
        map.getAnimalAtIndex(0).move(MoveDirection.BACKWARD);
        map.getAnimalAtIndex(0).move(MoveDirection.RIGHT);
        map.getAnimalAtIndex(0).move(MoveDirection.FORWARD);
        map.getAnimalAtIndex(0).move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1,1)));
        Assertions.assertEquals(animal2, map.objectAt(new Vector2d(1,2)));
        map.getAnimalAtIndex(1).move(MoveDirection.LEFT);
        map.getAnimalAtIndex(1).move(MoveDirection.LEFT);
        map.getAnimalAtIndex(1).move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1,1)));
        Assertions.assertEquals(animal2, map.objectAt(new Vector2d(1,2)));
    }
}
