package agh.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test public void toStringTest() {
        IWorldMap map = new RectangularMap(10,5);
        Animal animal = new Animal(map);
        Assertions.assertEquals("^", animal.toString());
    }

    @Test public void getPositionTest() {
        IWorldMap map = new RectangularMap(10,5);
        Animal animal1 = new Animal(map);
        Assertions.assertEquals("(2,2)", animal1.getPosition().toString());
        Animal animal2 = new Animal(map, new Vector2d(7,5));
        Assertions.assertEquals("(7,5)", animal2.getPosition().toString());
    }

    @Test public void moveTest() {
        IWorldMap map = new RectangularMap(10,5);
        Animal animal = new Animal(map);
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals(">", animal.toString());
        Assertions.assertEquals("(2,2)", animal.getPosition().toString());
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals("v", animal.toString());
        Assertions.assertEquals("(2,2)", animal.getPosition().toString());
        animal.move(MoveDirection.LEFT);
        Assertions.assertEquals(">", animal.toString());
        Assertions.assertEquals("(2,2)", animal.getPosition().toString());
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals(">", animal.toString());
        Assertions.assertEquals("(3,2)", animal.getPosition().toString());
        animal.move(MoveDirection.LEFT);
        Assertions.assertEquals("^", animal.toString());
        Assertions.assertEquals("(3,2)", animal.getPosition().toString());
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals("^", animal.toString());
        Assertions.assertEquals("(3,3)", animal.getPosition().toString());
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("<", animal.toString());
        Assertions.assertEquals("(4,3)", animal.getPosition().toString());
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals("^", animal.toString());
        Assertions.assertEquals("(4,4)", animal.getPosition().toString());

        Animal animal2 = new Animal(map);
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals(">", animal2.toString());
        Assertions.assertEquals("(5,2)", animal2.getPosition().toString());
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals("v", animal2.toString());
        Assertions.assertEquals("(5,0)", animal2.getPosition().toString());

        Animal animal3 = new Animal(map);
        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.FORWARD);
        animal3.move(MoveDirection.FORWARD);
        animal3.move(MoveDirection.FORWARD);
        Assertions.assertEquals("v", animal3.toString());
        Assertions.assertEquals("(2,0)", animal3.getPosition().toString());
        animal3.move(MoveDirection.LEFT);
        animal3.move(MoveDirection.BACKWARD);
        animal3.move(MoveDirection.BACKWARD);
        animal3.move(MoveDirection.BACKWARD);
        Assertions.assertEquals(">", animal3.toString());
        Assertions.assertEquals("(0,0)", animal3.getPosition().toString());
    }
}
