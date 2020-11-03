package agh.lab;

public class World {
    public static void main(String[] args) {
        String[] rawMoves = new String[] {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f"};
        MoveDirection[] directions = new OptionsParser().parse(rawMoves);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        System.out.println(map.toString());
        engine.run();
        System.out.println(map.toString());
    }
}
