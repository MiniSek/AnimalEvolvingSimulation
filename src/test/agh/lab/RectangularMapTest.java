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
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(1, 0))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(7, 4))));
        Assertions.assertTrue(map.place(new Animal(map, new Vector2d(2, 2))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(1, 0))));
        Assertions.assertFalse(map.place(new Animal(map, new Vector2d(2, 2))));
    }

    @Test public void canMoveToTest() {
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map, new Vector2d(1, 0)));
        map.place(new Animal(map, new Vector2d(7, 4)));
        map.place(new Animal(map, new Vector2d(3, 0)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(0,0)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(1,0)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(3,0)));
        Assertions.assertFalse(map.canMoveTo(new Vector2d(11,5)));
    }

    @Test public void integrationTest() {
        //
    }
}
