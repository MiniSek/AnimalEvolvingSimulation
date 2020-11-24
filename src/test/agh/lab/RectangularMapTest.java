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
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(5, 4))));
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

    @Test public void integrationTest() {
        IWorldMap map = new RectangularMap(10, 10);
        Animal animal1 = new Animal(map, new Vector2d(0,0));
        Animal animal2 = new Animal(map, new Vector2d(1,2));
        Animal animal3 = new Animal(map, new Vector2d(1,2));
        map.place(animal1);
        animal1.addObserver((IPositionChangeObserver)map);
        map.place(animal2);
        animal2.addObserver((IPositionChangeObserver)map);
        Assertions.assertFalse(map.place(animal3));

        animal1.move(MoveDirection.LEFT);
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertEquals(new Vector2d(0,0), animal1.getPosition());
        animal1.move(MoveDirection.BACKWARD);
        animal1.move(MoveDirection.RIGHT);
        animal1.move(MoveDirection.FORWARD);
        animal1.move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1,1)));
        Assertions.assertEquals(animal2, map.objectAt(new Vector2d(1,2)));
        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.LEFT);
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals(animal1, map.objectAt(new Vector2d(1,1)));
        Assertions.assertEquals(animal2, map.objectAt(new Vector2d(1,2)));
    }
}
