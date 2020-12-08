package evolution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapDirectionTest {
    @Test public void nextTest() {
        MapDirection direction = MapDirection.NORTH;
        Assertions.assertEquals(MapDirection.NORTHEAST, direction.next());

        direction = MapDirection.NORTHEAST;
        Assertions.assertEquals(MapDirection.EAST, direction.next());

        direction = MapDirection.EAST;
        Assertions.assertEquals(MapDirection.SOUTHEAST, direction.next());

        direction = MapDirection.SOUTHEAST;
        Assertions.assertEquals(MapDirection.SOUTH, direction.next());

        direction = MapDirection.SOUTH;
        Assertions.assertEquals(MapDirection.SOUTHWEST, direction.next());

        direction = MapDirection.SOUTHWEST;
        Assertions.assertEquals(MapDirection.WEST, direction.next());

        direction = MapDirection.WEST;
        Assertions.assertEquals(MapDirection.NORTHWEST, direction.next());

        direction = MapDirection.NORTHWEST;
        Assertions.assertEquals(MapDirection.NORTH, direction.next());
    }

    @Test public void previousTest() {
        MapDirection direction = MapDirection.NORTH;
        Assertions.assertEquals(direction.previous(), MapDirection.NORTHWEST);

        direction = MapDirection.NORTHEAST;
        Assertions.assertEquals(direction.previous(), MapDirection.NORTH);

        direction = MapDirection.EAST;
        Assertions.assertEquals(direction.previous(), MapDirection.NORTHEAST);

        direction = MapDirection.SOUTHEAST;
        Assertions.assertEquals(direction.previous(), MapDirection.EAST);

        direction = MapDirection.SOUTH;
        Assertions.assertEquals(direction.previous(), MapDirection.SOUTHEAST);

        direction = MapDirection.SOUTHWEST;
        Assertions.assertEquals(direction.previous(), MapDirection.SOUTH);

        direction = MapDirection.WEST;
        Assertions.assertEquals(direction.previous(), MapDirection.SOUTHWEST);

        direction = MapDirection.NORTHWEST;
        Assertions.assertEquals(direction.previous(), MapDirection.WEST);
    }

    @Test public void toUnitVectorTest() {
        MapDirection direction = MapDirection.NORTH;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(0,1)));

        direction = MapDirection.NORTHEAST;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(1,1)));

        direction = MapDirection.EAST;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(1,0)));

        direction = MapDirection.SOUTHEAST;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(1,-1)));

        direction = MapDirection.SOUTH;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(0,-1)));

        direction = MapDirection.SOUTHWEST;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(-1,-1)));

        direction = MapDirection.WEST;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(-1,0)));

        direction = MapDirection.NORTHWEST;
        Assertions.assertTrue(direction.toUnitVector().equals(new Vector2d(-1,1)));
    }
}
