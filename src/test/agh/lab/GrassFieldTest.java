package agh.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GrassFieldTest {
    @Test public void objectAtTest() {
        IWorldMap map = new GrassField(1);
        Animal animal = new Animal(map, new Vector2d(4, 2));
        map.place(animal);
        Assertions.assertEquals(null, map.objectAt(new Vector2d(12,15)));
        Assertions.assertEquals(animal, map.objectAt(new Vector2d(4,2)));
    }

    @Test public void isOccupiedTest() {
        IWorldMap map = new GrassField(5);
        Animal animal1 = new Animal(map, new Vector2d(2, 1));
        Animal animal2 = new Animal(map, new Vector2d(0, 4));
        map.place(animal1);
        map.place(animal2);
        Assertions.assertTrue(map.isOccupied(new Vector2d(2,1)));
        Assertions.assertTrue(map.isOccupied(new Vector2d(0,4)));
    }

    @Test public void canMoveTo() {
        IWorldMap map = new GrassField(15);
        Animal animal1 = new Animal(map, new Vector2d(3, 4));
        map.place(animal1);
        Assertions.assertFalse(map.canMoveTo(new Vector2d(3,4)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(4,4)));
        Assertions.assertTrue(map.canMoveTo(new Vector2d(Integer.MAX_VALUE,Integer.MIN_VALUE)));
    }

    @Test public void place() {
        IWorldMap map = new GrassField(15);
        Animal animal1 = new Animal(map, new Vector2d(8, 2));
        Animal animal2 = new Animal(map, new Vector2d(Integer.MAX_VALUE,Integer.MAX_VALUE));
        Assertions.assertTrue(map.place(animal1));
        Assertions.assertFalse(map.place(animal1));
        Assertions.assertTrue(map.place(animal2));
    }
}
