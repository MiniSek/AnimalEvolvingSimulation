package agh.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimulationEngineTest {
    @Test public void integrationTest() {
        IWorldMap map = new RectangularMap(10, 10);
        String[] rawMoves = new String[]{"l", "l", "r",   "f", "f", "r",    "f", "r", "f",    "l", "f", "l",   "f", "f", "f",    "r"};
        MoveDirection[] directions = new OptionsParser().parse(rawMoves);
        Vector2d[] locations = new Vector2d[]{new Vector2d(1, 1), new Vector2d(3, 2), new Vector2d(1, 5)};
        IEngine engine = new SimulationEngine(directions, map, locations);
        engine.run();
        Assertions.assertEquals(map.getAnimalAtIndex(0), map.objectAt(new Vector2d(0,0)));
        Assertions.assertEquals(map.getAnimalAtIndex(1), map.objectAt(new Vector2d(2,4)));
        Assertions.assertEquals(map.getAnimalAtIndex(2), map.objectAt(new Vector2d(1,4)));
    }
}
