package agh.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {
    @Test public void nextTest() {
        MapDirection direction=MapDirection.NORTH;
        Assertions.assertEquals(MapDirection.EAST, direction.next());
        direction=MapDirection.EAST;
        Assertions.assertEquals(MapDirection.SOUTH, direction.next());
        direction=MapDirection.SOUTH;
        Assertions.assertEquals(MapDirection.WEST, direction.next());
        direction=MapDirection.WEST;
        Assertions.assertEquals(MapDirection.NORTH, direction.next());
    }

    @Test public void previousTest() {
        MapDirection direction=MapDirection.NORTH;
        Assertions.assertEquals(direction.previous(), MapDirection.WEST);
        direction=MapDirection.WEST;
        Assertions.assertEquals(direction.previous(), MapDirection.SOUTH);
        direction=MapDirection.SOUTH;
        Assertions.assertEquals(direction.previous(), MapDirection.EAST);
        direction=MapDirection.EAST;
        Assertions.assertEquals(direction.previous(), MapDirection.NORTH);
    }
}